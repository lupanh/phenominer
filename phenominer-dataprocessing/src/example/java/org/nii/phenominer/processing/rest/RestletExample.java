package org.nii.phenominer.processing.rest;

import java.io.UnsupportedEncodingException;

import org.nii.phenominer.processing.parse.BllipParserServer;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.Response;

public class RestletExample extends Application {
	BllipParserServer server = new BllipParserServer("/home/bllip-parser",
			"/home/bllip-parser/biomodel/parser", "/home/bllip-parser/biomodel/reranker");

	public static void main(String[] args) throws Exception {
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
					message = server.parse(java.net.URLDecoder.decode((String) request
							.getAttributes().get("bllip"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					message = "";
				}
				response.setEntity(message, MediaType.TEXT_PLAIN);
			}
		};

		router.attach("/bllip={bllip}", bllip);

		return router;
	}

}
