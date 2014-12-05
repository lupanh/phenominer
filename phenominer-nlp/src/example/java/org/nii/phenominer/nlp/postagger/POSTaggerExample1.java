package org.nii.phenominer.nlp.postagger;

import opennlp.tools.tokenize.WhitespaceTokenizer;

public class POSTaggerExample1 {

	public static void main(String[] args) {
		String sentenceString = "increased brown adipose tissue amount";
		String tags[] = POSTaggerSingleton.getInstance().tagging(WhitespaceTokenizer.INSTANCE.tokenize(sentenceString));

		for (String tag : tags)
			System.out.print(tag + " ");
	}
}
