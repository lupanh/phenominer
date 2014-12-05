package org.nii.phenominer.processing.data;

import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

public class BioDocument {
	String id;
	String uid;
	Map<String, String> pubid;
	String title;
	String summary;
	String fulltext;
	Map<String, String> labels;
	Map<String, BioAnalyzerResult> analyzerResults;
	String createDate;
	String updateDate;

	public BioDocument() {
	}

	public BioDocument(String id, String title, String summary, String fulltext) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.fulltext = fulltext;
	}

	public BioDocument(String id, String uid, Map<String, String> pubid, String title,
			String summary, String fulltext, Map<String, String> labels,
			Map<String, BioAnalyzerResult> analyzerResults, String createDate, String updateDate) {
		this.id = id;
		this.uid = uid;
		this.pubid = pubid;
		this.title = title;
		this.summary = summary;
		this.fulltext = fulltext;
		this.labels = labels;
		this.analyzerResults = analyzerResults;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Map<String, String> getPubId() {
		return pubid;
	}

	public void setPubId(Map<String, String> pubid) {
		this.pubid = pubid;
	}

	public String addPubId(String key, String id) {
		if (pubid== null)
			pubid = new TreeMap<String, String>();
		return pubid.put(key, id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFulltext() {
		return fulltext;
	}

	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public String addLabel(String key, String label) {
		if (labels == null)
			labels = new TreeMap<String, String>();
		return labels.put(key, label);
	}

	public Map<String, BioAnalyzerResult> getAnalyzerResults() {
		return analyzerResults;
	}

	public void setAnalyzerResults(Map<String, BioAnalyzerResult> analyzerResults) {
		this.analyzerResults = analyzerResults;
	}

	public BioAnalyzerResult addAnalyzerResult(String key, BioAnalyzerResult result) {
		if (analyzerResults == null)
			analyzerResults = new TreeMap<String, BioAnalyzerResult>();
		return analyzerResults.put(key, result);
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String printJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
