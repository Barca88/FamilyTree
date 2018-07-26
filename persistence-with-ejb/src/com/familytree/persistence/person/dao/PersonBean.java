package com.familytree.persistence.person.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.familytree.persistence.person.model.Person;
import com.familytree.persistence.user.model.User;

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
		return em.createNamedQuery("AllPersons", Person.class).getResultList();
    }

	/**
	 * Get all persons from the table.
	 */
	@SuppressWarnings("unchecked")
	public Person getPersonByOwnerId(Integer ownerId) {
		List<Person> p = em.createQuery("from Person as p where p.ownerId = :ownerId").setParameter("ownerId", ownerId).getResultList();
		if (p.isEmpty()) {
			return new Person();
		} else {
			return p.get(0);
		}
	}

    /**
     * Add a person to the table.
     */
	public void addPerson(User person) {
        em.persist(person);
        em.flush();
    }
}