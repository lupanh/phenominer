package org.nii.phenominer.processing.parser;

import java.io.File;
import java.io.FileInputStream;

import org.nii.phenominer.processing.data.pubmeddtd.Article;
import org.nii.phenominer.processing.parser.pubmed.PMCXmlJAXBHelper;
import org.nii.phenominer.processing.parser.pubmed.PMCXmlJAXBParser;

public class PMCXmlJAXBParserExample {

	public static void main(String[] args) throws Exception {
		String nxmlFile = "test/AAPS_J_2008_Apr_2_10(1)_193-199.nxml";
		Article article = new PMCXmlJAXBParser().parse(new FileInputStream(new File(nxmlFile)));
		System.out.println(PMCXmlJAXBHelper.extractText(article));
	}
}
