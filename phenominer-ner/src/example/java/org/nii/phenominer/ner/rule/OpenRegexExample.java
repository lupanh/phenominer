package org.nii.phenominer.ner.rule;

import java.util.Arrays;

import org.nii.phenominer.ner.rule.RegularExpressionParsers;

import edu.washington.cs.knowitall.regex.RegularExpression;

public class OpenRegexExample {
	public static void main(String[] args) {
		String text = "abnormal kidney";
		String rule = "<abnormal> <kidney>";		
		RegularExpression<String> regex = RegularExpressionParsers.word.parse(rule);
		System.out.println(regex.apply(Arrays.asList(text.split("\\s+"))));
		System.out.println(regex.find(Arrays.asList(text.split("\\s+"))).groups().get(0));		
	}
}
