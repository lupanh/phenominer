package org.nii.phenominer.nlp.splitter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import opennlp.tools.sentdetect.SentenceDetectorFactory;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.model.ModelUtil;

public class SentenceSplitterTrainUtil {
	public static void trainSentenceDetectorModel() throws IOException {
		InputStream in = new FileInputStream("data/trainset/sentencesplitter/biosent.1.0.train");
		OutputStream out = new FileOutputStream("models/sentencesplitter/biosent.1.0.model");

		SentenceModel sentdetectModel = SentenceDetectorME.train("en", new SentenceSampleStream(
				new PlainTextByLineStream(new InputStreamReader(in))), new SentenceDetectorFactory(
				"en", true, null, null), ModelUtil.createTrainingParameters(1000, 1));
		sentdetectModel.serialize(out);

		out.close();
	}

	public static void main(String[] args) throws IOException {
		trainSentenceDetectorModel();
	}

}
