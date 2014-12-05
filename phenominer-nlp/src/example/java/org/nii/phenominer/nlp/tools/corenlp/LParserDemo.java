package org.nii.phenominer.nlp.tools.corenlp;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.lexparser.Options;
import edu.stanford.nlp.pipeline.DefaultPaths;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;

public class LParserDemo {

	public static void main(String[] args) {
		LexicalizedParser parser = LexicalizedParser.getParserFromFile(
				DefaultPaths.DEFAULT_PARSER_MODEL, new Options());
		String text = "I can almost always tell when movies use fake dinosaurs.";
		Tree parseTree = parser.parse(text);
		TreePrint treePrint = new TreePrint("oneline");
		treePrint.printTree(parseTree);		
	}

}
