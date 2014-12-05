package org.nii.phenominer.processing.parser;

import org.nii.phenominer.processing.analyzer.AnalyzerWorkflow;
import org.nii.phenominer.processing.analyzer.SentenceSplitterAnalyzer;
import org.nii.phenominer.processing.analyzer.TokenizerAnalyzer;
import org.nii.phenominer.processing.data.BioDocument;
import org.nii.phenominer.processing.data.BioDocumentSection;
import org.nii.phenominer.processing.parser.pubmed.PMC2BioDocument;

import com.cedarsoftware.util.io.JsonWriter;

public class PMC2BioDocumentExample {

	public static void main(String[] args) throws Exception {
		BioDocument document = PMC2BioDocument.create("test/AAPS_J_2008_Aug_7_10(2)_391-400.nxml");

		AnalyzerWorkflow collector = new AnalyzerWorkflow(new SentenceSplitterAnalyzer(),
				new TokenizerAnalyzer());
		collector.analyze(document, BioDocumentSection.SUMMARY);

		System.out.println(JsonWriter.formatJson(document.printJson()));
	}
}
