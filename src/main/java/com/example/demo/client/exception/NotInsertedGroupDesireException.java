package com.example.demo.client.exception;

public class NotInsertedGroupDesireException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public NotInsertedGroupDesireException(){
		super("This user is not inserted 'Desire Group'");
	}
}