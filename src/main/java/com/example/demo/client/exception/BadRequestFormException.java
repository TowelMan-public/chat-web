package com.example.demo.client.exception;

public class BadRequestFormException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public BadRequestFormException(String message){
		super(message);
	}
}
