package org.nii.phenominer.searchdb.parse;

import org.nii.phenominer.searchdb.bean.phenominer.AnnotationCollection;
import org.nii.phenominer.searchdb.bean.phenominer.Term;

import com.google.gson.Gson;

public class S5XMLParserExample {

	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		AnnotationCollection collection = S5XMLParser.getAnnotationCollection("data/S5.test");
		for (Term term : collection.getTerm()) {
			String json = gson.toJson(term);
			//File file = File.createTempFile("term", ".json");
			//FileHelper.writeToFile(json, file, Charset.forName("ISO-8859-1"));
			System.out.println(json);
			double value = Double.parseDouble(term.getAssociatedDisorders().getDisorder().get(0).getSupp());
			System.out.println(value);
			//JsonParserAndMapper jsonParserAndMapper = new JsonParserFactory().create();
			//collection = jsonParserAndMapper.parse(AnnotationCollection.class, json);
			//file.delete();
		}
	}
}
