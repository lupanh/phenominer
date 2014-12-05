package org.nii.phenominer.nlp.tools.jeniatagger;

import com.google.common.io.Resources;
import com.jmcejuela.bio.jenia.common.Sentence;

public class JeniaTaggerExample {
	public static void main(String[] args) {		
		Jenia.setModelsPath(Resources.getResource("models/genia").getFile());
		String text = "Although the precise mechanisms by which obesity raises blood pressure ( BP ) are not fully understood , there is clear evidence that abnormal kidney function plays a key role in obesity hypertension";
		Sentence sentence = Jenia.analyzeAll(text, true);
		System.out.println(sentence);
	}
}
