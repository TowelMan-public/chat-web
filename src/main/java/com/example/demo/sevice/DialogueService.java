package com.example.demo.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.UserInDialogueApi;
import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.security.UserDetailsImp;

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
}