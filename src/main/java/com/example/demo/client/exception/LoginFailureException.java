package com.example.demo.client.exception;

public class LoginFailureException  extends RuntimeException{
	//warningを回避するための宣言
	private static final long serialVersionUID = 1L; 
	
	public LoginFailureException(String message){
		super(message);
	}
}