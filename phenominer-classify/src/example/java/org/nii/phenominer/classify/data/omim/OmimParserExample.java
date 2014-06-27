package org.nii.phenominer.classify.data.omim;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

import org.xml.sax.InputSource;

public class OmimParserExample {

	public static void main(String... strings) throws Exception {
		String inFile = "data/omim/omim.txt";
		String O2GFile = "data/omim/mim2gene.txt";
		String OIDFile = "data/omim/omim_id.txt";

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				OIDFile), "UTF-8"));

		OmimParser parser = new OmimParser(true);
		InputSource inSource = new InputSource();
		inSource.setCharacterStream(new FileReader(inFile));
		ArrayList<OmimRecord> records = parser.parse(inSource);

		Map<String, String> phenotype = parser.loadOMIM2Gene(O2GFile);
		for (OmimRecord rec : records) {
			String type = phenotype.get(rec.getMimId() + "");
			if (type.contains("moved"))
				continue;

			writer.write(rec.getMimId() + "\t" + type + "\n");
			//writer.write("http://omim.org/entry/" + rec.getMimId() + "\n");
		}
		System.out.println(records.size());
		writer.close();
	}

}
