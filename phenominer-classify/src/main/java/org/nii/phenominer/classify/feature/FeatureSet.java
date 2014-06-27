package org.nii.phenominer.classify.feature;

import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class FeatureSet implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	TObjectIntHashMap<String> wordlist;
	ArrayList<String> labels;

	int labelKey = 0;

	public FeatureSet() {
		wordlist = new TObjectIntHashMap<String>();
		wordlist.put("NO_USE", 0);
		labels = new ArrayList<String>();
	}

	public TObjectIntHashMap<String> getWordlist() {
		return wordlist;
	}

	public void setWordlist(TObjectIntHashMap<String> wordlist) {
		this.wordlist = wordlist;
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	public int getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(int labelKey) {
		this.labelKey = labelKey;
	}

	public TreeMap<Integer, Integer> addStringFeatureVector(String[] strFeatures, String label,
			boolean flagTest) {
		HashSet<String> setFeatures = new HashSet<String>();
		TreeMap<Integer, Integer> vector = new TreeMap<Integer, Integer>();

		for (String feature : strFeatures)
			setFeatures.add(feature);
		if (setFeatures.size() == 0)
			return null;

		if (!label.equals(""))
			if (labels.contains(label))
				vector.put(labelKey, labels.indexOf(label));
			else {
				if (!flagTest) {
					labels.add(label);
					vector.put(labelKey, labels.indexOf(label));
				} else {
					// throw new
					// IllegalArgumentException("Label of Testing Data is error!!!");
					return null;
				}

			}

		for (String feature : setFeatures) {
			if (wordlist.contains(feature)) {
				vector.put(wordlist.get(feature), 1);
			} else {
				if (!flagTest) {
					wordlist.put(feature, wordlist.size());
					vector.put(wordlist.get(feature), 1);
				}
			}
		}

		return vector;
	}

	public TreeMap<Integer, Integer> addStringFeatureVector(ArrayList<String> sFeatures,
			String label, boolean isTest) {
		if (sFeatures == null)
			return null;
		return addStringFeatureVector(sFeatures.toArray(new String[sFeatures.size()]), label,
				isTest);
	}

	public TreeMap<Integer, Integer> addStringFeatureVector(String sFeatures, String label,
			boolean flagTest) {
		return addStringFeatureVector(sFeatures.split(" "), label, flagTest);
	}

	public String addprintVector(ArrayList<String> sFeatures, String label, boolean flagTest) {
		TreeMap<Integer, Integer> vector = addStringFeatureVector(sFeatures, label, flagTest);
		if (vector == null)
			return "";

		String text = "" + vector.get(labelKey);
		for (int key : vector.keySet()) {
			if (key == labelKey)
				continue;
			text += " " + key + ":" + vector.get(key);
		}
		return text;
	}
}
