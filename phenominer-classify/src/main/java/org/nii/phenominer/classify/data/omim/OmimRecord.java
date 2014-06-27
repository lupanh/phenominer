package org.nii.phenominer.classify.data.omim;

import java.util.HashSet;
import java.util.Set;

public class OmimRecord {
	private int mMimId;
	private String mTitle;
	private String[] mAltTitles;
	private String mDesc;
	private String mAllelicVars;
	private String mSyndromes;
	private String mRefs;
	private String mSeeRefs;
	private String mCreateDate;
	private String mContributors;
	private String mEdits;
	private String mRawText;

	private boolean mIsMoved;

	public static final String LBL_MIMID = "NO";
	public static final String LBL_TITLE = "TI";
	public static final String LBL_DESC = "TX";
	public static final String LBL_SYNDROMES = "CS";
	public static final String LBL_ALLELICVARS = "AV";
	public static final String LBL_SEE = "SA";
	public static final String LBL_REFS = "RF";
	public static final String LBL_CREATEDATE = "CD";
	public static final String LBL_CONTRIBS = "CN";
	public static final String LBL_EDITS = "ED";

	public OmimRecord() {
		mIsMoved = false;
	}

	void setMimId(int mimId) {
		mMimId = mimId;
	}

	public int getMimId() {
		return mMimId;
	}

	void setTitle(String title) {
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}

	void setAltTitles(String[] altTitles) {
		mAltTitles = altTitles;
	}

	public String[] getAltTitles() {
		return mAltTitles;
	}

	void setDesc(String desc) {
		mDesc = desc;
	}

	public String getDesc() {
		return mDesc;
	}

	void setAllelicVars(String allelicVars) {
		mAllelicVars = allelicVars;
	}

	public String getAllelicVars() {
		return mAllelicVars;
	}

	void setSyndromes(String syndromes) {
		mSyndromes = syndromes;
	}

	public String getSyndromes() {
		return mSyndromes;
	}

	void setRefs(String refs) {
		mRefs = refs;
	}

	public String getRefs() {
		return mRefs;
	}

	void setSeeRefs(String seeRefs) {
		mSeeRefs = seeRefs;
	}

	public String getSeeRefs() {
		return mSeeRefs;
	}

	void setCreateDate(String createDate) {
		mCreateDate = createDate;
	}

	public String getCreateDate() {
		return mCreateDate;
	}

	void setContributors(String contributors) {
		mContributors = contributors;
	}

	public String getContributors() {
		return mContributors;
	}

	void setEdits(String edits) {
		mEdits = edits;
	}

	public String getEdits() {
		return mEdits;
	}

	void setIsMoved(boolean isMoved) {
		mIsMoved = isMoved;
	}

	public boolean isMoved() {
		return mIsMoved;
	}

	public void setRawText(String rawText) {
		mRawText = rawText;
	}

	public String getRawText() {
		return mRawText;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("MIM Number: " + getMimId() + "\n");
		result.append("Title: " + getTitle() + "\n");
		if (getAltTitles() != null) {
			String[] altTitles = getAltTitles();
			for (int i = 0; i < altTitles.length; i++) {
				result.append("\t" + altTitles[i] + "\n");
			}
		}
		if (getDesc() != null) {
			result.append("*FIELD* Description:\n");
			result.append(getDesc());
		}
		if (getAllelicVars() != null) {
			result.append("*FIELD* Allelic Variants:\n");
			result.append(getAllelicVars());
		}
		if (getSyndromes() != null) {
			result.append("*FIELD* Syndromes:\n");
			result.append(getSyndromes());
		}
		return result.toString();
	}

	public void processField(OmimField field) {
		if (field.label() == null || field.text() == null) {
			System.err.println("missing field data");
			return;
		}
		// String sep = System.getProperty("line.separator");
		String sep = "\n";
		if (field.label().equals(LBL_MIMID)) {
			String[] text = field.text();
			int mimId = -1;
			try {
				mimId = Integer.parseInt(text[0]);
				setMimId(mimId);
			} catch (NumberFormatException nfe) {
				System.err.println("invalid MIM NO: " + text[0]);
			}
		} else if (field.label().equals(LBL_TITLE)) {
			String title = field.concatText(" ");
			String[] titles = title.split(";");
			String title1 = titles[0];
			// title is preceeded by MimId, MimId prefix indicates entry status,
			// see
			// http://www.ncbi.nlm.nih.gov/Omim/omimfaq.html#mim_number_symbols
			if (title1.startsWith("*")) { // * gene of known sequence
			} else if (title1.startsWith("+")) { // known phenotype, gene
													// sequence
			} else if (title1.startsWith("#")) { // known phenotype, gene locus
													// not unique
			} else if (title1.startsWith("%")) { // known phenotype, unknown
													// locus
			} else if (title1.startsWith("^")) { // moved
				setIsMoved(true);
			} else { // not clearly established as phenotype
			}
			int index = title1.indexOf(" ");
			title1 = title1.substring(index + 1);
			setTitle(title1);
			// get alternate titles
			if (titles.length > 1) {
				Set<String> altTitles = new HashSet<String>();
				for (int i = 1; i < titles.length; i++) {
					if (titles[i].trim().length() > 0)
						altTitles.add(titles[i].trim());
				}
				String[] altTitlesArray = new String[altTitles.size()];
				altTitlesArray = altTitles.toArray(altTitlesArray);
				setAltTitles(altTitlesArray);
			} else {
				setAltTitles(new String[0]);
			}

		} else if (field.label().equals(LBL_DESC)) {
			setDesc(field.concatText(sep));
		} else if (field.label().equals(LBL_SYNDROMES)) {
			setSyndromes(field.concatText(sep));
		} else if (field.label().equals(LBL_ALLELICVARS)) {
			setAllelicVars(field.concatText(sep));
		} else if (field.label().equals(LBL_SEE)) {
			setSeeRefs(field.concatText(sep));
		} else if (field.label().equals(LBL_REFS)) {
			setRefs(field.concatText(sep));
		} else if (field.label().equals(LBL_CREATEDATE)) {
		} else if (field.label().equals(LBL_CONTRIBS)) {
		} else if (field.label().equals(LBL_EDITS)) {
		} else {
			// unknown label
		}
	}

}
