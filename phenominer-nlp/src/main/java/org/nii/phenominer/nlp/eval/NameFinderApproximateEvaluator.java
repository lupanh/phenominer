package org.nii.phenominer.nlp.eval;

import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderEvaluationMonitor;
import opennlp.tools.util.Span;
import opennlp.tools.util.eval.Evaluator;

public class NameFinderApproximateEvaluator extends Evaluator<NameSample> {
	private ApproximateFMeasure fmeasure = new ApproximateFMeasure();
	private TokenNameFinder nameFinder;
	
	public NameFinderApproximateEvaluator(TokenNameFinder nameFinder, TokenNameFinderEvaluationMonitor... listeners) {
		super(listeners);
		this.nameFinder = nameFinder;
	}
	
	@Override
	protected NameSample processSample(NameSample reference) {

		if (reference.isClearAdaptiveDataSet()) {
			nameFinder.clearAdaptiveData();
		}
		
		Span predictedNames[] = nameFinder.find(reference.getSentence());
		for (int i = 0; i < predictedNames.length; i++) {
			if (predictedNames[i].getType().equals("default")) {
				predictedNames[i] = new Span(predictedNames[i].getStart(), predictedNames[i].getEnd(), "PHENOTYPE");
			}
		}
		
		Span references[] = reference.getNames();

		for (int i = 0; i < references.length; i++) {
			if (references[i].getType() == null) {
				references[i] = new Span(references[i].getStart(), references[i].getEnd(), "default");
			}
		}

		fmeasure.updateScores(references, predictedNames);

		return new NameSample(reference.getSentence(), predictedNames, reference.isClearAdaptiveDataSet());
	}

	public ApproximateFMeasure getFMeasure() {
		return fmeasure;
	}
}
