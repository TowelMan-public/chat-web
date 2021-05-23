package com.example.demo.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.UserEntity;
import com.example.demo.client.exception.AlreadyUsedUserIdNameException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * ユーザー情報に関するAPIを呼び出すクラス
 */
@Component
public class UserApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/user";
	
	/**
	 * ログインをするためのAPI
	 * @param userIdName ユーザーID名
	 * @param password パスワード
	 * @return 認証用トークン
	 * @throws AlreadyUsedUserIdNameException
	 */
	public String login(String userIdName, String password)
			throws AlreadyUsedUserIdNameException {
		final String URL = ROOT_URL + "/login";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		dto.setPassword(password);
		
		return restTemplateAdapter.postForObject(URL, dto, String.class)
				.getBody();
	}
	
	/**
	 * ユーザー登録をするAPI
	 * @param userName ユーザー名（ニックネーム）
	 * @param userIdName ユーザーID名
	 * @param password パスワード
	 * @throws NotFoundException
	 */
	public void insertUser(String userName, String userIdName, String password) 
			throws NotFoundException{
		final String URL = ROOT_URL + "/insert";
		
		var dto = new Dto();
		dto.setUserName(userName);
		dto.setUserIdName(userIdName);
		dto.setPassword(password);
		
		restTemplateAdapter.postForObject(URL, dto, null);
	}
	
	/**
	 * ユーザー情報を取得する
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @return ユーザー情報
	 */
	public UserEntity getUser(UserDetailsImp user, String userIdName){
		final String URL = ROOT_URL + "/get";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		
		return restTemplateAdapter.getForObjectWhenLogined(URL, dto, UserEntity.class, user);
	}
	
	/**
	 * ユーザーID名を変更する
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @throws AlreadyUsedUserIdNameException
	 */
	public void updateUserIdName(UserDetailsImp user, String userIdName)
			throws AlreadyUsedUserIdNameException{
		final String URL = ROOT_URL + "/update/id-name";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * ユーザー名の変更
	 * @param user ログイン情報
	 * @param userName ユーザー名
	 */
	public void updateUserName(UserDetailsImp user, String userName) {
		final String URL = ROOT_URL + "/update/name";
		
		var dto = new Dto();
		dto.setUserName(userName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * パスワードの更新
	 * @param user ログイン情報
	 * @param password パスワード
	 */
	public void updatePassword(UserDetailsImp user, String password) {
		final String URL = ROOT_URL + "/update/password";
		
		var dto = new Dto();
		dto.setPassword(password);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 退会
	 * @param user ログイン情報
	 */
	public void deleteUser(UserDetailsImp user) {
		final String URL = ROOT_URL + "/delete";
		
		restTemplateAdapter.postForObjectWhenLogined(URL, null, null, user);
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
