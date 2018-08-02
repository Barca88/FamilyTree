package com.familytree.persistence.person.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.familytree.persistence.person.model.Person;

/**
 * Bean encapsulating all operations for a person.
 */
@Stateless
@LocalBean
public class PersonBean {
    @PersistenceContext
    private EntityManager em;

    /**
     * Get all persons from the table.
     */
	public List<Person> getAllPersons() {
		List<Person> persons = em.createNamedQuery("AllPersons", Person.class).getResultList();
		return persons;
    }
	/**
     * Get Map of all persons from the table.
     */
	public Map<Integer,Person> getMapAllPersons() {
		HashMap<Integer,Person> r = new HashMap<Integer,Person>();
		List<Person> l = this.getAllPersons();
		for(Person p: l) r.put(p.getId(), p);

		return r;
    }

	/**
	 * Get all persons from the table.
	 */
	@SuppressWarnings("unchecked")
	public List<Person> getPersonByOwnerId(Integer ownerId) {
		List<Person> p = em.createQuery("from Person as p where p.ownerId = :ownerId").setParameter("ownerId", ownerId).getResultList();
		return p;
	}
	/**
     * Get Map of all persons from the table by user id.
     */
	public Map<Integer,Person> getMapAllPersonsByUserId(Integer id) {
		HashMap<Integer,Person> r = new HashMap<Integer,Person>();
		List<Person> l = this.getPersonByOwnerId(id);
		for(Person p: l) r.put(p.getId(), p);
		
		return r;
    }
    /**
     * Add a person to the table.
     */
	public Person addPerson(Person person) {
        em.persist(person);
        em.flush();
        return person;
    }
	public void deletePerson(Person p){
		em.remove(em.merge(p));
	}
	public void updatePerson(Person n) {
		em.merge(n);
	}
}