package org.nii.phenominer.ml.crf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import opennlp.model.SequenceStream;
import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.StringList;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.BigramNameFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.DictionaryFeatureGenerator;
import opennlp.tools.util.featuregen.OutcomePriorFeatureGenerator;
import opennlp.tools.util.featuregen.PrefixFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.SuffixFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

import org.nii.phenominer.ner.features.JeniaFeatureGenerator;
import org.nii.phenominer.ner.features.NgramTokenFeatureGenerator;
import org.nii.phenominer.ner.features.TrigramNameFeatureGenerator;
import org.nii.phenominer.ner.features.WordLengthFeatureGenerator;
import org.nii.phenominer.nlp.jeniatagger.Jenia;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.nlp.util.FileHelper;

import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFTrainerByLabelLikelihood;
import cc.mallet.fst.MEMM;
import cc.mallet.fst.PerClassAccuracyEvaluator;
import cc.mallet.fst.SegmentationEvaluator;
import cc.mallet.fst.TokenAccuracyEvaluator;
import cc.mallet.fst.Transducer;
import cc.mallet.fst.TransducerEvaluator;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelAlphabet;

public class CRFTrainerExample {
	static String trainingPath = "data/trainset/phenoner/phenominer2012.full.corpus";
	static String testingPath = "data/trainset/phenoner/phenominer2013.full.corpus";

	static Dictionary loadDictionary(String file) throws Exception {
		Dictionary dict = new Dictionary(false);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),
				"UTF-8"));
		String line = new String();
		while ((line = in.readLine()) != null) {
			dict.put(new StringList(TokenizerSingleton.getInstance().tokenize(line)));
		}
		in.close();
		return dict;
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

	public static CRF getCRF(InstanceList trainingInstances) {
		CRF crf = new CRF(trainingInstances.getDataAlphabet(),
				trainingInstances.getTargetAlphabet());

		String startStateName = crf.addOrderNStates(trainingInstances, getOrders(),
				(boolean[]) null, "other", Pattern.compile("other,*-cont"), null, true);
		crf.getState(startStateName).setInitialWeight(0.0);

		for (int i = 0; i < crf.numStates(); i++) {
			crf.getState(i).setInitialWeight(Transducer.IMPOSSIBLE_WEIGHT);
		}

		crf.getState(startStateName).setInitialWeight(0.0);
		crf.setWeightsDimensionAsIn(trainingInstances, true);

		return crf;
	}

	public static AdaptiveFeatureGenerator getFeatureGenerators() throws Exception {
		Jenia.setModelsPath("models/genia");
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
						//new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
						//new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 3, 3), 2, 2),
						new WindowFeatureGenerator(new JeniaFeatureGenerator(), 3, 3),
						// new DictionaryFeatureGenerator("HPO",
						// loadDictionary("data/dictionary/hpo.txt")),
						// new DictionaryFeatureGenerator("PATO",
						// loadDictionary("data/dictionary/pato.txt")),
						// new DictionaryFeatureGenerator("FMA",
						// loadDictionary("data/dictionary/fma3.2.txt")),
						// new DictionaryFeatureGenerator("GO",
						// loadDictionary("data/dictionary/go.txt")),
						// new DictionaryFeatureGenerator("MP",
						// loadDictionary("data/dictionary/mp.txt")),
						// new DictionaryFeatureGenerator("JOCHEM",
						// loadDictionary("data/dictionary/jochem.txt")),
						// new DictionaryFeatureGenerator("CHEBI",
						// loadDictionary("data/dictionary/chebi.txt")),
						// new DictionaryFeatureGenerator("UMLS",
						// loadDictionary("data/dictionary/umls.txt")),
						new BigramNameFeatureGenerator(),
						new TrigramNameFeatureGenerator(),
						new PrefixFeatureGenerator(), new SuffixFeatureGenerator(),
						new WordLengthFeatureGenerator(), new SentenceFeatureGenerator(true, false) 
						});
		return featureGenerator;
	}

	public static void main(String[] args) throws Exception {

		Charset charset = Charset.forName("ISO-8859-1");
		SequenceStream trainingSequences = readData(trainingPath, getFeatureGenerators(), charset);
		InstanceList trainingInstances = SequenceStreamToInstanceList
				.sequenceStreamToTrainingInstanceList(trainingSequences);

		CRF crf = getCRF(trainingInstances);
		CRFTrainerByLabelLikelihood crfTrainer = new CRFTrainerByLabelLikelihood(crf);
		crfTrainer.setGaussianPriorVariance(1.0);

		crfTrainer.train(trainingInstances, Integer.MAX_VALUE);
		FileHelper.writeObjectToFile(crf, new File("models/phenoner/phenominer2012.full.crf"));

		SequenceStream testingSequences = readData(testingPath, getFeatureGenerators(), charset);
		InstanceList testingInstances = SequenceStreamToInstanceList
				.sequenceStreamToTestingInstanceList(testingSequences,
						trainingInstances.getDataAlphabet(),
						(LabelAlphabet) trainingInstances.getTargetAlphabet());

		System.out.println("=============");
		TransducerEvaluator eval = new PerClassAccuracyEvaluator(testingInstances, "testing");
		eval.evaluate(crfTrainer);
		System.out.println("=============");
		eval = new TokenAccuracyEvaluator(testingInstances, "testing");
		eval.evaluate(crfTrainer);
		System.out.println("=============");
		eval = new SegmentationEvaluator(testingInstances, "testing");
		eval.evaluate(crfTrainer);
	}
}
