package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.DialogueApi;
import com.example.demo.client.api.DialogueTalkApi;
import com.example.demo.client.api.UserInDialogueApi;
import com.example.demo.client.api.entity.HaveUserResponse;
import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.form.DialogueModel;
import com.example.demo.form.inner.HaveUserModel;
import com.example.demo.form.inner.TalkModel;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.utility.CommonUtility;

/**
 * 友達に関するビジネスロジック
 */
@Service
public class DialogueService {

	@Autowired
	UserInDialogueApi userInDialogueApi;
	@Autowired
	DialogueApi dialogueApi;
	@Autowired
	DialogueTalkApi dialogueTalkApi;
	@Autowired
	CommonUtility commonUtility;
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

	/**
	 * 友達の削除
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 */
	public void deleteHaveUser(UserDetailsImp user, String haveUserIdName) {
		userInDialogueApi.deleteUserInDiarogue(user, haveUserIdName);
	}

	/**
	 * 友達トークリストを取得する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 * @param maxSize 最大件数
	 * @return 友達トークリスト
	 */
	public DialogueModel getDialogueTalks(UserDetailsImp user, String haveUserIdName, Integer maxSize) {
		//データの取得
		HaveUserResponse haveUserResponce = userInDialogueApi.getUserInDiarogue(user, haveUserIdName);
		Integer myStartIndex = haveUserResponce.getMyLastTalkIndex();
		List<TalkResponse> talkResponseList = dialogueApi.getDiarogueTalks(user, haveUserIdName, maxSize, myStartIndex - (maxSize / 2));
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var dialogueModel = new DialogueModel();
		dialogueModel.setHaveUserIdName(haveUserIdName);
		dialogueModel.setHaveUserName(haveUserResponce.getHaveUserName());
		dialogueModel.setTalkList(talkModelList);
		dialogueModel.setFirstIndex(myStartIndex - (maxSize / 2));
		dialogueModel.setFinalIndex( 
				commonUtility.min(
						myStartIndex + (maxSize / 2), haveUserResponce.getTalkLastTalkIndex()));
		
		return dialogueModel;
	}

	/**
	 * 友達トークの作成
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 * @param talkContent トーク内容
	 */
	public void insertTalk(UserDetailsImp user, String haveUserIdName, String talkContent) {
		dialogueTalkApi.insertTalk(user, haveUserIdName, talkContent);
	}

	/**
	 * 友達トークリストをstartIndexに指定されたインデクス以降から取得する
	 * @param user
	 * @param haveUserIdName
	 * @param startIndex
	 * @param maxSize
	 * @return 友達トークリスト
	 */
	public DialogueModel getNextDialogueTalks(UserDetailsImp user, String haveUserIdName, Integer startIndex, Integer maxSize) {
		//データの取得
		HaveUserResponse haveUserResponce = userInDialogueApi.getUserInDiarogue(user, haveUserIdName);
		List<TalkResponse> talkResponseList = dialogueApi.getDiarogueTalks(user, haveUserIdName, maxSize, startIndex);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var dialogueModel = new DialogueModel();
		dialogueModel.setHaveUserIdName(haveUserIdName);
		dialogueModel.setHaveUserName(haveUserResponce.getHaveUserName());
		dialogueModel.setTalkList(talkModelList);
		dialogueModel.setFinalIndex( 
				commonUtility.min(
						startIndex + maxSize - 1, haveUserResponce.getTalkLastTalkIndex()));
		
		return dialogueModel;
	}

	/**
	 * 友達トークリストをfinishIndex以前から取得する
	 * @param user
	 * @param haveUserIdName
	 * @param finishIndex
	 * @param maxSize
	 * @return 友達トークリスト
	 */
	public DialogueModel getBackDialogueTalks(UserDetailsImp user, String haveUserIdName, Integer finishIndex, Integer maxSize) {
		//データの取得
		HaveUserResponse haveUserResponce = userInDialogueApi.getUserInDiarogue(user, haveUserIdName);
		Integer startIndex = finishIndex - maxSize + 1;
		List<TalkResponse> talkResponseList = dialogueApi.getDiarogueTalks(user, haveUserIdName, maxSize, startIndex);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var dialogueModel = new DialogueModel();
		dialogueModel.setHaveUserIdName(haveUserIdName);
		dialogueModel.setHaveUserName(haveUserResponce.getHaveUserName());
		dialogueModel.setTalkList(talkModelList);
		dialogueModel.setFirstIndex(startIndex);
		
		return dialogueModel;
	}
}