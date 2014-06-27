package org.nii.phenominer.classify.data.omim;

public class OmimField {
	private final String mLabel;
	private final String[] mText;

	public OmimField(String label, String text[]) {
		mLabel = label;
		mText = text;
	}

	String label() {
		return mLabel;
	}

	String[] text() {
		return mText;
	}

	String concatText(String sep) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < mText.length; i++) {
			result.append(mText[i]);
			result.append(sep);
		}
		return result.toString();
	}

}
