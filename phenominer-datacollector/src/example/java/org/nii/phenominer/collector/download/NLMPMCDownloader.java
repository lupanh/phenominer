package org.nii.phenominer.collector.download;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import us.codecraft.xsoup.Xsoup;

public class NLMPMCDownloader {

	public static void main(String[] args) throws Exception {
		Document document = Jsoup.connect("http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2034302/")
				.userAgent("Mozilla").cookie("auth", "token").timeout(6000).get();
		List<String> sections = Xsoup.compile("//p[@id~='P\\d']/allText()")
				.evaluate(document).list();
		for (String section : sections) {
			System.out.println(section);
			System.out.println("========================");
		}
	}

}
