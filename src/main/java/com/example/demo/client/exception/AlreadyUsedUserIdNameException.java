package com.example.demo.client.exception;

public class AlreadyUsedUserIdNameException extends RuntimeException{
	private static final long serialVersionUID = 1L; 
	
	public AlreadyUsedUserIdNameException(){
		super("This userIdname is already used");
	}
}
