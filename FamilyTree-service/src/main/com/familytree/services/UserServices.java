package main.com.familytree.services;


import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.com.familytree.persistence.user.model.User;


public class UserServices {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User hello() {
		User u = new User(1, "EU", "123", "a@c.com", 1);
		return u;
	}
	
}
