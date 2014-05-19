package org.nii.phenominer.processing.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import opennlp.maxent.io.BinaryGISModelReader;
import opennlp.model.AbstractModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class BioTokenizer {
	TokenizerME tokenizer;
	final String modelTokenizer = "models/tokenizer/biotok.model";
	static BioTokenizer ourInstance = new BioTokenizer();

	public BioTokenizer() {
		tokenizer = createTokenizerModel();
	}

	public static BioTokenizer getInstance() {
		return ourInstance;
	}

	@SuppressWarnings("deprecation")
	public TokenizerME createTokenizerModel() {
		try {
			AbstractModel model = new BinaryGISModelReader(new DataInputStream(new FileInputStream(
					modelTokenizer))).getModel();
			TokenizerModel packageModel = new TokenizerModel("en", model, true);

			return new TokenizerME(packageModel);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] tokenize(String text) {
		String[] tokens = tokenizer.tokenize(text);
		return tokens;
	}
}
