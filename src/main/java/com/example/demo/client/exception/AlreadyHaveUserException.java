package com.example.demo.client.exception;

public class AlreadyHaveUserException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public AlreadyHaveUserException(){
		super("This user is already inserted in diarogue by you");
	}
}