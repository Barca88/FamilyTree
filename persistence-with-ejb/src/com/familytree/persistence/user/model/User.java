package com.familytree.persistence.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "\"FAMILYTREE\".\"familyTree.Data::MainEntities.User\"")
@NamedQueries({ @NamedQuery(name = "AllPersons", query = "select c from User c"), })
public class User implements Serializable {
	private static final long serialVersionUID = 981027347165437L;

	@Id
	@Column(name = "\"id\"")
	private Integer id;

	@Column(name = "\"userName\"", nullable = false)
	private String userName;

	@Column(name = "\"personId\"", nullable = true)
	private Integer personID;
	
	public User(){
		
	}
	public User(Integer id, String name, Integer personId){
		this.id = id;
		this.userName = name;
		this.personID = personId;
	}
	public User(User u){
		this.id = u.getId();
		this.userName = u.getUserName();
		this.personID = u.getPersonID();
	}
	public User clone(){
		return new User(this);
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the personID
	 */
	public Integer getPersonID() {
		return personID;
	}

	/**
	 * @param personID the personID to set
	 */
	public void setPersonID(Integer personID) {
		this.personID = personID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((personID == null) ? 0 : personID.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (personID == null) {
			if (other.personID != null)
				return false;
		} else if (!personID.equals(other.personID))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", personID=" + personID + "]";
	}

	

}