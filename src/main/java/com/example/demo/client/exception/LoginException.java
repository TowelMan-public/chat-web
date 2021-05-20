package com.example.demo.client.exception;

public class LoginException extends RuntimeException{
	//warningを回避するための宣言
	private static final long serialVersionUID = 1L; 
	
	public LoginException(String message){
		super(message);
	}
}