package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserInDialogueApi;
import com.example.demo.client.api.entity.HaveUserResponse;
import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.form.inner.HaveUserModel;
import com.example.demo.security.UserDetailsImp;

/**
 * 友達に関するビジネスロジック
 */
@Service
public class DialogueService {

	@Autowired
	UserInDialogueApi userInDialogueApi;
	
	/**
	 * 友達を追加する
	 * @param user ログイン情報
	 * @param haveUserIdName 追加したいユーザーのID名
	 * @throws NotFoundException
	 * @throws AlreadyHaveUserException
	 */
	public void insertDialogue(UserDetailsImp user, String haveUserIdName) 
			throws NotFoundException, AlreadyHaveUserException{
		userInDialogueApi.insertUserInDiarogue(user, haveUserIdName);
	}

	
	/**
	 * 友達リスト（html向け）を取得する
	 * @param user ログイン情報
	 * @return 友達リスト（html向け）
	 */
	public List<HaveUserModel> getHaveUserList(UserDetailsImp user) {
		//データの取得・宣言
		List<HaveUserResponse> responceList = userInDialogueApi.getUserInDiarogueList(user);
		List<HaveUserModel> modelList = new ArrayList<>();
		
		
		
		//処理
		for(HaveUserResponse response : responceList)
			modelList.add(new HaveUserModel(response));
		
		return modelList;
	}

	public void deleteHaveUser(UserDetailsImp user, String haveUserIdName) {
		userInDialogueApi.deleteUserInDiarogue(user, haveUserIdName);
	}
}