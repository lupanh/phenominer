package org.nii.phenominer.processing.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.nii.phenominer.processing.data.BioAnalyzerResult;
import org.nii.phenominer.processing.data.BioDocument;
import org.nii.phenominer.processing.data.BioDocumentSection;

public class AnalyzerWorkflow {
	Collection<Analyzer> analyzers;

	public AnalyzerWorkflow(Analyzer... analyzers) {
		this.analyzers = new ArrayList<Analyzer>(analyzers.length);
		Collections.addAll(this.analyzers, analyzers);
		this.analyzers = Collections.unmodifiableCollection(this.analyzers);
	}

	public void analyze(BioDocument document, BioDocumentSection section) {
		for (Analyzer analyzer : analyzers) {
			BioAnalyzerResult result = analyzer.createAnalyzerResult(document, section);
			if (result != null)
				document.addAnalyzerResult(analyzer.getName(), result);
		}
	}

	public Collection<Analyzer> getAnalyzers() {
		return this.analyzers;
	}
}
