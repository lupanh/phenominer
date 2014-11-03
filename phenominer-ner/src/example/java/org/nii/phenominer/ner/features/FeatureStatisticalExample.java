package org.nii.phenominer.ner.features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import opennlp.model.Event;
import opennlp.model.Sequence;
import opennlp.model.SequenceStream;
import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.StringList;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.DictionaryFeatureGenerator;
import opennlp.tools.util.featuregen.PrefixFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.SuffixFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

import org.nii.phenominer.ml.crf.NERSequenceStream;
import org.nii.phenominer.ner.util.FileHelper;
import org.nii.phenominer.nlp.jeniatagger.Jenia;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;

public class FeatureStatisticalExample {
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

	public static AdaptiveFeatureGenerator getFeatureGenerators() throws Exception {
		Jenia.setModelsPath("models/genia");
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 1, 2), 2, 2),
						new WindowFeatureGenerator(new JeniaFeatureGenerator(), 3, 3),
						//new DictionaryFeatureGenerator("HPO",
						//		loadDictionary("data/dictionary/hpo.txt")),
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
						new PrefixFeatureGenerator(), new SuffixFeatureGenerator(),
						new WordLengthFeatureGenerator(), new SentenceFeatureGenerator(true, false) });
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

	public static FeatureStatistical generateConstraints(SequenceStream trainingSequences) {
		FeatureStatistical featureStat = new FeatureStatistical();
		for (Sequence<?> sequence : trainingSequences) {
			Event events[] = sequence.getEvents();

			for (int eventIndex = 0; eventIndex < events.length; eventIndex++) {
				Event event = events[eventIndex];

				String features[] = event.getContext();

				String label = event.getOutcome();
				if (label.contains("-start")) {
					label = "B-" + label.replace("-start", "");
				} else if (label.contains("-cont"))
					label = "I-" + label.replace("-cont", "");

				for (String feature : features)
					featureStat.addFeatureLabel(feature, label);
			}
		}

		return featureStat;
	}

	static String trainingPath = "data/trainset/phenoner/phenominer2012.full.corpus";

	public static void main(String[] args) throws Exception {
		Charset charset = Charset.forName("ISO-8859-1");
		SequenceStream trainingSequences = readData(trainingPath, getFeatureGenerators(), charset);

		FileHelper.writeToFile(generateConstraints(trainingSequences).toString(), new File(
				"contrainstPhenominer2012.txt"), charset);
	}
}
