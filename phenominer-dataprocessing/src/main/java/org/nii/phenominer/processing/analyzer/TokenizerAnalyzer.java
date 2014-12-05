package org.nii.phenominer.processing.analyzer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nii.phenominer.nlp.tokenizer.TokenizerMESingleton;
import org.nii.phenominer.processing.data.BioAnalyzerResult;
import org.nii.phenominer.processing.data.BioDatum;
import org.nii.phenominer.processing.data.BioDocument;
import org.nii.phenominer.processing.data.BioDocumentSection;
import org.nii.phenominer.processing.data.BioSentenceDatum;

public class TokenizerAnalyzer implements Analyzer {
	public static final String NAME = "PhenoMiner Tokenizer";
	String PREFIX_DATUM = "sent";

	public TokenizerAnalyzer() {
	}

	public TokenizerAnalyzer(String prefix) {
		this.PREFIX_DATUM = prefix;
	}

	public BioAnalyzerResult createAnalyzerResult(BioDocument document, BioDocumentSection section) {
		BioAnalyzerResult sentsplitter = document.getAnalyzerResults().get(
				SentenceSplitterAnalyzer.NAME);
		if (sentsplitter.getDatums() == null)
			return null;

		BioAnalyzerResult result = new BioAnalyzerResult();
		result.setNameAnalyzer(NAME);

		switch (section) {
		case TITLE:
			result.setSection("title");
			break;
		case SUMMARY:
			result.setSection("summary");
			break;
		case FULLTEXT:
			result.setSection("fulltext");
			break;
		}

		Map<String, BioDatum> sents = sentsplitter.getDatums();
		for (String key : sents.keySet()) {
			BioSentenceDatum<String> datum = new BioSentenceDatum<String>();
			datum.setId(key);
			Object sent = BioSentenceDatum.class.cast(sents.get(key)).getContent();
			String[] tokens = TokenizerMESingleton.getInstance().tokenize((String) sent);
			datum.setContent(StringUtils.join(tokens, " "));
			result.addDatum(key, datum);
		}

		return result;
	}

	public String getName() {
		return NAME;
	}

}
