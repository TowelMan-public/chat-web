package com.example.demo.client.exception;

public class NotJoinGroupException 	extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public NotJoinGroupException(){
		super("This user is not inserted group");
	}
}