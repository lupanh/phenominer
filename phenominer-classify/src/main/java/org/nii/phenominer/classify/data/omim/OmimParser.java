package org.nii.phenominer.classify.data.omim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

public class OmimParser {

	private boolean mSaveRaw;

	public OmimParser() {
		mSaveRaw = false;
	}

	public OmimParser(boolean saveRaw) {
		mSaveRaw = saveRaw;
	}

	public ArrayList<OmimRecord> parse(InputSource is) throws IOException {
		ArrayList<OmimRecord> recs = new ArrayList<OmimRecord>();
		OmimRecord curRec = null;
		ArrayList<String> lines = new ArrayList<String>();
		StringBuffer rawText = new StringBuffer();
		String line = null;
		LineNumberReader in = new LineNumberReader(is.getCharacterStream());
		while ((line = in.readLine()) != null) {
			if (isRecordStart(line)) {
				if (curRec != null) {
					if (lines.size() > 0) {
						OmimField field = parseField(lines);
						curRec.processField(field);
					}
					if (mSaveRaw)
						curRec.setRawText(rawText.toString());
					recs.add(curRec);
					lines.clear();
					if (mSaveRaw)
						rawText.setLength(0);
				}
				curRec = new OmimRecord();
				if (mSaveRaw)
					rawText.append(line + "\n");
			} else if (isFieldStart(line)) {
				if (curRec == null) {
					throw new IllegalStateException("parse error at line: " + in.getLineNumber());
				}
				if (lines.size() > 0) {
					OmimField field = parseField(lines);
					curRec.processField(field);
				}
				lines.clear();
				lines.add(line);
				if (mSaveRaw)
					rawText.append(line + "\n");
			} else {
				lines.add(line);
				if (mSaveRaw)
					rawText.append(line + "\n");
			}
		}
		in.close();
		// finish processing last record in file
		if (curRec != null) {
			if (lines.size() > 0) {
				OmimField field = parseField(lines);
				curRec.processField(field);
			}
			if (mSaveRaw)
				curRec.setRawText(rawText.toString());
			recs.add(curRec);
		}
		return recs;
	}

	boolean isFieldStart(String line) {
		if (line.startsWith("*FIELD*"))
			return true;
		return false;
	}

	boolean isRecordStart(String line) {
		if (line.startsWith("*RECORD*"))
			return true;
		return false;
	}

	OmimField parseField(ArrayList<String> lines) {
		// get label following token "*FIELD* "
		String label = lines.get(0);
		int i = label.indexOf(' ');
		if (i > 0)
			label = label.substring(i + 1);
		lines.remove(0);
		// remaining lines are text
		String[] text = new String[lines.size()];
		text = lines.toArray(text);
		return new OmimField(label, text);
	}

	public Map<String, String> loadOMIM2Gene(String file) throws Exception {
		Map<String, String> human = new HashMap<String, String>();
		BufferedReader f;
		f = new BufferedReader(new FileReader(file));
		String line = f.readLine();
		line = f.readLine();
		while (line != null) {
			String[] item = line.split("\t");
			if (item != null && item.length > 2) {
				String omim = item[0];
				String gene = item[1];
				human.put(omim, gene);
			}
			line = f.readLine();
		}
		f.close();

		return human;
	}
}