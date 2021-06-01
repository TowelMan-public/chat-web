package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.GroupApi;
import com.example.demo.client.api.GroupTalkApi;
import com.example.demo.client.api.entity.GroupTalkRoomResponse;
import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.form.GroupModel;
import com.example.demo.form.inner.GroupListModel;
import com.example.demo.form.inner.TalkModel;
import com.example.demo.form.update.UpdateTalkForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.utility.CommonUtility;

/**
 * グループに関するビジネスロジック
 */
@Service
public class GroupService {

	@Autowired
	GroupApi groupApi;
	@Autowired
	GroupTalkApi groupTalkApi;
	@Autowired
	CommonUtility commonUtility;
	
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

	/**
	 * startIndex以降のグループトークリストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param startIndex どこからトークを取得するかのインデックス
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 */
	public GroupModel getNextDialogueTalks(UserDetailsImp user, Integer groupTalkRoomId, Integer startIndex, Integer maxSize) {
		//データの取得
		GroupTalkRoomResponse groupRalkRoomResponce = groupApi.getGroup(user, groupTalkRoomId);
		List<TalkResponse> talkResponseList = groupApi.getGroupTalks(user, groupTalkRoomId, startIndex, maxSize);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var groupModel = new GroupModel();
		groupModel.setGroupName(groupRalkRoomResponce.getGroupName());
		groupModel.setGroupTalkRoomId(groupRalkRoomResponce.getTalkRoomId());
		groupModel.setTalkList(talkModelList);
		groupModel.setFinalIndex(
				commonUtility.min(
						startIndex + maxSize - 1, groupRalkRoomResponce.getGroupLastTalkIndex()));
		
		return groupModel;
	}

	/**
	 * lastTalkIndex以前のグループトークリストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param lastTalkIndex このインデックスが最後になるように取得する
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 */
	public GroupModel getBackDialogueTalks(UserDetailsImp user, Integer groupTalkRoomId, Integer lastTalkIndex, Integer maxSize) {
		//データの取得
		GroupTalkRoomResponse groupRalkRoomResponce = groupApi.getGroup(user, groupTalkRoomId);
		Integer startIndex = lastTalkIndex - maxSize + 1;
		List<TalkResponse> talkResponseList = groupApi.getGroupTalks(user, groupTalkRoomId, startIndex, maxSize);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var groupModel = new GroupModel();
		groupModel.setGroupName(groupRalkRoomResponce.getGroupName());
		groupModel.setGroupTalkRoomId(groupRalkRoomResponce.getTalkRoomId());
		groupModel.setTalkList(talkModelList);
		groupModel.setFirstIndex(startIndex);
		
		return groupModel;
	}

	/**
	 * グループトークの作成
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkContent トーク内容
	 */
	public void insertTalk(UserDetailsImp user, Integer groupTalkRoomId, String talkContent) {
		groupTalkApi.insertTalk(user, groupTalkRoomId, talkContent);
	}

	/**
	 * グループトークリストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 */
	public GroupModel getGroupTalks(UserDetailsImp user, Integer groupTalkRoomId, Integer maxSize) {
		//データの取得
		GroupTalkRoomResponse groupRalkRoomResponce = groupApi.getGroup(user, groupTalkRoomId);
		Integer startIndex = groupRalkRoomResponce.getUserLastTalkIndex() - maxSize/2;
		List<TalkResponse> talkResponseList = groupApi.getGroupTalks(user, groupTalkRoomId, startIndex, maxSize);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var groupModel = new GroupModel();
		groupModel.setGroupName(groupRalkRoomResponce.getGroupName());
		groupModel.setGroupTalkRoomId(groupRalkRoomResponce.getTalkRoomId());
		groupModel.setTalkList(talkModelList);
		groupModel.setFirstIndex(startIndex);
		groupModel.setFinalIndex(
				commonUtility.min(
						startIndex + maxSize - 1, groupRalkRoomResponce.getGroupLastTalkIndex()));
		
		return groupModel;
	}

	/**
	 * 友達トークの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkIndex グループトークインデックス
	 * @return 友達トーク
	 */
	public UpdateTalkForm getTalk(UserDetailsImp user, Integer groupTalkRoomId, Integer talkIndex) {
		var talkResponse = groupTalkApi.getTalk(user, groupTalkRoomId, talkIndex);
		
		return new UpdateTalkForm(talkResponse.getContent());
	}

	/**
	 * 友達トークの更新
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkIndex グループトークインデックス
	 * @param talkContent グループトークの内容
	 */
	public void updateTalk(UserDetailsImp user, Integer groupTalkRoomId, Integer talkIndex, String talkContent) {
		groupTalkApi.updateTalk(user, groupTalkRoomId, talkIndex, talkContent);
	}

	/**
	 * 友達トークの削除
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkIndex グループトークインデックス
	 */
	public void deleteTalk(UserDetailsImp user, Integer groupTalkRoomId, Integer talkIndex) {
		groupTalkApi.deleteTalk(user, groupTalkRoomId, talkIndex);
	}
	
}