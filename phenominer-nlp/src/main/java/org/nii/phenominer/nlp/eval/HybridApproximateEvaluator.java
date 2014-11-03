package org.nii.phenominer.nlp.eval;

import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderEvaluationMonitor;
import opennlp.tools.util.Span;
import opennlp.tools.util.eval.Evaluator;

public class HybridApproximateEvaluator extends Evaluator<NameSample> {
	private ApproximateFMeasure fmeasure = new ApproximateFMeasure();
	private TokenNameFinder maxentFinder;
	private TokenNameFinder ruleFinder;

	public HybridApproximateEvaluator(TokenNameFinder maxentFinder, TokenNameFinder ruleFinder,
			TokenNameFinderEvaluationMonitor... listeners) {
		super(listeners);
		this.maxentFinder = maxentFinder;
		this.ruleFinder = ruleFinder;
	}

	@Override
	protected NameSample processSample(NameSample reference) {

		if (reference.isClearAdaptiveDataSet()) {
			maxentFinder.clearAdaptiveData();
			ruleFinder.clearAdaptiveData();
		}

		Span predictedMaxentNames[] = maxentFinder.find(reference.getSentence());
		Span predictedRuleNames[] = ruleFinder.find(reference.getSentence());
		for (int i = 0; i < predictedRuleNames.length; i++) {
			if (predictedRuleNames[i].getType().equals("default")) {
				predictedRuleNames[i] = new Span(predictedRuleNames[i].getStart(), predictedRuleNames[i].getEnd(), "PHENOTYPE");
			}
		}
		Span references[] = reference.getNames();

		for (int i = 0; i < references.length; i++) {
			if (references[i].getType() == null) {
				references[i] = new Span(references[i].getStart(), references[i].getEnd(),
						"default");
			}
		}

		fmeasure.updateScores(references, predictedRuleNames);

		return new NameSample(reference.getSentence(), predictedRuleNames,
				reference.isClearAdaptiveDataSet());
	}

	public ApproximateFMeasure getFMeasure() {
		return fmeasure;
	}
}
