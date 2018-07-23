/**
 * Class for storing User info.
 * @author m.goncalves
 */
package main.com.familytree.persistence.person.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "\"TREE\".\"Tree.Data::MainEntities.Person\"")
@NamedQueries({ @NamedQuery(name = "AllUser", query = "select c from Person c"), })

public class Person implements Serializable {
	private static final long serialVersionUID = 88123489089299L;

	@Id
  	@Column(name = "\"id\"")
  	private Integer id;

	@Column(name = "\"firstName\"")
	private String firstName;

	@Column(name = "\"lastName\"")
	private String lastName;

	@Column(name = "\"birthDate\"")
	private LocalDate birthDate;

	@Column(name = "\"deathDate\"")
	private LocalDate deathDate;

	@Column(name = "\"gender\"")
	private Integer gender;

	@Column(name = "\"ownerId\"")
	private Integer ownerId;

	@Column(name = "\"linkId\"")
	private Integer linkId;

	@Column(name = "\"fatherId\"")
	private Integer fatherId;

	@Column(name = "\"motherId\"")
	private Integer motherId;

	@Column(name = "\"relatedId\"")
	private Integer relatedId;

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthDate
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the deathDate
	 */
	public LocalDate getDeathDate() {
		return deathDate;
	}

	/**
	 * @param deathDate the deathDate to set
	 */
	public void setDeathDate(LocalDate deathDate) {
		this.deathDate = deathDate;
	}

	/**
	 * @return the gender
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * @return the ownerId
	 */
	public Integer getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the linkID
	 */
	public Integer getLinkId() {
		return linkId;
	}

	/**
	 * @param linkID the linkID to set
	 */
	public void setLinkId(Integer linkID) {
		this.linkId = linkID;
	}

	/**
	 * @return the fatherId
	 */
	public Integer getFatherId() {
		return fatherId;
	}

	/**
	 * @param fatherId the fatherId to set
	 */
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	/**
	 * @return the motherId
	 */
	public Integer getMotherId() {
		return motherId;
	}

	/**
	 * @param motherId the motherId to set
	 */
	public void setMotherId(Integer motherId) {
		this.motherId = motherId;
	}

	/**
	 * @return the relatedId
	 */
	public Integer getRelatedId() {
		return relatedId;
	}

	/**
	 * @param relatedId the relatedId to set
	 */
	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((fatherId == null) ? 0 : fatherId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((linkId == null) ? 0 : linkId.hashCode());
		result = prime * result + ((motherId == null) ? 0 : motherId.hashCode());
		result = prime * result + ((relatedId == null) ? 0 : relatedId.hashCode());
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
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
		Person other = (Person) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (fatherId == null) {
			if (other.fatherId != null)
				return false;
		} else if (!fatherId.equals(other.fatherId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (linkId == null) {
			if (other.linkId != null)
				return false;
		} else if (!linkId.equals(other.linkId))
			return false;
		if (motherId == null) {
			if (other.motherId != null)
				return false;
		} else if (!motherId.equals(other.motherId))
			return false;
		if (relatedId == null) {
			if (other.relatedId != null)
				return false;
		} else if (!relatedId.equals(other.relatedId))
			return false;
		if (ownerId == null) {
			if (other.ownerId != null)
				return false;
		} else if (!ownerId.equals(other.ownerId))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person [id=" + id
				+ ", firstName=" + firstName
				+ ", lastName=" + lastName
				+ ", birthDate=" + birthDate
				+ ", deathDate=" + deathDate
				+ ", gender=" + gender
				+ ", ownerId=" + ownerId
				+ ", linkId=" + linkId
				+ ", fatherId=" + fatherId
				+ ", motherId=" + motherId
				+ ", relatedId=" + relatedId
				+ "]";
	}
}
