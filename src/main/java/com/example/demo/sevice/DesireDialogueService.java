package com.example.demo.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.DesireUserApi;
import com.example.demo.client.api.entity.DesireHaveUserResponse;
import com.example.demo.security.UserDetailsImp;

@Service
public class DesireDialogueService {
	@Autowired
	DesireUserApi desireUserApi;

	/**
	 * 友達追加申請者リストの取得
	 * @param user ログイン情報
	 * @return 友達追加申請者リスト
	 */
	public List<DesireHaveUserResponse> getDesireHaveUserList(UserDetailsImp user) {
		return desireUserApi.getDesireUser(user);
	}
}
