package org.nii.phenominer.searchdb.parser;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.nii.phenominer.searchdb.data.phenominer.AnnotationCollection;

public class S5XMLParser {
	public static AnnotationCollection getAnnotationCollection(File xmlFile) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(AnnotationCollection.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		AnnotationCollection collection = (AnnotationCollection) jaxbUnmarshaller
				.unmarshal(xmlFile);
		return collection;
	}

	public static AnnotationCollection getAnnotationCollection(String xmlFile) throws Exception {
		return getAnnotationCollection(new File(xmlFile));
	}
}
