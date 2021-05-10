package com.example.demo.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.rest.RestTemplateAdapter;

import lombok.Data;

/**
 * ユーザー情報に関するapiを呼び出すクラス
 */
@Component
public class UserApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/user";
	
	/**
	 * ログインをするためのapi
	 * @param userIdName ユーザーID名
	 * @param password パスワード
	 * @return 認証用トークン
	 */
	public String login(String userIdName, String password) {
		final String URL = ROOT_URL + "/login";
		
		Dto dto = new Dto();
		dto.setUserIdName(userIdName);
		dto.setPassword(password);
		
		return restTemplateAdapter.postForObject(URL, dto, String.class)
				.getBody();
	}
	
	/**
	 * ユーザー情報に関するapiのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private String userName;
		private String userIdName;
		private String password;
	}
}
