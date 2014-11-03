package org.nii.phenominer.ml.crf;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.nii.phenominer.nlp.util.FileHelper;

import opennlp.model.SequenceStream;
import cc.mallet.fst.CRF;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Sequence;

public class CRFClassifierExample {
	static String testingPath = "data/trainset/phenoner/test.txt";

	public static void main(String[] args) throws Exception {
		CRF crf = (CRF) FileHelper.readObjectFromFile(new File("models/phenoner/phenominer2012.full.crf"));
		Alphabet dataAlphabet = crf.getInputPipe().getAlphabet();
		
		Charset charset = Charset.forName("ISO-8859-1");

		SequenceStream testingSequences = CRFTrainerExample.readData(testingPath, CRFTrainerExample.getFeatureGenerators(), charset);
		List<FeatureVectorSequence> fvSequences = SequenceStreamToInstanceList
				.sequenceStreamToClassifyInstanceList(testingSequences, dataAlphabet);
		
		for (FeatureVectorSequence fvSequence : fvSequences) {			
			System.out.println(fvSequence);
			Sequence<?> outputSequence = crf.transduce(fvSequence);
			for (int i = 0; i < outputSequence.size(); i++)
				System.out.println(outputSequence.get(i));
			//for (int i = 0; i < outputSequence.size(); i++)
			//	System.out.println(outputSequence.get(i));
			System.out.println("============");
		}			
	}
}
