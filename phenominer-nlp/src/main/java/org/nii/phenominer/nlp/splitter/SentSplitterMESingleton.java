package org.nii.phenominer.nlp.splitter;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentSplitterMESingleton {
	final String modelSplitter = "models/sentencesplitter/biosent.1.0.model";
	SentenceDetectorME splitter;
	static SentSplitterMESingleton ourInstance = new SentSplitterMESingleton();

	SentSplitterMESingleton() {
		splitter = createSentenceDetectorModel();
	}

	public static SentSplitterMESingleton getInstance() {
		return ourInstance;
	}

	public SentenceDetectorME createSentenceDetectorModel() {
		InputStream in;
		try {
			in = new FileInputStream(getClass().getClassLoader().getResource(modelSplitter).getFile());
			SentenceModel sentModel = new SentenceModel(in);

			return new SentenceDetectorME(sentModel);
		} catch (Exception e) {
		}
		return null;
	}

	public String[] split(String text) {
		String[] sents = splitter.sentDetect(text);
		return sents;
	}
}
