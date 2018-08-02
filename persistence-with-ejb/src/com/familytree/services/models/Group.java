package com.familytree.services.models;

import java.io.Serializable;

public class Group implements Serializable{
	private static final long serialVersionUID = 2394082100993456L;
    private int id;
    private String name;

    public Group(){
    }
    public Group(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    public Group(Group g){
        this.id = g.getId();
        this.name = g.getName();
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public Group clone(){
        return new Group(this);
    }

    public boolean equals(Object obj){
        if (obj == this) return true;
        if (( obj == null) || (obj.getClass() != this.getClass()))
            return false;
        Group o = (Group) obj;
        return o.getId() == this.getId() && o.getName().equals(this.getName());
    }

    public String toString(){
        StringBuilder st= new StringBuilder();
        st.append("Id: ").append(this.id).append("\n");
        st.append("Name: ").append(this.name).append("\n");
        return st.toString();
    }

}

