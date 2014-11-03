package org.nii.phenominer.ml.crf;

import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameContextGenerator;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class CRFContextGenerator implements NameContextGenerator {
	private AdaptiveFeatureGenerator featureGenerators[];

	public CRFContextGenerator(AdaptiveFeatureGenerator... featureGenerators) {

		if (featureGenerators != null) {
			this.featureGenerators = featureGenerators;
		}
	}

	public void addFeatureGenerator(AdaptiveFeatureGenerator generator) {
		AdaptiveFeatureGenerator generators[] = featureGenerators;

		featureGenerators = new AdaptiveFeatureGenerator[featureGenerators.length + 1];

		System.arraycopy(generators, 0, featureGenerators, 0, generators.length);

		featureGenerators[featureGenerators.length - 1] = generator;
	}

	public void updateAdaptiveData(String[] tokens, String[] outcomes) {

		if (tokens != null && outcomes != null && tokens.length != outcomes.length) {
			throw new IllegalArgumentException(
					"The tokens and outcome arrays MUST have the same size!");
		}

		for (AdaptiveFeatureGenerator featureGenerator : featureGenerators) {
			featureGenerator.updateAdaptiveData(tokens, outcomes);
		}
	}

	public void clearAdaptiveData() {
		for (AdaptiveFeatureGenerator featureGenerator : featureGenerators) {
			featureGenerator.clearAdaptiveData();
		}
	}

	public String[] getContext(int index, String[] tokens, String[] preds,
			Object[] additionalContext) {
		List<String> features = new ArrayList<String>();

		for (AdaptiveFeatureGenerator featureGenerator : featureGenerators) {
			featureGenerator.createFeatures(features, tokens, index, preds);
		}

		return features.toArray(new String[features.size()]);
	}
}
