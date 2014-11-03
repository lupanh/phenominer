package org.nii.phenominer.nlp.matching;

import java.util.HashMap;

import org.nii.phenominer.nlp.matching.BioSpan;
import org.nii.phenominer.nlp.matching.LongestMatching;

public class LongestMatchingExample {
	public static HashMap<String, String> dict = new HashMap<String, String>();

	public static void main(String[] args) {
		dict.put("kidney", "ANATOMY");
		dict.put("abnormal kidney", "PHENOTYPE");
		dict.put("kidney abnormality", "PHENOTYPE");

		String sentence = "Abnormal kidney is a phenotype entity";
		String tokens[] = sentence.split("[^\\w]+");
		LongestMatching matching = new LongestMatching(dict);
		BioSpan[] spans = matching.tagging(tokens, -1, true);
		System.out.println(BioSpan.getStringAnnotated(spans, tokens));
	}

}
