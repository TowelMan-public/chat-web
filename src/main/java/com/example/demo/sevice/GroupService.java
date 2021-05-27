package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.GroupApi;
import com.example.demo.client.api.entity.GroupTalkRoomResponse;
import com.example.demo.form.inner.GroupListModel;
import com.example.demo.security.UserDetailsImp;

/**
 * グループに関するビジネスロジック
 */
@Service
public class GroupService {

	@Autowired
	GroupApi groupApi;
	
	/**
	 * グループを作る
	 * @param user ログイン情報
	 * @param groupName グループ名
	 */
	public void createGroup(UserDetailsImp user, String groupName) {
		groupApi.insertGroup(user, groupName);
	}

	/**
	 * グループリスト（html向け）を取得する
	 * @param user ログイン情報
	 * @return グループリスト（html向け）
	 */
	public List<GroupListModel> getGroupList(UserDetailsImp user) {
		//データ習得・宣言
		List<GroupTalkRoomResponse> responseList = groupApi.getGroups(user);
		List<GroupListModel> modelList = new ArrayList<>();
		
		//処理
		for(GroupTalkRoomResponse response : responseList)
			modelList.add(new GroupListModel(response));
		
		return modelList;
	}

	/**
	 * グループ名を取得する
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @return グループ名
	 */
	public String getGroupName(UserDetailsImp user, Integer groupTalkRoomId) {
		return groupApi.getGroup(user, groupTalkRoomId)
					.getGroupName();
	}

	/**
	 * グループ名を更新する
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param groupName グループ名
	 */
	public void updateGroupName(UserDetailsImp user, Integer groupTalkRoomId, String groupName) {
		groupApi.updateGroupName(user, groupTalkRoomId, groupName);
	}

	/**
	 * グループを削除する
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 */
	public void deleteGroup(UserDetailsImp user, Integer groupTalkRoomId) {
		groupApi.deleteGroup(user, groupTalkRoomId);
	}
	
}