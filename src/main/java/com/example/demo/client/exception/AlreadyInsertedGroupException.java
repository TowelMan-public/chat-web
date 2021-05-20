package com.example.demo.client.exception;

public class AlreadyInsertedGroupException extends RuntimeException{
	private static final long serialVersionUID = 1L; 
	
	public AlreadyInsertedGroupException(){
		super("User is already inserted in this group!");
	}
}