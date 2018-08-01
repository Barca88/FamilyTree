package com.familytree.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.familytree.persistence.person.dao.PersonBean;
import com.familytree.persistence.person.model.Person;
import com.familytree.persistence.user.dao.UserBean;
import com.familytree.persistence.user.model.User;
import com.familytree.services.models.Line;
import com.familytree.services.models.Return;
import com.familytree.util.GsonFactory;
import com.familytree.util.RequestHandler;
import com.google.gson.Gson;

/**
 * Servlet implementing a simple EJB based persistence sample application for SAP Cloud Platform.
 */
public class UserServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);
	private static final Gson frontEndGson = GsonFactory.getInstance().createFrontEndGson();

	@EJB
	private UserBean userBean;

	@EJB
	private PersonBean personBean;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Return r = new Return();
			String s = request.getRemoteUser();

			if(!userBean.existUser(s)){
				User u = new User();
				u.setUserName(s);
				userBean.addUser(u);
				r.setUser(u);

			} else {
				User u = userBean.getUserByUserName(s);
				r.setUser(u);

				if(u.getPersonId() != null){
					Map<Integer,Person> map = personBean.getMapAllPersonsByUserId(u.getId());
					map = r.addGroups(map);
					r.addLines(map);		
					r.addPersons(map);
				}
			}
			response.getWriter().println(frontEndGson.toJson(r));
		} catch (Exception e) {
			LOGGER.error("Persistence operation failed", e);
		}
	} 

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		try { 
			String s = request.getRemoteUser();
			User u = userBean.getUserByUserName(s);

			Return data = frontEndGson.fromJson(RequestHandler.handleRequest(request),Return.class);

			HashMap<Integer,Person> newData = (HashMap<Integer, Person>) data.returnToMap();
			HashMap<Integer,Person> oldData = (HashMap<Integer, Person>) personBean.getMapAllPersonsByUserId(u.getId());
			//Set User PersonId
			if(u.getPersonId() == null && data.getUser().getPersonId() != null){
				userBean.updatePersonId(u.getId(),data.getUser().getPersonId());
			}
			//Delete
			for(Person p : oldData.values()){
				if(!newData.containsKey(p.getId())){
					personBean.deletePerson(p);
					oldData.remove(p.getId());
				}
			}
			//Create Persons
			for(Person p : newData.values()){
				if(p.getId()<0)	{
					Person added = new Person();
					added.setFirstName(p.getFirstName());
					added.setLastName(p.getLastName());
					added.setEmail(p.getEmail());
					added.setBirthDate(p.getBirthDate());
					added.setDeathDate(p.getDeathDate());
					added.setGender(p.getGender());
					added.setOwnerId(p.getOwnerId());
					added.setLinkId(p.getLinkId());
					added.setFatherId(p.getFatherId());
					added.setMotherId(p.getMotherId());
					added.setRelatedId(p.getRelatedId()); 
					System.out.println("Added = "+added);

					personBean.addPerson(added);               //Create Person on db

					newId(newData,p.getId(),added.getId());		//Update id's

					newData.put(added.getId(),added);
					newData.remove(p.getId()); 
					System.out.println("added id = " + added.getId());

				}
			}

			oldData = (HashMap<Integer, Person>) personBean.getMapAllPersonsByUserId(u.getId()); //update oldData whit db

			for(Person p : newData.values()){
				if(!p.equals(oldData.get(p.getId()))){
					personBean.updatePerson(p);
				}
			}
		} catch (Exception e) {
			response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
			LOGGER.error("Persistence operation failed", e);
		}
	}
	/**
	 * Function used to update the user id created on the front end to the real id
	 * @param map
	 * @param o
	 * @param n
	 */
	private void newId(Map<Integer,Person> map,Integer o,Integer n){
		for(Person p : map.values()){
			if(p.getFatherId() == o) p.setFatherId(n);
			if(p.getMotherId() == o) p.setMotherId(n);
			if(p.getRelatedId() == o) p.setRelatedId(n);
		}
	}

	private void appendPersonTable(HttpServletResponse response) throws SQLException, IOException {
		// Append table that lists all persons
		List<User> resultList = userBean.getAllUsers();
		response.getWriter().println(
				"<p><table border=\"1\"><tr><th colspan=\"3\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
				+ "Entries in the Database</th></tr>");
		if (resultList.isEmpty()) {
			response.getWriter().println("<tr><td colspan=\"3\">Database is empty</td></tr>");
		} else {
			response.getWriter().println("<tr><th>First name</th><th>Last name</th><th>Id</th></tr>");
		}
		// IXSSEncoder xssEncoder = XSSEncoder.getInstance();
		int iterator = 1;
		for (User p : resultList) {
			response.getWriter().println(
					"</td><td>"
							+ p.getUserName()
							+ "</td><td>"
							+ iterator++
							+ "</td></tr>");
		}
		response.getWriter().println("</table></p>");
	}

	private void appendAddForm(HttpServletResponse response) throws IOException {
		// Append form through which new persons can be added
		response.getWriter().println(
				"<p><form action=\"\" method=\"post\">" + "First name:<input type=\"text\" name=\"FirstName\">"
						+ "&nbsp;Last name:<input type=\"text\" name=\"LastName\">"
						+ "&nbsp;<input type=\"submit\" value=\"Add Person\">" + "</form></p>");
	}

	private void doAdd(HttpServletRequest request) throws ServletException, IOException, SQLException {
		// Extract name of person to be added from request
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");

		// Add person if name is not null/empty
		if (firstName != null && lastName != null && !firstName.trim().isEmpty() && !lastName.trim().isEmpty()) {
			// User person = new User();
			// person.setFirstName(firstName.trim());
			// person.setLastName(lastName.trim());
			// userBean.addPerson(person);
		}
	}
}
