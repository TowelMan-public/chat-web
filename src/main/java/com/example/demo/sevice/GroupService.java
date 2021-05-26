package com.example.demo.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.GroupApi;
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
	
}