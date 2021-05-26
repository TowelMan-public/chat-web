package com.example.demo.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.DesireGroupApi;
import com.example.demo.client.api.entity.DesireUserInGroupResponce;
import com.example.demo.security.UserDetailsImp;

@Service
public class DesireGroupService {
	@Autowired
	DesireGroupApi desireGroupApi;

	/**
	 * グループに加入してほしい申請リストの取得
	 * @param user ログイン情報
	 * @return グループに加入してほしい申請リスト
	 */
	public List<DesireUserInGroupResponce> getDesireGroupList(UserDetailsImp user) {
		return desireGroupApi.getDesireUser(user);
	}
}
