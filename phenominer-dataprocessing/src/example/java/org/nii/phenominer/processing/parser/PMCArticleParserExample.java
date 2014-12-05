package org.nii.phenominer.processing.parser;

import java.util.List;

import org.nii.phenominer.processing.data.pubmed.PMCArticleAbstract;
import org.nii.phenominer.processing.data.pubmed.PMCArticleFullText;
import org.nii.phenominer.processing.data.pubmed.PMCArticleSentence;
import org.nii.phenominer.processing.parser.pubmed.PMCArticleParser;

public class PMCArticleParserExample {

	public static void main(String[] args) {
		PMCArticleParser pa = new PMCArticleParser("test/AAPS_J_2008_Apr_2_10(1)_193-199.nxml");

		String title = pa.getTitle();
		if (!title.equals(""))
			System.out.println(title);
		
		System.out.println("ABSTRACT:");
		PMCArticleAbstract abs = pa.getAbstract();
		for (PMCArticleSentence s : abs.getAbstractSentences()) {
			System.out.println(s.getText());
		}

		System.out.println("CONTEXT:");
		PMCArticleFullText ft = pa.getFullText();
		List<PMCArticleSentence> sentences = ft.getFullTextSentences();
		for (PMCArticleSentence sentence : sentences) {
			if (sentence.getInParagraphIndex() == 0) {
				System.out.println();
			}
			System.out.println(sentence.getText());
		}
		
		System.out.println(pa.getAuthors().get(0).getFirstName());
	}

}
