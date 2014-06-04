package org.nii.phenominer.processing.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Text {
	String content;
	String[] tokens;
	boolean isTokenize;
	List<Annotation> annotations = new ArrayList<Annotation>();

	public Text(String content) {
		this.content = content;
	}

	public Text(String content, String[] tokens, boolean isTokenize, List<Annotation> annotations) {
		this.content = content;
		this.tokens = tokens;
		this.isTokenize = isTokenize;
		this.annotations = annotations;
	}

	public String getText() {
		return content;
	}

	public void setText(String content) {
		this.content = content;
	}

	public String[] getTokens() {
		return tokens;
	}

	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}

	public boolean getIsTokenize() {
		return isTokenize;
	}

	public void setTokenize(boolean isTokenize) {
		this.isTokenize = isTokenize;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public boolean addAnnotations(Annotation annotation) {
		return annotations.add(annotation);
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	@Override
	public String toString() {
		return "Document [text=" + content + ", tokens=" + Arrays.toString(tokens) + ", tokenize="
				+ isTokenize + ", annotations=" + annotations + "]";
	}
}
