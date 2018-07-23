package main.com.familytree.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserServices {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(){
		return "Hello World!";
	}
	
}
