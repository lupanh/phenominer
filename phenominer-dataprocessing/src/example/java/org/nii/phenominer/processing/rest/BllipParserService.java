package org.nii.phenominer.processing.rest;

import org.nii.phenominer.processing.parse.BllipParserServer;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class BllipParserService extends ServerResource {
	String text;
	BllipParserServer server = new BllipParserServer("/home/bllip-parser",
			"/home/bllip-parser/biomodel/parser", "/home/bllip-parser/biomodel/reranker");

	@Override
	public void doInit() {		
		this.text = (String) getRequestAttributes().get("text");
	}

	@Get
	public String toString() {
		return server.parse(text);
	}
}
