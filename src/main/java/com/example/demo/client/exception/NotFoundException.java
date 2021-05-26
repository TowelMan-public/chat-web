package com.example.demo.client.exception;

import java.util.regex.Pattern;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
	public NotFoundException(String message){
		super(message);
	}
	
	public String getErrorFieldName() {
		//正規表現のパターン
		final var patternString = "^(.*) is not found$";
		
		//項目（ここではfieldName）を取得する
		var matcher = Pattern.compile(patternString)
							.matcher(super.getMessage());
		matcher.matches();
		return matcher.group(1);
	}

	public boolean isErrorFieldUserIdName() {
		return getErrorFieldName().equals("userIdName");
	}
}