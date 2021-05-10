package com.example.demo.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserApi;

@Service
public class UserDetailsServiceImp{
	@Autowired
	UserApi userApi;
	
	public String loginAndReturnToken(String userIdName,String password) throws AuthenticationException {
		return userApi.login(userIdName, password);
	}
	
}
