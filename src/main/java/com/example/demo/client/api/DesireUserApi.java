package com.example.demo.client.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.DesireHaveUserResponse;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * 友達追加申請に関するAPIを呼び出すクラス
 */
@Component
public class DesireUserApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/desire/user";
	
	/**
	 * 友達追加申請リストを取得するAPI
	 * @param user ログイン情報
	 * @return 友達追加申請リスト
	 */
	public List<DesireHaveUserResponse> getDesireUser(UserDetailsImp user) {
		final String URL = ROOT_URL + "/gets";
		
		return restTemplateAdapter.getForListWhenLogined(URL, null, DesireHaveUserResponse[].class, user);
	}
	
	/**
	 * 友達追加申請を断るAPI
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @throws NotFoundException
	 */
	public void deleteDesireUser(UserDetailsImp user, String userIdName) 
			throws NotFoundException {
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達追加申請を受けるAPI
	 * @param user ログイン情報
	 * @param userIdName ユーザーID名
	 * @throws NotFoundException
	 */
	public void joinUser(UserDetailsImp user, String userIdName) 
			throws NotFoundException {
		final String URL = ROOT_URL + "/join";
		
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
