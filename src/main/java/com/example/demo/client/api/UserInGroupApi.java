package com.example.demo.client.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.UserInGroupResponse;
import com.example.demo.client.exception.AlreadyInsertedGroupDesireException;
import com.example.demo.client.exception.AlreadyInsertedGroupException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotJoinGroupException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * グループ加入者に関するAPIを呼び出すクラス
 */
@Component
public class UserInGroupApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/group/user";
	
	/**
	 * グループにユーザーを加入させるAPI
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param userIdName ユーザーID名
	 * @throws NotFoundException
	 * @throws NotJoinGroupException
	 * @throws AlreadyInsertedGroupDesireException
	 * @throws AlreadyInsertedGroupException
	 */
	public void insertUserInGroup(UserDetailsImp user, Integer groupTalkRoomId, String userIdName) 
			throws NotFoundException, NotJoinGroupException, AlreadyInsertedGroupDesireException, AlreadyInsertedGroupException {
		final String URL = ROOT_URL + "/insert";
		
		var dto = new Dto();
		dto.setTalkRoomId(groupTalkRoomId);
		dto.setUserIdName(userIdName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループ加入者リストの取得するAPI
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @return グループ加入者リスト
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public List<UserInGroupResponse> getUsersInGroup(UserDetailsImp user, Integer groupTalkRoomId) 
			throws NotJoinGroupException, NotFoundException{
		final String URL = ROOT_URL + "/gets";
		
		var dto = new Dto();
		dto.setTalkRoomId(groupTalkRoomId);
		
		return restTemplateAdapter.getForListWhenLogined(URL, dto, UserInGroupResponse[].class, user);
	}

	/**
	 * グループ加入者をグループから抜けさせる
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param userIdName ユーザーID名
	 * @throws NotFoundException
	 * @throws NotJoinGroupException
	 */
	public void deleteUserInGroup(UserDetailsImp user, Integer groupTalkRoomId, String userIdName) 
			throws NotFoundException, NotJoinGroupException{
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setTalkRoomId(groupTalkRoomId);
		dto.setUserIdName(userIdName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループから抜ける
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public void exitGroup(UserDetailsImp user, Integer groupTalkRoomId) 
			throws NotJoinGroupException, NotFoundException {
		final String URL = ROOT_URL + "/exit";
		
		var dto = new Dto();
		dto.setTalkRoomId(groupTalkRoomId);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループ加入者に関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private Integer talkRoomId;
		private String userIdName;
	}
}
