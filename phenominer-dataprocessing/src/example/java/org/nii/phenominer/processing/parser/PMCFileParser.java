package org.nii.phenominer.processing.parser;

import java.util.List;

import edu.uwm.pmcarticleparser.PMCArticle;
import edu.uwm.pmcarticleparser.structuralelements.PMCArticleAbstract;
import edu.uwm.pmcarticleparser.structuralelements.PMCArticleFullText;
import edu.uwm.pmcarticleparser.structuralelements.PMCArticleSentence;

public class PMCFileParser {

	public static void main(String[] args) {
		PMCArticle pa = new PMCArticle("test/PMC3280045.xml");

		String title = pa.getTitle();
		if (!title.equals("No Title Found"))
			System.out.println(title + "\n");

		PMCArticleAbstract abs = pa.getAbstract();
		for (PMCArticleSentence s : abs.getAbstractSentences()) {
			System.out.println(s.getText() + "\n");
		}

		PMCArticleFullText ft = pa.getFullText();
		List<PMCArticleSentence> sentences = ft.getFullTextSentences();
		for (PMCArticleSentence sentence : sentences) {
			if (sentence.getInParagraphIndex() == 0) {
				System.out.println();
			}
			System.out.println(sentence.getText());
		}
	}

}
