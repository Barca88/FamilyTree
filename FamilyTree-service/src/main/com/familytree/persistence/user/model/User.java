/**
* Class for storing User info.
* @author m.goncalves
*/
package main.com.familytree.persistence.user.model;
		
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "\"TREE\".\"Tree.Data::MainEntities.User\"")
@NamedQueries({ @NamedQuery(name = "AllUser", query = "select c from User c"), })

public class User implements Serializable {
	private static final long serialVersionUID = 981027347165437L;

	@Id
	@Column(name = "\"id\"")
	private Integer id;

	@Column(name = "\"userName\"", nullable = false)
	private String userName;

	@Column(name = "\"password\"", nullable = false)
	private String password;

	@Column(name = "\"email\"", nullable = false)
	private String email;

	@Column(name = "\"personID\"", nullable = true)
	private Integer personID;
	
	public User(){

	}
	public User(Integer id, String userName, String password, String email, Integer personId){
		this.setId(id);
		this.setUserName(userName);
		this.setPassword(password);
		this.setEmail(email);
		this.setPersonID(personId);
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((personID == null) ? 0 : personID.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", personID=" + personID + "]";
	}

}