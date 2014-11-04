package org.nii.phenominer.nlp.app;

import java.io.UnsupportedEncodingException;

import opennlp.tools.tokenize.TokenizerME;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.nii.phenominer.nlp.tokenizer.TokenizerSingleton;
import org.nii.phenominer.nlp.tools.bllipparser.BllipParserServer;
import org.nii.phenominer.nlp.tools.jeniatagger.Jenia;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import com.jmcejuela.bio.jenia.common.Sentence;

import edu.stanford.nlp.util.StringUtils;

public class NLPToolsWebService extends Application {
	static BllipParserServer bllipparser;
	static OntologyAnnotator annotater = null;
	static TokenizerME tokenizer;

	private static Options buildOptions() {
		Options options = new Options();
		{
			Option bllipHomePath = new Option("b", "bllipHome", true, "Bllip home path");
			bllipHomePath.setRequired(true);
			bllipHomePath.setArgName("path");
			options.addOption(bllipHomePath);

			Option f1ModelPath = new Option("f", "firstStageModel", true,
					"Bllip first stage model path");
			f1ModelPath.setRequired(true);
			f1ModelPath.setArgName("path");
			options.addOption(f1ModelPath);

			Option f2ModelPath = new Option("s", "secondStageModel", true,
					"Bllip second stage model path");
			f2ModelPath.setRequired(true);
			f2ModelPath.setArgName("path");
			options.addOption(f2ModelPath);

			Option ontologyListFile = new Option("o", "ontologyListFile", true,
					"Ontology list file path");
			ontologyListFile.setRequired(false);
			ontologyListFile.setArgName("file");
			options.addOption(ontologyListFile);

			Option portNumber = new Option("p", "portServer", true, "Port server");
			portNumber.setRequired(true);
			portNumber.setArgName("number");
			options.addOption(portNumber);
		}
		return options;
	}

	private static void usage(Options options) {
		new HelpFormatter().printHelp("NLPToolsWebService", options, true);
		System.exit(1);
	}

	private static void init() throws Exception {
		bllipparser = new BllipParserServer("/home/bllip-parser",
				"/home/bllip-parser/biomodel/parser", "/home/bllip-parser/biomodel/reranker");
		Jenia.setModelsPath("models/genia");
		annotater = new OntologyAnnotator("test/ontologyMatcher.txt");
		tokenizer = TokenizerSingleton.getInstance().createTokenizerModel();
	}

	private static void init(String bllipHome, String firstStageModelPath,
			String secondStageModelPath, String ontologiesListPath) throws Exception {
		bllipparser = new BllipParserServer(bllipHome, firstStageModelPath, secondStageModelPath);
		Jenia.setModelsPath("models/genia");
		if (ontologiesListPath != null)
			annotater = new OntologyAnnotator(ontologiesListPath);
		tokenizer = TokenizerSingleton.getInstance().createTokenizerModel();
	}

	public static void main(String[] args) throws Exception {
		Options options = buildOptions();

		CommandLine commandLine = null;
		try {
			commandLine = new GnuParser().parse(options, args);
			String bllipHomePath = commandLine.getOptionValue("b");
			String f1ModelPath = commandLine.getOptionValue("f");
			String f2ModelPath = commandLine.getOptionValue("s");
			String ontologyListFile = commandLine.getOptionValue("o");
			int portNumber = Integer.parseInt(commandLine.getOptionValue("p"));

			init(bllipHomePath, f1ModelPath, f2ModelPath, ontologyListFile);

			Component component = new Component();
			component.getServers().add(Protocol.HTTP, portNumber);

			Application application = new NLPToolsWebService();

			component.getDefaultHost().attachDefault(application);
			component.start();
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			usage(options);
		}
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		Restlet bllip = new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				String message;
				try {
					boolean isTokenized = Boolean.parseBoolean((String) request.getAttributes()
							.get("tokenize"));
					String text = java.net.URLDecoder.decode(
							(String) request.getAttributes().get("text"), "UTF-8");
					if (isTokenized)
						text = StringUtils.join(tokenizer.tokenize(text));
					message = bllipparser.parse(text);
				} catch (UnsupportedEncodingException e) {
					message = "";
				}
				response.setEntity(message, MediaType.TEXT_PLAIN);
			}
		};

		Restlet jenia = new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				String message;
				try {
					boolean isTokenized = Boolean.parseBoolean((String) request.getAttributes()
							.get("tokenize"));
					String text = java.net.URLDecoder.decode(
							(String) request.getAttributes().get("text"), "UTF-8");
					if (isTokenized)
						text = StringUtils.join(tokenizer.tokenize(text));
					Sentence sentence = Jenia.analyzeAll(text, true);
					message = sentence.toString();
				} catch (UnsupportedEncodingException e) {
					message = "";
				}
				response.setEntity(message, MediaType.TEXT_PLAIN);
			}
		};
		
		if (annotater != null) {
			Restlet annotator = new Restlet() {
				@Override
				public void handle(Request request, Response response) {
					String message;
					try {
						boolean isTokenized = Boolean.parseBoolean((String) request.getAttributes()
								.get("tokenize"));
						String text = java.net.URLDecoder.decode(
								(String) request.getAttributes().get("text"), "UTF-8");
						String[] tokens;
						if (isTokenized) {
							tokens = tokenizer.tokenize(text);
							text = StringUtils.join(tokens);
						} else
							tokens = text.split("\\s");
						message = annotater.annotateXML(text, tokens, false);
					} catch (Exception e) {
						message = "";
					}

					response.setEntity(message, MediaType.TEXT_XML);
				}
			};
			router.attach("/annotator={text}/tokenized={tokenize}", annotator);
		}		

		router.attach("/bllip={text}/tokenized={tokenize}", bllip);
		router.attach("/genia={text}/tokenized={tokenize}", jenia);

		return router;
	}
}
