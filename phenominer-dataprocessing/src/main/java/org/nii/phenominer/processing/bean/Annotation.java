package org.nii.phenominer.processing.bean;

public class Annotation {
	String text;
	int fromToken;
	int toToken;
	int fromOffset;
	int toOffset;
	String type;
	String source;

	public Annotation(String text, int fromToken, int toToken, int fromOffset, int toOffset,
			String type, String source) {
		super();
		this.text = text;
		this.fromToken = fromToken;
		this.toToken = toToken;
		this.fromOffset = fromOffset;
		this.toOffset = toOffset;
		this.type = type;
		this.source = source;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getFromToken() {
		return fromToken;
	}

	public void setFromToken(int fromToken) {
		this.fromToken = fromToken;
	}

	public int getToToken() {
		return toToken;
	}

	public void setToToken(int toToken) {
		this.toToken = toToken;
	}

	public int getFromOffset() {
		return fromOffset;
	}

	public void setFromOffset(int fromOffset) {
		this.fromOffset = fromOffset;
	}

	public int getToOffset() {
		return toOffset;
	}

	public void setToOffset(int toOffset) {
		this.toOffset = toOffset;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Annotation [text=" + text + ", fromToken=" + fromToken + ", toToken=" + toToken
				+ ", fromOffset=" + fromOffset + ", toOffset=" + toOffset + ", type=" + type
				+ ", source=" + source + "]";
	}
}
