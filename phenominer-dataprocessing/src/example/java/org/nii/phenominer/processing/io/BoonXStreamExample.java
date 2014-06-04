package org.nii.phenominer.processing.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.boon.IO;
import org.boon.json.JsonParserAndMapper;
import org.boon.json.JsonParserFactory;
import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;

import com.thoughtworks.xstream.XStream;

public class BoonXStreamExample {

	public static void main(String[] args) throws Exception {
		DocumentExample doc1 = new DocumentExample("abc", "bcd", "cde", null);
		DocumentExample doc2 = new DocumentExample("abc", "bcd", "cde", null);
		List<DocumentExample> docs = new ArrayList<DocumentExample>();
		docs.add(doc1);
		docs.add(doc2);
		
		JsonSerializer serializer = new JsonSerializerFactory().create();
		XStream xstream = new XStream();
		xstream.alias("document", DocumentExample.class);
		xstream.alias("documents", List.class);
		
		String json = serializer.serialize(doc1).toString();
		String xml = xstream.toXML(docs);
		System.out.println(xml);
		
		File file = File.createTempFile("document", ".json");
		IO.write(IO.path(file.toString()), json);

		JsonParserAndMapper jsonParserAndMapper = new JsonParserFactory().create();
		doc1 = jsonParserAndMapper.parse(DocumentExample.class, json);
		System.out.println(doc1);
		file.delete();
	}
}
