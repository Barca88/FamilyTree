package com.familytree.persistence.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"FAMILYTREE\".\"familyTree.Data::MainEntities.User\"")
@NamedQueries({ @NamedQuery(name = "AllUsers", query = "select c from User c"), 
@NamedQuery(name="updatePersonId",query="update User set personId = :personId where id = :id") })
public class User implements Serializable {
	private static final long serialVersionUID = 981027347165437L;

	@Id
	@SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "\"FAMILYTREE\".\"familyTree.Data.sequences::UserSequence\"", allocationSize = 1) 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR") 
	@Column(name = "\"id\"")
	private Integer id;

	@Column(name = "\"userName\"", nullable = false)
	private String userName;

	@Column(name = "\"personId\"", nullable = true)
	private Integer personId;

	public User(){

	}
	public User(Integer id, String name, Integer personId){
		this.id = id;
		this.userName = name;
		this.personId = personId;
	}
	public User(User u){
		this.id = u.getId();
		this.userName = u.getUserName();
		this.personId = u.getPersonId();
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
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
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
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
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
		return "User [id=" + id + ", userName=" + userName + ", personId=" + personId + "]";
	}



}