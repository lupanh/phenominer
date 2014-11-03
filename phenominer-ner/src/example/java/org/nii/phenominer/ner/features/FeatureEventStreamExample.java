package org.nii.phenominer.ner.features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import opennlp.model.EventStream;
import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DefaultNameContextGenerator;
import opennlp.tools.namefind.NameFinderEventStream;
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

import org.nii.phenominer.nlp.jeniatagger.Jenia;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.nlp.util.FileHelper;

public class FeatureEventStreamExample {
	static String trainingPath = "test1.txt";
	
	static Dictionary loadDictionary(String file) throws Exception {
		Dictionary dict = new Dictionary(false);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line = new String();
		while ((line = in.readLine()) != null) {
			dict.put(new StringList(TokenizerSingleton.getInstance().tokenize(line)));
		}
		in.close();
		return dict;
	}
	
	public static void main(String[] args) throws Exception {
		Jenia.setModelsPath("models/genia");
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(new AdaptiveFeatureGenerator[] {
				new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
				new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
				new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
				new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 3, 3), 2, 2),
				new WindowFeatureGenerator(new JeniaFeatureGenerator(), 2, 2),
				//new DictionaryFeatureGenerator("HPO", loadDictionary("data/dictionary/hpo.txt")),
				//new DictionaryFeatureGenerator("PATO", loadDictionary("data/dictionary/pato.txt")),
				//new DictionaryFeatureGenerator("FMA", loadDictionary("data/dictionary/fma3.2.txt")),
				new PrefixFeatureGenerator(),
				new SuffixFeatureGenerator(),
				new WordLengthFeatureGenerator(),
				//new BigramNameFeatureGenerator(), 
				//new OutcomePriorFeatureGenerator(), 
				//new PreviousMapFeatureGenerator(),
				new SentenceFeatureGenerator(true, false) 
		});

		Charset charset = Charset.forName("ISO-8859-1");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(trainingPath), charset);
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
		
		EventStream es = new NameFinderEventStream(sampleStream, "EN", new DefaultNameContextGenerator(featureGenerator));
		
		while (es.hasNext()) {
			FileHelper.appendToFile(es.next().toString() + "\n", new File("features.txt"), charset);
		}
	}
}
