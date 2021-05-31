package com.example.demo.exception;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public ValidationException(){
		super("Validation Error");
	}
}