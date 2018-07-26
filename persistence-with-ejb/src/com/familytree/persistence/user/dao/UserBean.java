package com.familytree.persistence.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    /**
     * Get all persons from the table.
     */
	public List<User> getAllPersons() {
		return em.createNamedQuery("AllPersons", User.class).getResultList();
    }
	public Map<Integer,User> getMapAllPersons(){
		HashMap<Integer,User> map = new HashMap<Integer,User>();
		for(User u : em.createNamedQuery("AllPersons", User.class).getResultList()){
			map.put(u.getId(), u.clone());
		}
		return map;
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
	public void addUser(User user) {
        em.persist(user);
        em.flush();
    }
	//por testar
	public boolean existUser(String userName){
		if(em.createQuery("form User as u where u.userName = :userName").setParameter("userName", userName) != null){
			return true;
		}
		return false;
	}
}
