package org.nii.phenominer.ner.matching;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;

import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;

public class DictionaryNameFinderCorpusExample {
	static TokenNameFinder mNameFinder;

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

	public static void main(String[] args) throws Exception {
		mNameFinder = new DictionaryNameFinder(loadDictionary("data/dictionary/hpo.txt"));
		InputStream in = new FileInputStream("data/phenominer/khordad.corpus");
		ObjectStream<String> testingStream = new PlainTextByLineStream(new InputStreamReader(in));
		ObjectStream<NameSample> testStream = new NameSampleDataStream(testingStream);
		NameSample sample;
		while ((sample = testStream.read()) != null) {
			Span[] spans = mNameFinder.find(sample.getSentence());
			System.out.println(spans.length);
		}

	}

}
