package com.example.demo.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.client.exception.BadRequestFormException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotJoinGroupException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * グループトーク単体に関するAPIを呼び出すクラス
 */
@Component
public class GroupTalkApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/group/talk";
	
	/**
	 * グループトークの作成をするAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @param talkContentText トークテキスト
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public void insertTalk(UserDetailsImp user, Integer talkRoomId, String talkContentText) 
			throws NotJoinGroupException, NotFoundException{
		final String URL = ROOT_URL + "/insert";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		dto.setTalkContentText(talkContentText);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループトークの取得をするAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @param talkIndex トークインデックス
	 * @return グループトーク
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public TalkResponse getTalk(UserDetailsImp user, Integer talkRoomId, Integer talkIndex) 
			throws NotJoinGroupException, NotFoundException{
		final String URL = ROOT_URL + "/get";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		dto.setTalkIndex(talkIndex);
		
		return restTemplateAdapter.getForObjectWhenLogined(URL, dto, TalkResponse.class, user);
	}
	
	/**
	 * グループトークの更新をするAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @param talkIndex トークインデックス
	 * @param talkContentText トークテキスト
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 * @throws BadRequestFormException
	 */
	public void updateTalk(UserDetailsImp user, Integer talkRoomId, Integer talkIndex, String talkContentText) 
			throws NotJoinGroupException, NotFoundException, BadRequestFormException{
		final String URL = ROOT_URL + "/update";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		dto.setTalkIndex(talkIndex);
		dto.setTalkContentText(talkContentText);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループトークの作成をするAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @param talkIndex トークインデクス
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 * @throws BadRequestFormException
	 */
	public void deleteTalk(UserDetailsImp user, Integer talkRoomId, Integer talkIndex) 
			throws NotJoinGroupException, NotFoundException, BadRequestFormException{
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		dto.setTalkIndex(talkIndex);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループトーク単体に関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private Integer talkRoomId;
		private String talkContentText;
		private Integer talkIndex;
	}
}
