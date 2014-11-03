package org.nii.phenominer.ml.crf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import opennlp.model.SequenceStream;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

import org.nii.phenominer.ner.features.NgramTokenFeatureGenerator;

import cc.mallet.fst.CRF;
import cc.mallet.fst.PerClassAccuracyEvaluator;
import cc.mallet.fst.SegmentationEvaluator;
import cc.mallet.fst.TokenAccuracyEvaluator;
import cc.mallet.fst.TransducerEvaluator;
import cc.mallet.fst.semi_supervised.FSTConstraintUtil;
import cc.mallet.fst.semi_supervised.pr.CRFTrainerByPR;
import cc.mallet.fst.semi_supervised.pr.constraints.OneLabelL2IndPRConstraints;
import cc.mallet.fst.semi_supervised.pr.constraints.PRConstraint;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.Sequence;
import cc.mallet.util.Maths;

public class CRFbyPRTrainerExample {
	public static AdaptiveFeatureGenerator getFeatureGenerators() throws Exception {
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 3, 3), 2, 2),
						new SentenceFeatureGenerator(true, false) });
		return featureGenerator;
	}

	public static SequenceStream readData(String file, AdaptiveFeatureGenerator featureGenerator,
			Charset charset) throws Exception {
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(file),
				charset);
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
		SequenceStream sequences = new NERSequenceStream(sampleStream, featureGenerator);
		return sequences;
	}

	static String trainingPath = "test/train.example";
	static String unlabeledPath = "test/unlabeled.example";
	static String testingPath = "test/test.example";

	static String constraintsPath = "test/contrainstTest.txt";

	public static void main(String[] args) throws Exception {
		Charset charset = Charset.forName("ISO-8859-1");
		SequenceStream trainingSequences = readData(trainingPath, getFeatureGenerators(), charset);
		SequenceStream testingSequences = readData(testingPath, getFeatureGenerators(), charset);
		SequenceStream unlabeledSequences = readData(unlabeledPath, getFeatureGenerators(), charset);

		InstanceList trainingInstances = SequenceStreamToInstanceList
				.sequenceStreamToTrainingInstanceList(trainingSequences);
		InstanceList unlabeledInstances = SequenceStreamToInstanceList
				.sequenceStreamToTrainingInstanceList(unlabeledSequences);
		InstanceList testingInstances = SequenceStreamToInstanceList
				.sequenceStreamToTestingInstanceList(testingSequences,
						unlabeledInstances.getDataAlphabet(),
						(LabelAlphabet) trainingInstances.getTargetAlphabet());

		HashMap<Integer, double[][]> constraints = FSTConstraintUtil.loadGEConstraints(
				new FileReader(new File(constraintsPath)), trainingInstances);

		ArrayList<PRConstraint> constraintsList = new ArrayList<PRConstraint>();
		OneLabelL2IndPRConstraints prConstraints = new OneLabelL2IndPRConstraints(true);
		for (int fi : constraints.keySet()) {
			double[][] dist = constraints.get(fi);
            for (int li = 0; li < dist.length; li++) {
              if (!Double.isInfinite(dist[li][0]) && !Maths.almostEquals(dist[li][0],dist[li][1])) {
                throw new RuntimeException("Support for range constraints in PR in development. ");
              }
              
              if (!Double.isInfinite(dist[li][0])) {
                prConstraints.addConstraint(fi, li, dist[li][0], 10.0);
              }
            }
		}
		constraintsList.add(prConstraints);

		CRF crf = new CRF(unlabeledInstances.getDataAlphabet(),
				trainingInstances.getTargetAlphabet());

		crf.addFullyConnectedStatesForLabels();
		crf.setWeightsDimensionDensely();

		CRFTrainerByPR crfTrainer = new CRFTrainerByPR(crf, constraintsList);
		double gaussianPriorVariance = 10.0;
		crfTrainer.setPGaussianPriorVariance(gaussianPriorVariance);
		crfTrainer.train(unlabeledInstances, Integer.MAX_VALUE);

		System.out.println("=============");
		TransducerEvaluator eval = new PerClassAccuracyEvaluator(testingInstances, "testing");
		eval.evaluate(crfTrainer);
		System.out.println("=============");
		eval = new TokenAccuracyEvaluator(testingInstances, "testing");
		eval.evaluate(crfTrainer);
		System.out.println("=============");
		eval = new SegmentationEvaluator(testingInstances, "testing");
		eval.evaluate(crfTrainer);
		
		System.out.println("============");
		List<FeatureVectorSequence> fvSequences = SequenceStreamToInstanceList
				.sequenceStreamToClassifyInstanceList(testingSequences,
						unlabeledInstances.getDataAlphabet());
		for (FeatureVectorSequence fvSequence : fvSequences) {
			Sequence<?> outputSequence = crf.transduce(fvSequence);
			for (int i = 0; i < outputSequence.size(); i++)
				System.out.println(outputSequence.get(i));
			System.out.println("============");
		}
	}
}
