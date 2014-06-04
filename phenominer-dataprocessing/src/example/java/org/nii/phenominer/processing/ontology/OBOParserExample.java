package org.nii.phenominer.processing.ontology;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;

public class OBOParserExample {
	public static void main(String[] args) throws Exception {
		OBOFormatParser p = new OBOFormatParser();
		OBODoc obodoc = p.parse("test/hp.18-02-14.obo");
		for (Frame frame : obodoc.getTermFrames()) {
			System.out.println(frame.getId() + "\t" + frame.getTagValue(OboFormatTag.TAG_NAME));
			for (Clause syn : frame.getClauses(OboFormatTag.TAG_SYNONYM)) {
				System.out.println(frame.getId() + "\t" + syn.getValue());
			}
		}
	}
}