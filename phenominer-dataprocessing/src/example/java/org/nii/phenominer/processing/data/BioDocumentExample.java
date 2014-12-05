package org.nii.phenominer.processing.data;

import org.joda.time.DateTime;

public class BioDocumentExample {
	public static void main(String...strings) {
		BioDocument document = new BioDocument("PM001", "Hello vietnam", "Vietnam is national", "The capital of Vietnam is Hanoi");
		document.setCreateDate(DateTime.now().toString());
		
		BioAnalyzerResult sdresult = new BioAnalyzerResult("summary");
		sdresult.addDatum("token0", new BioSentenceDatum("", "", "Hello"));
		sdresult.addDatum("token1", new BioSentenceDatum("", "", "vietnam"));
		document.addAnalyzerResult("tokenizer", sdresult);
		System.out.println(document.printJson());
		
	}
}
