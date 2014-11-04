package org.nii.phenominer.ner.eval;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
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
import org.nii.phenominer.ner.features.WordLengthFeatureGenerator;
import org.nii.phenominer.nlp.eval.ApproximateFMeasure;
import org.nii.phenominer.nlp.eval.NameFinderApproximateEvaluator;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.nlp.tools.jeniatagger.Jenia;

public class KhordadMaxentCrossValidationExample {
	static int numValidations = 10;
	static int iterator = 100;
	static int cutoff = 2;

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
						new DictionaryFeatureGenerator("HPO", loadDictionary("data/dictionary/hpo.txt")),
						new DictionaryFeatureGenerator("PATO", loadDictionary("data/dictionary/pato.txt")),
						new DictionaryFeatureGenerator("FMA", loadDictionary("data/dictionary/fma3.2.txt")),
						new PrefixFeatureGenerator(), new SuffixFeatureGenerator(),
						new WordLengthFeatureGenerator(), new BigramNameFeatureGenerator(),
						new OutcomePriorFeatureGenerator(), new PreviousMapFeatureGenerator(),
						new SentenceFeatureGenerator(true, false) });
		return featureGenerator;
	}

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("data/phenominer/khordad.corpus");
		Charset charset = Charset.forName("UTF-8");

		BufferedReader buffin = new BufferedReader(new InputStreamReader(in, charset));
		String line = "";
		List<String> lines = new ArrayList<String>();
		while ((line = buffin.readLine()) != null) {
			if (line.trim().equals(""))
				continue;
			lines.add(line);
		}
		buffin.close();
		Collections.shuffle(lines);

		double avgPrecision = 0.0;
		double avgRecall = 0.0;
		double avgF1 = 0.0;

		double index = 0.0;
		double increment = lines.size() / (double) numValidations;

		PrintWriter outTotal = new PrintWriter(new FileWriter("temp/10folds/total.output"));

		for (int i = 0; i < numValidations; i++) {
			int start = (int) Math.round(index);
			int end = (int) Math.round(index + increment);

			StringBuffer test = new StringBuffer();
			StringBuffer train = new StringBuffer();

			for (int j = 0; j < lines.size(); j++) {
				if (j >= start && j < end)
					test.append(lines.get(j) + "\n");
				else
					train.append(lines.get(j) + "\n");
			}

			InputStream is;
			// Training
			PrintWriter out = new PrintWriter(new FileWriter("temp/10folds/cv" + i + ".output"));
			// out.write("=====================Train set====================\n");

			is = new ByteArrayInputStream(train.toString().getBytes());
			InputStream training = new ByteArrayInputStream(train.toString().getBytes());
			ObjectStream<String> trainingStream = new PlainTextByLineStream(training, charset);
			ObjectStream<NameSample> trainStream = new NameSampleDataStream(trainingStream);
			TokenNameFinderModel nerModel = NameFinderME.train("en", "PHENOTYPE", trainStream,
					createFeatureGenerator(), Collections.<String, Object> emptyMap(), iterator,
					cutoff);

			NameFinderApproximateEvaluator evaluator = new NameFinderApproximateEvaluator(
					new NameFinderME(nerModel));

			is = new ByteArrayInputStream(test.toString().getBytes());
			ObjectStream<String> testingStream = new PlainTextByLineStream(new InputStreamReader(
					is, charset));
			ObjectStream<NameSample> testStream = new NameSampleDataStream(testingStream);
			evaluator.evaluate(testStream);
			ApproximateFMeasure result = evaluator.getFMeasure();
			System.out.println(result.toString());

			avgPrecision += evaluator.getFMeasure().getPrecisionScore();
			avgRecall += evaluator.getFMeasure().getRecallScore();
			avgF1 += evaluator.getFMeasure().getFMeasure();

			System.out.println();
			index += increment;

			out.write("=====================Results====================\n");
			out.write(evaluator.getFMeasure().toString());

			outTotal.write("=====================CV_" + i + "====================\n");
			outTotal.write(evaluator.getFMeasure().toString());
			out.close();
		}

		outTotal.write("=====================Total====================\n");
		outTotal.write("Average Precision: " + (double) avgPrecision / numValidations + "\n");
		outTotal.write("Average Recall: " + (double) avgRecall / numValidations + "\n");
		outTotal.write("Average F-Measure: " + (double) avgF1 / numValidations + "\n");
		outTotal.close();
	}
}
