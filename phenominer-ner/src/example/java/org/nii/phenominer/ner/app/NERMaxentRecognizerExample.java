package org.nii.phenominer.ner.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.util.StringList;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.BigramNameFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
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
import org.nii.phenominer.ner.features.WordLengthFeatureGenerator;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.nlp.tools.jeniatagger.Jenia;

public class NERMaxentRecognizerExample {
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

	static AdaptiveFeatureGenerator createFeatureGenerator() throws Exception {
		Jenia.setModelsPath("models/genia");
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 3, 3), 2, 2),
						new WindowFeatureGenerator(new JeniaFeatureGenerator(), 2, 2),
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
						new PrefixFeatureGenerator(), new SuffixFeatureGenerator(),
						new WordLengthFeatureGenerator(), new BigramNameFeatureGenerator(),
						new OutcomePriorFeatureGenerator(), new PreviousMapFeatureGenerator(),
						new SentenceFeatureGenerator(true, false) });
		return featureGenerator;
	}

	public static void main(String[] args) throws Exception {
		NERMaxentRecognizer nerFinder = new NERMaxentRecognizer(
				"models/phenoner/khordad.model", NERMaxentFactoryExample1.createFeatureGenerator());
		System.out.print("Enter your sentence: ");
		Scanner scan = new Scanner(System.in);

		while (scan.hasNext()) {
			String text = scan.nextLine();
			if (text.equals("exit")) {
				break;
			}
			String[] tokens = TokenizerSingleton.getInstance().tokenize(text);
			String output = nerFinder.recognize(tokens);
			System.out.println(output);
			System.out.print("Enter your sentence: ");

		}
		scan.close();
	}

}
