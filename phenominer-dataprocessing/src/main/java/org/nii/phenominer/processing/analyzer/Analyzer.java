package org.nii.phenominer.processing.analyzer;

import org.nii.phenominer.processing.data.BioAnalyzerResult;
import org.nii.phenominer.processing.data.BioDocument;
import org.nii.phenominer.processing.data.BioDocumentSection;

public interface Analyzer {
	BioAnalyzerResult createAnalyzerResult(BioDocument document, BioDocumentSection section);
	String getName();
}
