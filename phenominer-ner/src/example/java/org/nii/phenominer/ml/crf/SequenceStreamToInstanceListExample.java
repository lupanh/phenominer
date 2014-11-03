package org.nii.phenominer.ml.crf;

import java.io.FileInputStream;
import java.nio.charset.Charset;

import opennlp.model.Event;
import opennlp.model.Sequence;
import opennlp.model.SequenceStream;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
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
import org.nii.phenominer.nlp.jeniatagger.Jenia;

import cc.mallet.types.InstanceList;

public class SequenceStreamToInstanceListExample {
	static String trainingPath = "data/trainset/phenoner/phenominer2013.bf.corpus";
	
	public static SequenceStream readData(String file, AdaptiveFeatureGenerator featureGenerator,
			Charset charset) throws Exception {
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(file),
				charset);
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);
		SequenceStream sequences = new NERSequenceStream(sampleStream, featureGenerator);		
		return sequences;
	}
	
	public static void main(String[] args) throws Exception {
		AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
				new AdaptiveFeatureGenerator[] {
						new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new TokenFeatureGenerator(true), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 2, 2), 2, 2),
						new WindowFeatureGenerator(new NgramTokenFeatureGenerator(true, 3, 3), 2, 2),
						new PrefixFeatureGenerator(),
						new SuffixFeatureGenerator(),
						new WordLengthFeatureGenerator(),
						new BigramNameFeatureGenerator(), 
						new OutcomePriorFeatureGenerator(), 
						new PreviousMapFeatureGenerator(),
						new WordLengthFeatureGenerator(), new SentenceFeatureGenerator(true, false) });

		Charset charset = Charset.forName("ISO-8859-1");
		SequenceStream trainingSequences = readData(trainingPath, featureGenerator, charset);
		
		InstanceList trainingInstances = SequenceStreamToInstanceList
				.sequenceStreamToTrainingInstanceList(trainingSequences);
		System.out.println(trainingInstances.getDataAlphabet().size());
	}

}
