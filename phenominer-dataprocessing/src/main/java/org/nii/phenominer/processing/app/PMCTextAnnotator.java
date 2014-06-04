package org.nii.phenominer.processing.app;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import opennlp.tools.tokenize.TokenizerME;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.boon.IO;
import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;
import org.nii.phenominer.processing.bean.Annotation;
import org.nii.phenominer.processing.bean.Text;
import org.nii.phenominer.processing.matching.BioSpan;
import org.nii.phenominer.processing.matching.OntologyAnnotator;
import org.nii.phenominer.processing.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.processing.util.FileHelper;

import com.cedarsoftware.util.io.JsonWriter;
import com.thoughtworks.xstream.XStream;

import edu.stanford.nlp.util.StringUtils;

public class PMCTextAnnotator {
	static boolean tokenize = false;
	static String outputFormat = "json";
	static TokenizerME tokenizer;
	static OntologyAnnotator annotater;
	static JsonSerializer serializer;
	static XStream xstream;

	private static Options buildOptions() {
		Options options = new Options();
		{
			Option optionFile = new Option("f", "opt", true, "Option file path");
			optionFile.setRequired(true);
			optionFile.setArgName("path");
			options.addOption(optionFile);

			Option tokenized = new Option("t", "tok", true, "Tokenize=true|false");
			tokenized.setArgName("true|false");
			options.addOption(tokenized);

			Option format = new Option("s", "format", true, "Output format=json|xml");
			format.setArgName("json|xml");
			options.addOption(format);

			Option inputDir = new Option("i", "dir", true, "Input directory path");
			inputDir.setRequired(true);
			inputDir.setArgName("path");
			options.addOption(inputDir);

			Option outputDir = new Option("o", "output", true, "Output directory path");
			outputDir.setRequired(true);
			outputDir.setArgName("path");
			options.addOption(outputDir);
		}
		return options;
	}

	private static void usage(Options options) {
		new HelpFormatter().printHelp("PMCTextAnnotator", options, true);
		System.exit(1);
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		tokenizer = TokenizerSingleton.getInstance().createTokenizerModel();
		serializer = new JsonSerializerFactory().create();
		xstream = new XStream();
		xstream.alias("text", Text.class);
		xstream.alias("annotation", Annotation.class);
		xstream.alias("document", List.class);

		Options options = buildOptions();

		CommandLine commandLine = null;
		try {
			commandLine = new GnuParser().parse(options, args);
			String optionFile = commandLine.getOptionValue("f");
			String tokenized = commandLine.getOptionValue("t");
			outputFormat = commandLine.getOptionValue("s");
			String inputDir = commandLine.getOptionValue("i");
			String outputDir = commandLine.getOptionValue("o");

			annotater = new OntologyAnnotator(optionFile);
			tokenize = Boolean.parseBoolean(tokenized);
			if (!outputFormat.toLowerCase().equals("json")
					&& !outputFormat.toLowerCase().equals("xml"))
				outputFormat = "json";
			process(new File(inputDir), new File(outputDir), ".txt");
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			usage(options);
		}
		long current = System.currentTimeMillis();
		System.out.println((current - start) + "ms");
	}

	static void process(File src, File dest, String extension) throws Exception {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory processed from " + src + "  to " + dest);
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				process(srcFile, destFile, extension);
			}
		} else {
			if (src.toString().endsWith(extension)) {
				System.out.println(src);
				List<String> lines = IO.readLines(src);
				List<Text> documents = new ArrayList<Text>();
				for (String line : lines) {
					if (line.trim().length() == 0)
						continue;
					String[] tokens;
					if (tokenize) {
						tokens = tokenizer.tokenize(line);
						line = StringUtils.join(tokens);
					} else
						tokens = line.split("\\s");

					Text doc = new Text(line);
					doc.setTokenize(tokenize);
					doc.setTokens(tokens);

					Set<BioSpan> spans = annotater.annotate(tokens);
					for (BioSpan span : spans) {
						String[] fields = span.getType().split("\\|");
						int start = span.getStartOffset(tokens);
						int end = span.getEndOffset(tokens);
						Annotation annotation = new Annotation(line.substring(start, end),
								span.getStart(), span.getEnd(), span.getStartOffset(tokens),
								span.getEndOffset(tokens), fields[0], fields[1]);
						doc.addAnnotations(annotation);
					}
					documents.add(doc);
				}

				if (outputFormat.equals("json")) {
					String json = serializer.serialize(documents).toString();
					FileHelper.writeToFile(JsonWriter.formatJson(json),
							new File(dest.toString() + ".json"),
							Charset.forName("ISO-8859-1"));
				} else if (outputFormat.equals("xml")) {
					String xml = xstream.toXML(documents);
					FileHelper.writeToFile(xml, new File(dest.toString() + ".xml"),
							Charset.forName("ISO-8859-1"));
				}
			}

		}
	}

}
