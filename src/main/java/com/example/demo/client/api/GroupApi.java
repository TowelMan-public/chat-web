package com.example.demo.client.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.GroupTalkRoomEntity;
import com.example.demo.client.api.entity.GroupTalkRoomResponse;
import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotJoinGroupException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * グループに関するAPIを呼び出すクラス
 */
@Component
public class GroupApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/group";
	
	/**
	 * グループ情報を取得するAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @return グループ情報
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public GroupTalkRoomEntity getGroup(UserDetailsImp user, Integer talkRoomId) 
			throws NotJoinGroupException, NotFoundException {
		final String URL = ROOT_URL + "/get";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		
		return restTemplateAdapter.getForObjectWhenLogined(URL, dto, GroupTalkRoomEntity.class, user);
	}
	
	/**
	 * 加入してるグループリストの取得をするAPI
	 * @param user ログイン情報
	 * @return グループリスト
	 */
	public List<GroupTalkRoomResponse> getGroups(UserDetailsImp user) 
			{
		final String URL = ROOT_URL + "/gets";
		
		return restTemplateAdapter.getForObjectsWhenLogined(URL, null, GroupTalkRoomResponse.class, user);
	}
	
	/**
	 * グループ名を変更するAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @param groupName グループ名
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public void updateGroupName(UserDetailsImp user, Integer talkRoomId, String groupName) 
			throws NotJoinGroupException, NotFoundException{
		final String URL = ROOT_URL + "/update/name";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		dto.setGroupName(groupName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループトークリストの取得をするAPI
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @param startIndex 最初のトークインデックス
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 * @throws NotFoundException
	 * @throws NotJoinGroupException
	 */
	public List<TalkResponse> getGroupTalks(UserDetailsImp user, Integer talkRoomId, Integer startIndex, Integer maxSize) 
			throws NotFoundException, NotJoinGroupException {
		final String URL = ROOT_URL + "/gets/talks";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		dto.setStartIndex(startIndex);
		dto.setMaxSize(maxSize);
		
		return restTemplateAdapter.getForObjectsWhenLogined(URL, dto, TalkResponse.class, user);
	}
	
	/**
	 * グループの削除
	 * @param user ログイン情報
	 * @param talkRoomid グループトークルームID
	 * @throws NotJoinGroupException
	 * @throws NotFoundException
	 */
	public void deleteGroup(UserDetailsImp user, Integer talkRoomid) 
			throws NotJoinGroupException, NotFoundException{
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomid);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループの作成
	 * @param user ログイン情報
	 * @param groupName グループ名
	 */
	public void insertGroup(UserDetailsImp user, String groupName) 
			{
		final String URL = ROOT_URL + "/insert";
		
		var dto = new Dto();
		dto.setGroupName(groupName);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達トークに関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private Integer talkRoomId;
		private String groupName;
		private Integer maxSize;
		private Integer startIndex;
	}
}
