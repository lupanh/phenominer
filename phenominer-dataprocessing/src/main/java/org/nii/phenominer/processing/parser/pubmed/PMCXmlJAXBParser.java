package org.nii.phenominer.processing.parser.pubmed;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.commons.io.IOUtils;
import org.nii.phenominer.processing.data.pubmeddtd.Article;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class PMCXmlJAXBParser {
	private Unmarshaller unmarshaller;

	private XMLReader xmlReader;

	private static JAXBContext jcSingelton = null;

	private JAXBContext getSingleton() throws JAXBException {
		if (jcSingelton == null)
			jcSingelton = JAXBContext.newInstance(Article.class.getPackage().getName());
		return jcSingelton;
	}

	public PMCXmlJAXBParser() throws JAXBException, ParserConfigurationException, SAXException {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		spf.setFeature("http://xml.org/sax/features/validation", false);

		unmarshaller = getSingleton().createUnmarshaller();
		xmlReader = spf.newSAXParser().getXMLReader();
	}

	public Article parse(InputStream is) throws FileNotFoundException, JAXBException {
		try {
			InputSource inputSource = new InputSource(new InputStreamReader(is));
			SAXSource source = new SAXSource(xmlReader, inputSource);

			return (Article) unmarshaller.unmarshal(source);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
