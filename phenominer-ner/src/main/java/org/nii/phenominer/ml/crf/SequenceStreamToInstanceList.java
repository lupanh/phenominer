package org.nii.phenominer.ml.crf;

import java.util.ArrayList;
import java.util.List;

import opennlp.model.Event;
import opennlp.model.Sequence;
import opennlp.model.SequenceStream;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVector;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.LabelSequence;
import edu.stanford.nlp.util.ArrayUtils;

public class SequenceStreamToInstanceList {
	public static InstanceList sequenceStreamToTrainingInstanceList(SequenceStream sequences) {
		Alphabet dataAlphabet = new Alphabet();
		LabelAlphabet targetAlphabet = new LabelAlphabet();

		InstanceList trainingData = new InstanceList(dataAlphabet, targetAlphabet);

		int nameIndex = 0;
		for (Sequence<?> sequence : sequences) {
			FeatureVector featureVectors[] = new FeatureVector[sequence.getEvents().length];
			Label malletOutcomes[] = new Label[sequence.getEvents().length];

			Event events[] = sequence.getEvents();

			for (int eventIndex = 0; eventIndex < events.length; eventIndex++) {

				Event event = events[eventIndex];

				String features[] = event.getContext();
				int malletFeatures[] = new int[features.length];

				for (int featureIndex = 0; featureIndex < features.length; featureIndex++) {
					malletFeatures[featureIndex] = dataAlphabet.lookupIndex(features[featureIndex],
							true);
				}

				featureVectors[eventIndex] = new FeatureVector(dataAlphabet, malletFeatures);

				String label = event.getOutcome();
				if (label.contains("-start")) {
					label = "B-" + label.replace("-start", "");
				} else if (label.contains("-cont"))
					label = "I-" + label.replace("-cont", "");

				malletOutcomes[eventIndex] = targetAlphabet.lookupLabel(label, true);
			}

			LabelSequence malletOutcomeSequence = new LabelSequence(malletOutcomes);

			FeatureVectorSequence malletSequence = new FeatureVectorSequence(featureVectors);

			trainingData.add(new Instance(malletSequence, malletOutcomeSequence, "name"
					+ nameIndex++, "source"));
		}
		return trainingData;
	}

	public static InstanceList sequenceStreamToTestingInstanceList(SequenceStream sequences,
			Alphabet dataAlphabet, LabelAlphabet targetAlphabet) {
		InstanceList testingData = new InstanceList(dataAlphabet, targetAlphabet);

		int nameIndex = 0;
		for (Sequence<?> sequence : sequences) {
			FeatureVector featureVectors[] = new FeatureVector[sequence.getEvents().length];
			Label malletOutcomes[] = new Label[sequence.getEvents().length];

			Event events[] = sequence.getEvents();

			for (int eventIndex = 0; eventIndex < events.length; eventIndex++) {

				Event event = events[eventIndex];

				String features[] = event.getContext();
				ArrayList<Integer> malletFeatures = new ArrayList<Integer>();

				for (int featureIndex = 0; featureIndex < features.length; featureIndex++) {
					int index = dataAlphabet.lookupIndex(features[featureIndex], false);
					if (index != -1)
						malletFeatures.add(index);
				}

				featureVectors[eventIndex] = new FeatureVector(dataAlphabet,
						ArrayUtils.toPrimitive(malletFeatures.toArray(new Integer[malletFeatures
								.size()])));

				String label = event.getOutcome();
				if (label.contains("-start")) {
					label = "B-" + label.replace("-start", "");
				} else if (label.contains("-cont"))
					label = "I-" + label.replace("-cont", "");

				malletOutcomes[eventIndex] = targetAlphabet.lookupLabel(label, false);
			}

			LabelSequence malletOutcomeSequence = new LabelSequence(malletOutcomes);

			FeatureVectorSequence malletSequence = new FeatureVectorSequence(featureVectors);

			testingData.add(new Instance(malletSequence, malletOutcomeSequence, "name"
					+ nameIndex++, "source"));
		}

		return testingData;
	}

	public static List<FeatureVectorSequence> sequenceStreamToClassifyInstanceList(
			SequenceStream sequences, Alphabet dataAlphabet) {
		List<FeatureVectorSequence> fvSequence = new ArrayList<FeatureVectorSequence>();
		for (Sequence<?> sequence : sequences) {
			FeatureVector featureVectors[] = new FeatureVector[sequence.getEvents().length];

			Event events[] = sequence.getEvents();

			for (int eventIndex = 0; eventIndex < events.length; eventIndex++) {

				Event event = events[eventIndex];

				String features[] = event.getContext();
				ArrayList<Integer> malletFeatures = new ArrayList<Integer>();

				for (int featureIndex = 0; featureIndex < features.length; featureIndex++) {
					int index = dataAlphabet.lookupIndex(features[featureIndex], false);
					if (index != -1)
						malletFeatures.add(index);
				}

				featureVectors[eventIndex] = new FeatureVector(dataAlphabet,
						ArrayUtils.toPrimitive(malletFeatures.toArray(new Integer[malletFeatures
								.size()])));
			}

			FeatureVectorSequence malletSequence = new FeatureVectorSequence(featureVectors);
			fvSequence.add(malletSequence);
		}

		return fvSequence;
	}
}
