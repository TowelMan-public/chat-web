package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.GroupApi;
import com.example.demo.client.api.entity.GroupTalkRoomResponse;
import com.example.demo.form.inner.GroupListModel;
import com.example.demo.security.UserDetailsImp;

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
	
}