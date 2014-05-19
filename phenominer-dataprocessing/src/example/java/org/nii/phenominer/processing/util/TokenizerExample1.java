package org.nii.phenominer.processing.util;

import opennlp.tools.tokenize.TokenizerME;

public class TokenizerExample1 {
	static TokenizerME tokenizer;

	public static void main(String[] args) {
		String text = "An inherited enzymatic defect in porphyria cutanea tarda: decreased uroporphyrinogen decarboxylase activity. Uroporphyrinogen decarboxylase activity was measured in liver and erythrocytes of normal subjects and in patients with porphyria cutanea tarda and their relatives. In patients with porphyria cutanea tarda, hepatic uroporphyrinogen decarboxylase activity was significantly reduced (mean 0.43 U/mg protein; range 0.25-0.99) as compared to normal subjects (mean 1.61 U/mg protein; range 1.27-2.42). Erythrocyte uroporphyrinogen decarboxylase was also decreased in patients with porphyria cutanea tarda. The mean erythrocyte enzymatic activity in male patients was 0.23 U/mg Hb (range 0.16-0.30) and in female patients was 0.17 U/mg Hb (range 0.15-0.18) as compared with mean values in normal subjects of 0.38 U/mg Hb (range 0.33-0.45) in men and 0.26 U/mg Hb (range 0.18-0.36) in women. With the erythrocyte assay, multiple examples of decreased uroporphyrinogen decarboxylase activity were detected in members of three families of patients with porphyria cutanea tarda. In two of these families subclinical porphyria was also recognized. The inheritance pattern was consistant with an autosomal dominant trait. The difference in erythrocyte enzymatic activity between men and women was not explained but could have been due to estrogens. This possibility was supported by the observation that men under therapy with estrogens for carcinoma of the prostate had values in the normal female range. It is proposed that porphyria cutanea tarda results from the combination of an inherited defect in uroporphyrinogen decarboxylase and an acquired factor, usually siderosis associated with alcoholic liver disease.";
		tokenizer = BioTokenizer.getInstance().createTokenizerModel();
		String[] sents = tokenizer.tokenize(text);
		for (String sent : sents)
			System.out.println(sent);
	}
}
