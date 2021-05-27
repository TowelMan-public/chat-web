package com.example.demo.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserInGroupApi;
import com.example.demo.client.api.entity.UserInGroupResponse;
import com.example.demo.client.exception.AlreadyInsertedGroupDesireException;
import com.example.demo.client.exception.AlreadyInsertedGroupException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotJoinGroupException;
import com.example.demo.security.UserDetailsImp;

/**
 * グループ加入者に関するビジネスロジック
 */
@Service
public class UserInGroupService {

	@Autowired
	UserInGroupApi userInGroupApi;
	
	/**
	 * グループ加入者リストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @return グループ加入者リスト
	 */
	public List<UserInGroupResponse> getUserInGroupList(UserDetailsImp user, Integer groupTalkRoomId) {
		return userInGroupApi.getUsersInGroup(user, groupTalkRoomId);
	}

	/**
	 * ユーザーをグループに加入させる
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param userIdName グループに加入させたいユーザーID名
	 */
	public void insertUserInGroup(UserDetailsImp user, Integer groupTalkRoomId, String userIdName) 
			throws NotFoundException, AlreadyInsertedGroupDesireException, AlreadyInsertedGroupException {
		userInGroupApi.insertUserInGroup(user, groupTalkRoomId, userIdName);
	}

	/**
	 * グループ加入者を脱退させる
	 * @param user ログイン情報
	 * @param groupTalkRoomId　グループトークルームID
	 * @param userIdName グループに加入してるユーザーID名
	 */
	public void deleteUserInGroup(UserDetailsImp user, Integer groupTalkRoomId, String userIdName) {
		userInGroupApi.deleteUserInGroup(user, groupTalkRoomId, userIdName);
	}

}
