package com.familytree.exeptions;

import java.io.Serializable;

public class NonexistentUserExeption extends Exception implements Serializable{
	private static final long serialVersionUID = 8453741291556098301L;

	public NonexistentUserExeption(String message)
    {
        super(message);
    }
}