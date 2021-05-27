package com.example.demo.client.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.HaveUserResponse;
import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotHaveUserException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * ユーザーが持っている友達に関するAPIを呼び出すクラス
 */
@Component
public class UserInDialogueApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/diarogue/user";
	
	/**
	 * 友達リストの取得をするAPI
	 * @param user ログイン情報
	 * @return 友達リスト
	 */
	public List<HaveUserResponse> getUserInDiarogueList(UserDetailsImp user) {
		final String URL = ROOT_URL + "/gets";
		
		return restTemplateAdapter.getForListWhenLogined(URL, null, HaveUserResponse[].class, user);
	}
	
	/**
	 * 友達追加をするAPI
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @throws NotFoundException
	 * @throws AlreadyHaveUserException
	 */
	public void insertUserInDiarogue(UserDetailsImp user, String userIdName) 
			throws NotFoundException, AlreadyHaveUserException {
		final String URL = ROOT_URL + "/insert";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達を削除する
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @throws NotFoundException
	 * @throws NotHaveUserException
	 */
	public void deleteUserInDiarogue(UserDetailsImp user, String userIdName) 
			throws NotFoundException, NotHaveUserException {
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達追加申請に関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private String userIdName;
	}
}
