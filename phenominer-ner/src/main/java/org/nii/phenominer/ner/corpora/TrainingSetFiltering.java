package org.nii.phenominer.ner.corpora;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.nii.phenominer.nlp.util.FileHelper;

public class TrainingSetFiltering {
	static String fileIn = "data/phenominer/phenominerssl2014.full.bf.corpus";
	static String fileOut = "data/phenominer/phenominerssl2014.full.lite.bf.corpus";

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn),
				"UTF-8"));
		Set<String> content = new HashSet<String>();
		String line = new String();
		while ((line = in.readLine()) != null) {
			if (line.contains("<START:PHENOTYPE>"))
				content.add(line);
		}
		in.close();
		FileHelper.writeSetToFile(content, fileOut);
	}

}
