package com.example.demo.client.exception;

public class InvalidLoginException  extends RuntimeException{
	//warningを回避するための宣言
	private static final long serialVersionUID = 1L; 
	
	public InvalidLoginException(String message){
		super(message);
	}
}