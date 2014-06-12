package org.nii.phenominer.processing.rest;

import java.io.UnsupportedEncodingException;

import org.nii.phenominer.processing.nlp.jeniatagger.Jenia;
import org.nii.phenominer.processing.parse.BllipParserServer;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.Response;

import com.jmcejuela.bio.jenia.common.Sentence;

public class RestletExample extends Application {
	static BllipParserServer bllipparser;

	private static void init() {
		bllipparser = new BllipParserServer("/home/bllip-parser",
				"/home/bllip-parser/biomodel/parser", "/home/bllip-parser/biomodel/reranker");
		Jenia.setModelsPath("models/genia");
	}

	public static void main(String[] args) throws Exception {
		init();

		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 8111);

		Application application = new RestletExample();

		component.getDefaultHost().attachDefault(application);
		component.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		Restlet bllip = new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				String message;
				try {
					message = bllipparser.parse(java.net.URLDecoder.decode((String) request
							.getAttributes().get("bllip"), "UTF-8"));
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
					Sentence sentence = Jenia.analyzeAll(java.net.URLDecoder.decode(
							(String) request.getAttributes().get("bllip"), "UTF-8"), true);
					message = sentence.toString();
				} catch (UnsupportedEncodingException e) {
					message = "";
				}
				response.setEntity(message, MediaType.TEXT_PLAIN);
			}
		};

		router.attach("/bllip={bllip}", bllip);
		router.attach("/genia={genia}", jenia);

		return router;
	}

}
