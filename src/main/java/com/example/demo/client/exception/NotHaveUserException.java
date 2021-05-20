package com.example.demo.client.exception;

public class NotHaveUserException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public NotHaveUserException(){
		super("This user is not inserted in diarogue");
	}
}