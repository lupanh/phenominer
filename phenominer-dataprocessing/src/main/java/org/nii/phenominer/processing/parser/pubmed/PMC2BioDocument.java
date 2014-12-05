package org.nii.phenominer.processing.parser.pubmed;

import org.joda.time.DateTime;
import org.nii.phenominer.processing.data.BioDocument;

public class PMC2BioDocument {
	public static BioDocument create(String file) {
		BioDocument document = new BioDocument();
		PMCArticleParser parser = new PMCArticleParser(file);

		String pmcid = parser.getPmcId();
		if (!pmcid.equals(""))
			document.setId("PMC" + pmcid);
		else {
			pmcid = "";
			document.setId("PMC" + parser.hashCode());
		}

		document.addPubId("pmc-id", pmcid);
		document.addPubId("pubmed-id", parser.getPubmedId());
		document.addPubId("doi", parser.getDoiId());

		String title = parser.getTitle();
		document.setTitle(title);

		String summary = parser.getAbstractText();
		document.setSummary(summary);

		document.setCreateDate(DateTime.now().toString());

		return document;
	}
}
