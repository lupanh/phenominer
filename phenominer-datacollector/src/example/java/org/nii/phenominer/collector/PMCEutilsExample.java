package org.nii.phenominer.collector;

import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.util.List;

public class PMCEutilsExample {
	public static void main(String... strings) throws Exception {
		List<PubmedArticleType> articles = new PubmedSearch().search("axon", 100);
		System.out.println(articles.size());
	}
}
