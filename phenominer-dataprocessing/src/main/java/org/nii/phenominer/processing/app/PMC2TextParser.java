package org.nii.phenominer.processing.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.nii.phenominer.processing.data.pubmed.PMCArticleAbstract;
import org.nii.phenominer.processing.data.pubmed.PMCArticleFullText;
import org.nii.phenominer.processing.data.pubmed.PMCArticleSentence;
import org.nii.phenominer.processing.parser.pubmed.PMCArticleParser;

public class PMC2TextParser {
	static void copyFolder(File src, File dest) throws Exception {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else {
			extract(src.toString(), dest.toString());
		}
	}

	static void extract(String inputFile, String outputFile) throws Exception {
		PMCArticleParser pa = new PMCArticleParser(inputFile);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				outputFile.substring(0, outputFile.lastIndexOf(File.separator)) + "/pmc"
						+ pa.getPmcId() + ".txt")));
		
		String title = pa.getTitle();
		if (!title.equals("No Title Found"))
			writer.write(title + "\n");
		
		PMCArticleAbstract abs = pa.getAbstract();
		for (PMCArticleSentence s : abs.getAbstractSentences()) {
			writer.write(s.getText() + "\n");
		}
		
		PMCArticleFullText ft = pa.getFullText();
		List<PMCArticleSentence> sentences = ft.getFullTextSentences();
		for (PMCArticleSentence sentence : sentences) {
			if (sentence.getInParagraphIndex() == 0) {
				writer.write("\n");
			}
			writer.write(sentence.getText() + "\n");
		}
		
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		copyFolder(new File(args[0]), new File(args[1]));
	}
}
