package org.nii.phenominer.nlp.tokenizer;

import java.io.File;
import java.nio.charset.Charset;

import org.nii.phenominer.nlp.splitter.SentSplitterMESingleton;
import org.nii.phenominer.nlp.util.FileHelper;

public class TokenizerExample2 {
	static String folderTest = "corpus/PubmedOMIM";

	public static void main(String[] args) throws Exception {
		new File("sentenceExample2.txt").delete();
		File folder = new File(folderTest);
		for (File file : folder.listFiles()) {
			System.out.println(file.getName());
			String content = "FILE: " + file.getName() + "\n";
			String text = FileHelper.readFileAsString(file, Charset.forName("UTF-8"));
			String[] sents = SentSplitterMESingleton.getInstance().split(text);
			for (String sent : sents) {
				String[] tokens = TokenizerMESingleton.getInstance().tokenize(sent);
				for (String token : tokens)
					content += token + " ";
				content += "\n";
			}

			FileHelper.appendToFile(content, new File("sentenceExample2.txt"),
					Charset.forName("UTF-8"));
		}
	}
}
