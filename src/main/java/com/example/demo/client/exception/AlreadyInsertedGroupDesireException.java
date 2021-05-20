package com.example.demo.client.exception;

public class AlreadyInsertedGroupDesireException extends RuntimeException{
	private static final long serialVersionUID = 1L; 
	
	public AlreadyInsertedGroupDesireException(){
		super("User is already inserted in this group desire!");
	}
}