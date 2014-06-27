package org.nii.phenominer.processing.matching;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opennlp.tools.tokenize.TokenizerME;

import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.processing.bean.Annotation;
import org.nii.phenominer.processing.bean.Text;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;

import com.cedarsoftware.util.io.JsonWriter;
import com.thoughtworks.xstream.XStream;

import edu.stanford.nlp.util.StringUtils;

public class OntologyAnnotator {
	List<LongestMatching> matchers = new ArrayList<LongestMatching>();
	JsonSerializer serializer;
	XStream xstream;

	void init() {
		xstream = new XStream();
		xstream.alias("document", Text.class);
		xstream.alias("annotation", Annotation.class);
		serializer = new JsonSerializerFactory().create();
	}
	
	public OntologyAnnotator(String optFile) throws Exception {
		init();
		loadOptionFile(optFile);
	}

	public Map<String, String> getOntologyTerms(String oboFile, String prefix) throws Exception {
		Map<String, String> terms = new HashMap<String, String>();
		OBOFormatParser p = new OBOFormatParser();
		OBODoc obodoc = p.parse(oboFile);
		for (Frame frame : obodoc.getTermFrames()) {
			if (frame.getTagValue(OboFormatTag.TAG_NAME).toString().length() < 4)
				continue;
			terms.put(frame.getTagValue(OboFormatTag.TAG_NAME).toString().toLowerCase(), prefix
					+ "|" + frame.getId());
			for (Clause syn : frame.getClauses(OboFormatTag.TAG_SYNONYM)) {
				if (syn.getValue().toString().length() < 4)
					continue;
				terms.put(syn.getValue().toString().toLowerCase(), prefix + "|" + frame.getId());
			}
		}
		return terms;
	}

	public Map<String, String> getFileTerms(String txtFile, String prefix) throws Exception {
		Map<String, String> terms = new HashMap<String, String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile),
				"UTF-8"));
		String line = new String();
		while ((line = in.readLine()) != null) {
			if (line.trim().equals(""))
				continue;
			String[] fields = line.split("\t");
			terms.put(fields[1].toLowerCase(), prefix + "|" + fields[0]);
		}
		in.close();

		return terms;
	}

	public Map<String, String> getSNOMEDTerms(String snomedFile, String prefix) throws Exception {
		Map<String, String> terms = new HashMap<String, String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(snomedFile), "UTF-8"));
		String line = new String();
		while ((line = in.readLine()) != null) {
			if (line.trim().equals(""))
				continue;
			String[] fields = line.split("\t");
			if (fields[3].length() < 4)
				continue;
			terms.put(fields[3].toLowerCase(), prefix + "|SNOMEDCT:" + fields[2]);
		}
		in.close();

		return terms;
	}

	void loadOptionFile(String optFile) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(optFile),
				"UTF-8"));
		String line = new String();
		while ((line = in.readLine()) != null) {
			if (line.trim().equals(""))
				continue;
			String[] fields = line.split("\t");
			Map<String, String> dictionary = new HashMap<String, String>();
			if (fields[0].equals("OBO")) {
				System.out.println("Loading " + fields[1]);
				dictionary = getOntologyTerms(fields[2], fields[1]);
			} else if (fields[0].equals("TEXT")) {
				System.out.println("Loading " + fields[1]);
				dictionary = getFileTerms(fields[2], fields[1]);
			} else if (fields[0].equals("SNOMED")) {
				System.out.println("Loading " + fields[1]);
				dictionary = getSNOMEDTerms(fields[2], fields[1]);
			}

			matchers.add(new LongestMatching(dictionary));
		}
		in.close();
	}

	public Set<BioSpan> annotate(String tokens[]) {
		Set<BioSpan> spans = new HashSet<BioSpan>();

		for (LongestMatching matcher : matchers) {
			for (int i = 1; i <= 4; i++) {
				BioSpan[] annotations = matcher.tagging(tokens, i, true);
				spans.addAll(new ArrayList<BioSpan>(Arrays.asList(annotations)));
			}
			// BioSpan[] annotations = matcher.tagging(text, -1, true);
			// spans.addAll(new ArrayList<BioSpan>(Arrays.asList(annotations)));
		}

		return spans;
	}

	public Text annotate(String text, String tokens[], boolean isTokenized) {
		Set<BioSpan> spans = annotate(tokens);

		Text doc = new Text(text);
		doc.setTokenize(isTokenized);
		doc.setTokens(tokens);

		for (BioSpan span : spans) {
			String[] fields = span.getType().split("\\|");
			int start = span.getStartOffset(tokens);
			int end = span.getEndOffset(tokens);
			Annotation annotation = new Annotation(text.substring(start, end), span.getStart(),
					span.getEnd(), span.getStartOffset(tokens), span.getEndOffset(tokens),
					fields[0], fields[1]);
			doc.addAnnotations(annotation);
		}
		return doc;
	}

	public String annotateJson(String text, String tokens[], boolean isTokenized) throws Exception {
		Text doc = annotate(text, tokens, isTokenized);

		String json = serializer.serialize(doc).toString();
		return JsonWriter.formatJson(json);
	}

	public String annotateXML(String text, String tokens[], boolean isTokenized) throws Exception {
		Text doc = annotate(text, tokens, isTokenized);		
		return xstream.toXML(doc);
	}

	public static void main(String[] args) throws Exception {
		boolean isTokenized = false;
		TokenizerME tokenizer = TokenizerSingleton.getInstance().createTokenizerModel();
		OntologyAnnotator annotater = new OntologyAnnotator("test/ontologyMatcher.txt");
		String text = "Autosomal dominant inheritance";
		String[] tokens;
		if (isTokenized) {
			tokens = tokenizer.tokenize(text);
			text = StringUtils.join(tokens);
		} else
			tokens = text.split("\\s");

		System.out.println(annotater.annotateXML(text, tokens, isTokenized));
	}
}
