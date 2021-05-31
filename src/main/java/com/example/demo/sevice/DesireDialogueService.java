package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.DesireUserApi;
import com.example.demo.client.api.DialogueApi;
import com.example.demo.client.api.DialogueTalkApi;
import com.example.demo.client.api.entity.DesireHaveUserResponse;
import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.form.DialogueModel;
import com.example.demo.form.inner.TalkModel;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.utility.CommonUtility;

/**
 * 友達追加申請に関するビジネスロジック
 */
@Service
public class DesireDialogueService {
	@Autowired
	DesireUserApi desireUserApi;
	@Autowired
	DialogueApi dialogueApi;
	@Autowired
	DialogueTalkApi dialogueTalkApi;
	@Autowired
	CommonUtility commonUtility;
	
	/**
	 * 友達追加申請者リストの取得
	 * @param user ログイン情報
	 * @return 友達追加申請者リスト
	 */
	public List<DesireHaveUserResponse> getDesireHaveUserList(UserDetailsImp user) {
		return desireUserApi.getDesireUserList(user);
	}

	/**
	 * 友達申請トークリストを取得する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 * @param maxSize 最大件数
	 * @return 友達トークリスト
	 */
	public DialogueModel getDesireDialogueTalks(UserDetailsImp user, String haveUserIdName, Integer maxSize) {
		//データの取得
		DesireHaveUserResponse desireHaveUserResponce = desireUserApi.getDesireUser(user, haveUserIdName);
		Integer myStartIndex = desireHaveUserResponce.getLastTalkIndex();
		String haveUserName = desireHaveUserResponce.getHaveUserName();
		List<TalkResponse> talkResponseList = dialogueApi.getDiarogueTalks(user, haveUserIdName, maxSize, myStartIndex - (maxSize / 2));
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var dialogueModel = new DialogueModel();
		dialogueModel.setHaveUserIdName(haveUserIdName);
		dialogueModel.setHaveUserName(haveUserName);
		dialogueModel.setTalkList(talkModelList);
		dialogueModel.setFirstIndex(myStartIndex - (maxSize / 2));
		dialogueModel.setFinalIndex( 
				desireUserApi.getDesireUser(user, haveUserIdName)
							 .getLastTalkIndex());
		
		return dialogueModel;
	}

	/**
	 * 友達申請トークリストをstartIndexに指定されたインデクス以降から取得する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達申請を出しているユーザーのID名
	 * @param startIndex 読み込んだ一番最初のトークインデックス
	 * @param maxSize 最大件数
	 * @return 友達トークリスト
	 */
	public DialogueModel getNextDialogueTalks(UserDetailsImp user, String haveUserIdName, Integer startIndex, Integer maxSize) {
		//データの取得
		DesireHaveUserResponse desireHaveUserResponce = desireUserApi.getDesireUser(user, haveUserIdName);
		String haveUserName = desireHaveUserResponce.getHaveUserName();
		List<TalkResponse> talkResponseList = dialogueApi.getDiarogueTalks(user, haveUserIdName, maxSize, startIndex);
		List<TalkModel> talkModelList = new ArrayList<>();
		Integer mylastIndex = desireUserApi.getDesireUser(user, haveUserIdName)
										   .getLastTalkIndex();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var dialogueModel = new DialogueModel();
		dialogueModel.setHaveUserIdName(haveUserIdName);
		dialogueModel.setHaveUserName(haveUserName);
		dialogueModel.setTalkList(talkModelList);
		dialogueModel.setFinalIndex( 
				commonUtility.min(
						startIndex + maxSize - 1, mylastIndex));
		
		return dialogueModel;
	}

	/**
	 * 友達申請トークリストをfinishIndex以前から取得する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達申請を出しているユーザーのID名
	 * @param finishIndex 読み込んだ最後のトークインデックス
	 * @param maxSize 最大件数
	 * @return 友達トークリスト
	 */
	public DialogueModel getBackDialogueTalks(UserDetailsImp user, String haveUserIdName, Integer finishIndex, Integer maxSize) {
		//データの取得
		DesireHaveUserResponse desireHaveUserResponce = desireUserApi.getDesireUser(user, haveUserIdName);
		String haveUserName = desireHaveUserResponce.getHaveUserName();
		Integer startIndex = finishIndex - maxSize + 1;
		List<TalkResponse> talkResponseList = dialogueApi.getDiarogueTalks(user, haveUserIdName, maxSize, startIndex);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var dialogueModel = new DialogueModel();
		dialogueModel.setHaveUserIdName(haveUserIdName);
		dialogueModel.setHaveUserName(haveUserName);
		dialogueModel.setTalkList(talkModelList);
		dialogueModel.setFirstIndex(startIndex);
		
		return dialogueModel;
	}

	/**
	 * 友達追加申請を受ける
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ID名
	 */
	public void joinDesireHaveUser(UserDetailsImp user, String haveUserIdName) {
		desireUserApi.joinUser(user, haveUserIdName);
	}

	/**
	 * 友達追加申請を断る
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ID名
	 */
	public void deleteDesireHaveUser(UserDetailsImp user, String haveUserIdName) {
		desireUserApi.deleteDesireUser(user, haveUserIdName);
	}
}
