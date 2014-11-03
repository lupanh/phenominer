package org.nii.phenominer.ner.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.nii.phenominer.nlp.matching.BioSpan;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class NERMaxentRecognizer {
	AdaptiveFeatureGenerator featureGenerator;
	NameFinderME nerFinder;
	int beamSize = 3;

	public NERMaxentRecognizer(String modelPath, AdaptiveFeatureGenerator featureGenerator)
			throws Exception {
		this.featureGenerator = featureGenerator;
		loadNERModel(modelPath);
	}

	void loadNERModel(String modelPath) throws Exception {
		InputStream model = new FileInputStream(modelPath);
		loadNERModel(model);
	}

	void loadNERModel(InputStream model) throws Exception {
		TokenNameFinderModel nerModel = new TokenNameFinderModel(model);
		nerFinder = new NameFinderME(nerModel, featureGenerator, beamSize);
	}

	public String recognize(String[] tokens) {
		Span[] spans = nerFinder.find(tokens);
		return BioSpan.getStringNameSample(spans, tokens);
	}

	public static void main(String[] args) throws Exception {
		NERMaxentRecognizer nerFinder = new NERMaxentRecognizer(args[0],
				NERMaxentFactoryExample1.createFeatureGenerator());
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
