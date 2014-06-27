package org.nii.phenominer.classify.feature;

import java.util.TreeMap;

public class FeatureSetExample {

	public static void main(String[] args) {
		String strVector1 = "Trương_Thị_May hạnh_phúc diện_kiến Đức_Pháp_Vương_Drukpa";
		String strVector2 = "abt shuttl anf 2G:hu 2G:ut";

		FeatureSet featureSet = new FeatureSet();
		TreeMap<Integer, Integer> vector1 = featureSet.addStringFeatureVector(strVector1, "ABC",
				false);
		TreeMap<Integer, Integer> vector2 = featureSet.addStringFeatureVector(strVector2, "XYZ",
				false);
		System.out.println(featureSet.getWordlist());
		System.out.println(vector1);
		System.out.println(vector2);
		System.out.println(featureSet.getLabels());
	}
}
