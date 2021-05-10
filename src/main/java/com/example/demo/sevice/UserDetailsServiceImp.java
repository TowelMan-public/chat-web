package com.example.demo.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserApi;

/**
 * ログイン処理のビジネスロジックを書くクラス
 */
@Service
public class UserDetailsServiceImp{
	@Autowired
	UserApi userApi;
	
	/**
	 * ログインのために認証用トークンを取得する
	 * @param userIdName ユーザーID名
	 * @param password パスワード
	 * @return 認証用トークン
	 * @throws AuthenticationException ログインで何か問題が起きた、失敗した
	 */
	public String loginAndReturnToken(String userIdName,String password) throws AuthenticationException {
		return userApi.login(userIdName, password);
	}
	
}
