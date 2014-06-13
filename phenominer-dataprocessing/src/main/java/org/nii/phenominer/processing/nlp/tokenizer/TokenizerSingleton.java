package org.nii.phenominer.processing.nlp.tokenizer;

import java.io.DataInputStream;
import java.io.FileInputStream;

import opennlp.maxent.io.BinaryGISModelReader;
import opennlp.model.AbstractModel;
import opennlp.tools.tokenize.TokenizerFactory;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class TokenizerSingleton {
	TokenizerME tokenizer;
	final String modelTokenizer = "models/tokenizer/biotokenizer.1.0.model";
	static TokenizerSingleton ourInstance = new TokenizerSingleton();

	public TokenizerSingleton() {
		tokenizer = createTokenizerModel();
	}

	public static TokenizerSingleton getInstance() {
		return ourInstance;
	}

	public TokenizerME createTokenizerModel() {
		try {
			AbstractModel model = new BinaryGISModelReader(new DataInputStream(new FileInputStream(
					modelTokenizer))).getModel();
			TokenizerModel tokenizerModel = new TokenizerModel(model, null, new TokenizerFactory(
					"en", null, true, null));
			return new TokenizerME(tokenizerModel);
		} catch (Exception e) {
		}
		return null;
	}

	public String[] tokenize(String text) {
		String[] tokens = tokenizer.tokenize(text);
		return tokens;
	}	
}
