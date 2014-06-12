package org.nii.phenominer.processing.rest;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class UserResource extends ServerResource {
	String userName;
	Object user;

	@Override
	public void doInit() {
		this.userName = (String) getRequestAttributes().get("user");
		this.user = null; // Could be a lookup to a domain object.
	}

	@Get
	public String toString() {
		return "Account of user \"" + this.userName + "\"";
	}
}
