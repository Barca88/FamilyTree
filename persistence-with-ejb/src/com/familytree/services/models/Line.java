package com.familytree.services.models;

import java.io.Serializable;

public class Line implements Serializable{
	private static final long serialVersionUID = 198794874212122488L;
	private Integer from;
	private Integer to;
	
	public Line(){
	}
	public Line(Integer f,Integer t){
		this.from = f;
		this.to = t;
	}
	public Line(Line l){
		this.from = l.getFrom();
		this.to = l.getTo();
	}
	public Line clone(){
		return new Line(this);
	}
	/**
	 * @return the from
	 */
	public Integer getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(Integer from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public Integer getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(Integer to) {
		this.to = to;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Line [from=" + from + ", to=" + to + "]";
	}

}
