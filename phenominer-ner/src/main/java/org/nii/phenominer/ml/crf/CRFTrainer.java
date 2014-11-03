package org.nii.phenominer.ml.crf;

import java.io.File;
import java.util.regex.Pattern;

import org.nii.phenominer.nlp.util.FileHelper;

import opennlp.model.SequenceStream;
import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFTrainerByLabelLikelihood;
import cc.mallet.fst.Transducer;
import cc.mallet.fst.TransducerTrainer;
import cc.mallet.types.InstanceList;

public class CRFTrainer {
	CRFTrainerByLabelLikelihood crfTrainer;

	private int[] getOrders() {
		String[] ordersString = "0,1".split(",");
		int[] orders = new int[ordersString.length];
		for (int i = 0; i < ordersString.length; i++) {
			orders[i] = Integer.parseInt(ordersString[i]);
			System.err.println("Orders: " + orders[i]);
		}
		return orders;
	}

	public CRF doTrain(InstanceList trainingData) throws Exception {
		CRF crf = new CRF(trainingData.getDataAlphabet(), trainingData.getTargetAlphabet());

		String startStateName = crf.addOrderNStates(trainingData, getOrders(), (boolean[]) null,
				"other", Pattern.compile("other,*-cont"), null, true);
		crf.getState(startStateName).setInitialWeight(0.0);

		for (int i = 0; i < crf.numStates(); i++) {
			crf.getState(i).setInitialWeight(Transducer.IMPOSSIBLE_WEIGHT);
		}

		crf.getState(startStateName).setInitialWeight(0.0);
		crf.setWeightsDimensionAsIn(trainingData, true);

		crfTrainer = new CRFTrainerByLabelLikelihood(crf);
		crfTrainer.setGaussianPriorVariance(10.0);

		crfTrainer.train(trainingData, Integer.MAX_VALUE);

		return crf;
	}

	public CRF doTrain(SequenceStream trainingSequences) throws Exception {

		InstanceList trainingData = SequenceStreamToInstanceList
				.sequenceStreamToTrainingInstanceList(trainingSequences);

		return doTrain(trainingData);
	}

	public TransducerTrainer getTransducerTrainer() {
		return crfTrainer;
	}

	public void saveModel(CRF crf, String file) throws Exception {
		FileHelper.writeObjectToFile(crf, new File(file));
	}
}
