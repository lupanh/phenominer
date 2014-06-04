package org.nii.phenominer.processing.io;

import java.util.List;

public class DocumentExample {
	String content;
	String contentXML;
	String contentTokenized;
	List<String> sentences;
	
	public DocumentExample(String content, String contentXML, String contentTokenized,
			List<String> sentences) {
		super();
		this.content = content;
		this.contentXML = contentXML;
		this.contentTokenized = contentTokenized;
		this.sentences = sentences;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentXML() {
		return contentXML;
	}

	public void setContentXML(String contentXML) {
		this.contentXML = contentXML;
	}

	public String getContentTokenized() {
		return contentTokenized;
	}

	public void setContentTokenized(String contentTokenized) {
		this.contentTokenized = contentTokenized;
	}

	public List<String> getSentences() {
		return sentences;
	}

	public void setSentences(List<String> sentences) {
		this.sentences = sentences;
	}	
}
