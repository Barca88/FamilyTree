package com.familytree.services;

import java.io.IOException;
import java.util.ArrayList;
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
	private static final long serialVersionUID = 139285720938407L;
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
			String userName = request.getParameter("userName");
			if(userBean.existUser(userName)){
				s = userName;
			}
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
			Person person;

			Return data = frontEndGson.fromJson(RequestHandler.handleRequest(request),Return.class);

			HashMap<Integer,Person> newData = (HashMap<Integer, Person>) data.returnToMap();
			HashMap<Integer,Person> oldData = (HashMap<Integer, Person>) personBean.getMapAllPersonsByUserId(u.getId());
			
			//Set User PersonId
			if(u.getPersonId() == null && data.getUser().getPersonId() != null && 
					newData.containsKey(data.getUser().getPersonId())){

				person = newPerson(newData.remove(data.getUser().getPersonId()));
				newData.put(person.getId(), person);
				userBean.updatePersonId(u.getId(),person.getId());
				u = userBean.getUserByUserName(s);
				data.setUser(u);
			}
			
			//Delete
			for(Person p : oldData.values()){
				if(!newData.containsKey(p.getId()) && p.getId() != u.getId()){
					personBean.deletePerson(p);
				}
			}
			
			//Create Persons
			List<Person> news = new ArrayList<Person>();
			Person added;
			for(Person p : newData.values()){
				if(p.getId()<0)	{
					added = newPerson(p); 

					newId(newData, p.getId(), added.getId());		//Update id's

					news.add(added);
				} else {
					news.add(p);
				}
			}

			oldData = (HashMap<Integer, Person>) personBean.getMapAllPersonsByUserId(u.getId()); //update oldData whit db
			
			//update Persons
			for(Person p : news){
				if(!p.equals(oldData.get(p.getId()))){
					personBean.updatePerson(p);
				}
			}
			//return
			Return r = new Return();
			r.setUser(u);

			if(u.getPersonId() != null){
				Map<Integer,Person> map = personBean.getMapAllPersonsByUserId(u.getId());
				map = r.addGroups(map);
				r.addLines(map);		
				r.addPersons(map);
			}
			response.getWriter().println(frontEndGson.toJson(r));
		} catch (Exception e) {
			response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
			LOGGER.error("Persistence operation failed", e);
		}
	}
	/**
	 * Create a person on data base
	 * @param p
	 * @return
	 */
	public Person newPerson(Person p){
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

		return personBean.addPerson(added); 
	}
	/**
	 * Function used to update the user id created on the front end to the real id
	 * @param map
	 * @param o Temporary Id
	 * @param n New Id
	 */	 
	private void newId(Map<Integer,Person> map, Integer o, Integer n){
		for(Person p : map.values()){
			if(p.getFatherId() == o) p.setFatherId(n);
			if(p.getMotherId() == o) p.setMotherId(n);
			if(p.getRelatedId() == o) p.setRelatedId(n);
		}
	}
}
