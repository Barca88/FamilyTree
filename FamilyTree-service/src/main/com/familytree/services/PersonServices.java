package main.com.familytree.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;

import main.com.familytree.common.BaseService;
import main.com.familytree.persistence.person.model.Person;

public class PersonServices extends BaseService{
	/**
	 * GetPerson
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("id") int id) {
		Person p = new Person(id, "First"+id, "Last", LocalDate.now(), LocalDate.now(), 1,1,1,1,2,3);
		return p;
	}
	/**
	 * UpdatePerson
	 * @param p
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person updatePerson(Person p){
		//chamar o facade
		return p;
	}
	/**
	 * NewPerson
	 * @param p
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person newPerson(Person p){
		this.response.setStatus(Response.Status.CREATED.getStatusCode()); 
		return p;
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePerson(Person p){
		return "Person Deleted!";
	}
}