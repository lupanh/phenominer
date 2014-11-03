package org.nii.phenominer.ml.crf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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

import org.nii.phenominer.ner.features.FeatureStatisticalExample;
import org.nii.phenominer.ner.features.NgramTokenFeatureGenerator;

import cc.mallet.fst.CRF;
import cc.mallet.fst.PerClassAccuracyEvaluator;
import cc.mallet.fst.SegmentationEvaluator;
import cc.mallet.fst.TokenAccuracyEvaluator;
import cc.mallet.fst.Transducer;
import cc.mallet.fst.TransducerEvaluator;
import cc.mallet.fst.semi_supervised.CRFTrainerByGE;
import cc.mallet.fst.semi_supervised.CRFTrainerByLikelihoodAndGE;
import cc.mallet.fst.semi_supervised.FSTConstraintUtil;
import cc.mallet.fst.semi_supervised.constraints.GEConstraint;
import cc.mallet.fst.semi_supervised.constraints.OneLabelKLGEConstraints;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.Sequence;
import cc.mallet.util.Maths;

public class CRFbyERTrainerExample {
	public static AdaptiveFeatureGenerator getFeatureGenerators() throws Exception {
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 1, 2), 2, 2),
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

	private static int[] getOrders() {
		String[] ordersString = "0,1".split(",");
		int[] orders = new int[ordersString.length];
		for (int i = 0; i < ordersString.length; i++) {
			orders[i] = Integer.parseInt(ordersString[i]);
			System.err.println("Orders: " + orders[i]);
		}
		return orders;
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

		// FileHelper.writeToFile(FeatureStatisticalExample.generateConstraints(trainingSequences)
		// .toString(), new File(constrainsPath), charset);

		InstanceList trainingInstances = SequenceStreamToInstanceList
				.sequenceStreamToTrainingInstanceList(trainingSequences);

		InstanceList unlabeledInstances = SequenceStreamToInstanceList
				.sequenceStreamToTestingInstanceList(unlabeledSequences,
						trainingInstances.getDataAlphabet(),
						(LabelAlphabet) trainingInstances.getTargetAlphabet());

		List<FeatureVectorSequence> fvSequences = SequenceStreamToInstanceList
				.sequenceStreamToClassifyInstanceList(testingSequences,
						trainingInstances.getDataAlphabet());

		HashMap<Integer, double[][]> constraints = FSTConstraintUtil.loadGEConstraints(
				new FileReader(new File(constraintsPath)), trainingInstances);

		ArrayList<GEConstraint> constraintsList = new ArrayList<GEConstraint>();
		OneLabelKLGEConstraints geConstraints = new OneLabelKLGEConstraints();
		for (int fi : constraints.keySet()) {
			double[][] dist = constraints.get(fi);

			boolean allSame = true;
			double sum = 0;

			double[] prob = new double[dist.length];
			for (int li = 0; li < dist.length; li++) {
				prob[li] = dist[li][0];
				if (!Maths.almostEquals(dist[li][0], dist[li][1])) {
					allSame = false;
					break;
				} else if (Double.isInfinite(prob[li])) {
					prob[li] = 0;
				}
				sum += prob[li];
			}

			if (!allSame) {
				throw new RuntimeException(
						"A KL divergence penalty cannot be used with target ranges!");
			}
			if (!Maths.almostEquals(sum, 1)) {
				throw new RuntimeException(
						"Targets must sum to 1 when using a KL divergence penalty!");
			}

			geConstraints.addConstraint(fi, prob, 1);
		}
		constraintsList.add(geConstraints);

		CRF crf = new CRF(unlabeledInstances.getDataAlphabet(),
				trainingInstances.getTargetAlphabet());

		crf.addFullyConnectedStatesForLabels();
		crf.setWeightsDimensionDensely();

		CRFTrainerByLikelihoodAndGE crfTrainer = new CRFTrainerByLikelihoodAndGE(crf,
				constraintsList, null);
		double gaussianPriorVariance = 1.0;
		crfTrainer.setGaussianPriorVariance(gaussianPriorVariance);
		crfTrainer.train(trainingInstances, unlabeledInstances, Integer.MAX_VALUE);

		System.out.println("============");
		for (FeatureVectorSequence fvSequence : fvSequences) {
			Sequence<?> outputSequence = crf.transduce(fvSequence);
			for (int i = 0; i < outputSequence.size(); i++)
				System.out.println(outputSequence.get(i));
			System.out.println("============");
		}
	}
}
