package org.nii.phenominer.processing.data;

public class BioSentenceDatum<T> implements BioDatum {
	String id;
	String uid;
	T content;

	public BioSentenceDatum() {
	}
	
	public BioSentenceDatum(T content) {
		this.content = content;
	}	

	public BioSentenceDatum(String id, String uid, T content) {
		this.id = id;
		this.uid = uid;
		this.content = content;
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

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
}
