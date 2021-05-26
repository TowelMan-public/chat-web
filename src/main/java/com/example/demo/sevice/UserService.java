package com.example.demo.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserApi;
import com.example.demo.client.exception.AlreadyUsedUserIdNameException;
import com.example.demo.form.insert.SignupForm;

@Service
public class UserService {
	
	@Autowired
	UserApi userApi;

	/**
	 * ユーザー登録をする
	 * @param form ユーザー登録に使う情報
	 * @throws AlreadyUsedUserIdNameException
	 */
	public void insertUser(SignupForm form) throws AlreadyUsedUserIdNameException {
		userApi.insertUser(form.getUserName(), form.getUserIdName(), form.getPassword());
	}
	
}
