package org.nii.phenominer.ner.features;

import java.util.List;

import org.nii.phenominer.nlp.tools.jeniatagger.Jenia;

import com.jmcejuela.bio.jenia.common.Sentence;

import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;

public class JeniaFeatureGenerator extends FeatureGeneratorAdapter {
	private String currentSentence[];
	private Sentence sentence;

	@Override
	public void createFeatures(List<String> features, String[] tokens, int index, String[] previousOutcomes) {
		if (currentSentence != tokens) {
			currentSentence = tokens;
			sentence = Jenia.analyzeAll(tokens, true);
		}
		if (sentence != null) {
			String geniaLemma = sentence.get(index).baseForm.toLowerCase();
			String geniaPos = sentence.get(index).pos;
			String geniaChunk = sentence.get(index).chunk;
			String geniaNE = sentence.get(index).ne;
			features.add("genia" + ":w=lemma=" + geniaLemma);
			features.add("genia" + ":w=pos=" + geniaPos);
			features.add("genia" + ":w=chunk=" + geniaChunk);
			features.add("genia" + ":w=ne=" + geniaNE);
		}	
	}
}
