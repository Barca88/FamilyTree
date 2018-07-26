package com.familytree.persistence.person.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public List<User> getAllPersons() {
		return em.createNamedQuery("AllPersons", User.class).getResultList();
    }

	/**
	 * Get all persons from the table.
	 */
	@SuppressWarnings("unchecked")
	public User getUserById(Integer id) {
		List<User> u = em.createQuery("from User as u where u.id = :id").setParameter("id", id).getResultList();
		if (u.isEmpty()) {
			return new User();
		} else {
			return u.get(0);
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