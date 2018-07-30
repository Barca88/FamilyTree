package com.familytree.services.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.familytree.persistence.person.model.Person;
import com.familytree.persistence.user.model.User;
import com.familytree.services.models.Line;
import com.familytree.services.models.Group;

public class Return {
	private User user;
	private List<Person> nodes;
	private List<Line> lines;
	private List<Group> groups;

	public Return(){
	}
	public Return(User u, List<Person> p, List<Line> l, List<Group> g){
		ArrayList<Person> nodes = new ArrayList<Person>();
		ArrayList<Line> lines = new ArrayList<Line>();
		ArrayList<Group> groups = new ArrayList<Group>();
		for(Person person : p) nodes.add(person.clone());
		for(Line line : l) lines.add(line.clone());
		for(Group group : g) groups.add(group.clone());
		this.user = u.clone();
		this.nodes = nodes;
		this.lines = lines;
		this.groups = groups;
	}
	public Return(Return r){
		this.user = r.getUser();
		this.nodes = r.getNodes();
		this.lines = r.getLines();
		this.groups = r.getGroups();
	}
	public void addPersons(Map<Integer,Person> map){
		this.setNodes(map.values());
	}
	public Map<Integer,Person> addGroups(Map<Integer,Person> map){
		ArrayList<Group> groups = new ArrayList<Group>();
		//group
		int id = 0; // Group Id
		HashMap<Integer,Person> aux = new HashMap<Integer,Person>();
		HashMap<Integer,Integer> ignore = new HashMap<Integer,Integer>();
		Person person = new Person();
		boolean b = false;  // used to verify if the person is new
		for(Person p : map.values()){

			if(map.containsKey(p.getRelatedId()) && p.getRelatedId() != null && !ignore.containsKey(p.getId())){
				person = map.get(p.getRelatedId());
				person.setRelatedId(id);
				p.setRelatedId(id);
				b = true;

				if(p.getGender() == 1){
					groups.add(new Group(id, p.getLastName()));
					id++;

				} else {
					groups.add(new Group(id,person.getLastName()));
					id++;
				}

			}
			aux.put(p.getId(), p);
			if(b) { 
				aux.put(person.getId(), person);
				ignore.put(person.getId(), person.getId());
				b = false;
			}
			ignore.put(p.getId(), p.getId());
		}
		this.setGroups(groups);
		return aux;
	}
	public void addLines(Map<Integer,Person> map){
		ArrayList<Line> lines = new ArrayList<Line>();

		for(Person p: map.values()){
			if(p.getFatherId() != null){
				lines.add(new Line(p.getFatherId(), p.getId()));
			}
			if(p.getMotherId() != null){
				lines.add(new Line(p.getMotherId(),p.getId()));
			}
		}
		this.setLines(lines);
	}
	public User getUser(){
		return this.user.clone();
	}

	public List<Person> getNodes(){
		ArrayList<Person> r = new ArrayList<Person>();
		for(Person person : nodes) r.add(person.clone());
		return r;
	}  
	public List<Line> getLines(){
		ArrayList<Line> r = new ArrayList<Line>();
		for(Line line : lines) r.add(line.clone());
		return r;
	}
	public List<Group> getGroups(){
		ArrayList<Group> r = new ArrayList<Group>();
		for(Group group : groups) r.add(group.clone());
		return r;
	}
	public void setUser(User u){
		this.user = u.clone();
	}
	public void setNodes(Collection<Person> p){   // Não sei se funciona 
		ArrayList<Person> r = new ArrayList<Person>();
		for(Person person : p) r.add(person.clone());
		this.nodes = r;
	}
	public void setLines(List<Line> l){
		ArrayList<Line> r = new ArrayList<Line>();
		for(Line line : l) r.add(line.clone());
		this.lines = r;
	}
	public void setGroups(List<Group> g){
		ArrayList<Group> r = new ArrayList<Group>();
		for(Group group : g) r.add(group.clone());
		this.groups = r;
	}
	public Return clone(){
		return new Return(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Return [user=" + user + ", nodes=" + nodes + ", lines=" + lines + ", groups=" + groups + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
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
		Return other = (Return) obj;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (lines == null) {
			if (other.lines != null)
				return false;
		} else if (!lines.equals(other.lines))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}
}