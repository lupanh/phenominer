package org.nii.phenominer.processing.data;

import java.util.Map;
import java.util.TreeMap;

public class BioAnalyzerResult {
	String nameAnalyzer;
	String section;
	Map<String, BioDatum> datums;

	public BioAnalyzerResult() {
	}

	public BioAnalyzerResult(String section) {
		this.section = section;
	}

	public BioAnalyzerResult(String nameAnalyzer, String section) {
		this.nameAnalyzer = nameAnalyzer;
		this.section = section;
	}

	public BioAnalyzerResult(String nameAnalyzer, String section, Map<String, BioDatum> datums) {
		this.nameAnalyzer = nameAnalyzer;
		this.section = section;
		this.datums = datums;
	}

	public String getNameAnalyzer() {
		return nameAnalyzer;
	}

	public void setNameAnalyzer(String nameAnalyzer) {
		this.nameAnalyzer = nameAnalyzer;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Map<String, BioDatum> getDatums() {
		return datums;
	}

	public void setDatums(Map<String, BioDatum> datums) {
		this.datums = datums;
	}

	public BioDatum addDatum(String key, BioDatum datum) {
		if (datums == null)
			datums = new TreeMap<String, BioDatum>();
		return datums.put(key, datum);
	}
}
