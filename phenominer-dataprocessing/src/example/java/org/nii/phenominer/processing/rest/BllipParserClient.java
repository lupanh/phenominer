package org.nii.phenominer.processing.rest;

import org.restlet.resource.ClientResource;

public class BllipParserClient {

	public static void main(String[] args) throws Exception {
		ClientResource client;
		for (int i = 0; i < 100; i++) {
			String text = "In this tutorial, we will create a method that listens a POST request from a client and returns an appropriated message. We reuse class created in the previous tutorial.";
			client = new ClientResource("http://23.92.53.181:8111/bllip=" + text + i + "/tokenized=true");
			client.get().write(System.out);	
		}
		
	}

}
