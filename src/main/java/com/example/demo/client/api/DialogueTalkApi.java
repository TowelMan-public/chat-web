package com.example.demo.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.client.exception.BadRequestFormException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotHaveUserException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * 友達トーク単体に関するAPIを呼び出すクラス
 */
@Component
public class DialogueTalkApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/dialogue/talk";
	
	/**
	 * 友達トークの作成をするAPI
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ユーザーのID名
	 * @param talkContentText
	 * @throws NotFoundException
	 * @throws NotHaveUserException
	 */
	public void insertTalk(UserDetailsImp user, String haveUserIdName, String talkContentText) 
			throws NotFoundException, NotHaveUserException{
		final String URL = ROOT_URL + "/insert";
		
		var dto = new Dto();
		dto.setHaveUserIdName(haveUserIdName);
		dto.setTalkContentText(talkContentText);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達トークの取得をするAPI
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ユーザーのID名
	 * @param talkIndex　トークインデックス
	 * @return 友達トーク
	 * @throws NotFoundException
	 * @throws NotHaveUserException
	 */
	public TalkResponse getTalk(UserDetailsImp user, String haveUserIdName, Integer talkIndex) 
			throws NotFoundException, NotHaveUserException{
		final String URL = ROOT_URL + "/get";
		
		var dto = new Dto();
		dto.setHaveUserIdName(haveUserIdName);
		dto.setTalkIndex(talkIndex);
		
		return restTemplateAdapter.getForObjectWhenLogined(URL, dto, TalkResponse.class, user);
	}
	
	/**
	 * 友達トークの更新をするAPI
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ユーザーのID名
	 * @param talkIndex トークインデックス
	 * @param talkContentText トークテキスト
	 * @throws NotFoundException
	 * @throws NotHaveUserException
	 * @throws BadRequestFormException
	 */
	public void updateTalk(UserDetailsImp user, String haveUserIdName, Integer talkIndex, String talkContentText) 
			throws NotFoundException, NotHaveUserException, BadRequestFormException{
		final String URL = ROOT_URL + "/update";
		
		var dto = new Dto();
		dto.setHaveUserIdName(haveUserIdName);
		dto.setTalkIndex(talkIndex);
		dto.setTalkContentText(talkContentText);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達トークの削除をするAPI
	 * @param user　ログイン情報
	 * @param haveUserIdName 友達ユーザーのID名
	 * @param talkIndex トークインデックス
	 * @throws NotFoundException
	 * @throws NotHaveUserException
	 * @throws BadRequestFormException
	 */
	public void deleteTalk(UserDetailsImp user, String haveUserIdName, Integer talkIndex) 
			throws NotFoundException, NotHaveUserException, BadRequestFormException{
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setHaveUserIdName(haveUserIdName);
		dto.setTalkIndex(talkIndex);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達トーク単体に関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private String haveUserIdName;
		private String talkContentText;
		private Integer talkIndex;
	}
}
