package org.nii.phenominer.processing.analyzer;

import org.nii.phenominer.nlp.splitter.SentSplitterMESingleton;
import org.nii.phenominer.processing.data.BioAnalyzerResult;
import org.nii.phenominer.processing.data.BioDocument;
import org.nii.phenominer.processing.data.BioDocumentSection;
import org.nii.phenominer.processing.data.BioSentenceDatum;

public class SentenceSplitterAnalyzer implements Analyzer {
	public static final String NAME = "PhenoMiner Sentence Splitter";
	String PREFIX_DATUM = "sent";
	
	public SentenceSplitterAnalyzer() {
	}
	
	public SentenceSplitterAnalyzer(String prefix) {		
		this.PREFIX_DATUM = prefix;
	}

	public BioAnalyzerResult createAnalyzerResult(BioDocument document, BioDocumentSection section) {
		BioAnalyzerResult result = new BioAnalyzerResult();
		result.setNameAnalyzer(NAME);
		String content = "";

		switch (section) {
		case TITLE:
			result.setSection("title");			
			result.addDatum(PREFIX_DATUM + "0", new BioSentenceDatum<String>(document.getTitle()));
			return result;
		case SUMMARY:
			content = document.getSummary();
			result.setSection("summary");
			break;
		case FULLTEXT:
			result.setSection("fulltext");
			content = document.getFulltext();
			break;
		}
		
		String[] sents = SentSplitterMESingleton.getInstance().split(content);
		for (int i = 0; i < sents.length; i++) {
			BioSentenceDatum<String> datum = new BioSentenceDatum<String>();
			datum.setId(PREFIX_DATUM + i);
			datum.setContent(sents[i]);
			result.addDatum(PREFIX_DATUM + i, datum);
		}			

		return result;
	}

	public String getName() {
		return NAME;
	}

}
