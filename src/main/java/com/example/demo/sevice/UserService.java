package com.example.demo.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserApi;
import com.example.demo.client.api.entity.UserEntity;
import com.example.demo.client.exception.AlreadyUsedUserIdNameException;
import com.example.demo.form.insert.SignupForm;
import com.example.demo.security.UserDetailsImp;

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

	/**
	 * ユーザー情報を取得する
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @return ユーザー情報
	 */
	public UserEntity getUser(UserDetailsImp user, String userIdName) {
		return userApi.getUser(user, userIdName);
	}

	/**
	 * ユーザーID名を変更する
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @throws AlreadyUsedUserIdNameException
	 */
	public void updateUserIdName(UserDetailsImp user, String userIdName) throws AlreadyUsedUserIdNameException {
		userApi.updateUserIdName(user, userIdName);
		user.setUsername(userIdName);
	}

	/**
	 * ユーザー名を変更する
	 * @param user ログイン情報
	 * @param userName ユーザー名
	 */
	public void updateUserName(UserDetailsImp user, String userName) {
		userApi.updateUserName(user, userName);
	}

	/**
	 *  パスワードを変更する
	 * @param user ログイン情報
	 * @param password パスワード
	 */
	public void updatePassword(UserDetailsImp user, String password) {
		userApi.updatePassword(user, password);
	}

	public void deleteUser(UserDetailsImp user) {
		// TODO 自動生成されたメソッド・スタブ
		
	}	
}