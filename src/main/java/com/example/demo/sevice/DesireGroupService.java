package com.example.demo.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.api.DesireGroupApi;
import com.example.demo.client.api.GroupApi;
import com.example.demo.client.api.entity.DesireUserInGroupResponce;
import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.form.GroupModel;
import com.example.demo.form.inner.TalkModel;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.utility.CommonUtility;

/**
 * グループに加入してほしい申請に関するビジネスロジック
 */
@Service
public class DesireGroupService {
	@Autowired
	DesireGroupApi desireGroupApi;
	@Autowired
	GroupApi groupApi;
	@Autowired
	CommonUtility commonUtility;

	/**
	 * グループに加入してほしい申請リストの取得
	 * @param user ログイン情報
	 * @return グループに加入してほしい申請リスト
	 */
	public List<DesireUserInGroupResponce> getDesireGroupList(UserDetailsImp user) {
		return desireGroupApi.getDesireUserList(user);
	}

	/**
	 * グループに加入してほしい申請が出されている人向けトークリストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 */
	public GroupModel getDesireGroupTalks(UserDetailsImp user, Integer groupTalkRoomId, Integer maxSize) {
		//データの取得
		DesireUserInGroupResponce desireGroupResponce = desireGroupApi.getDesireUser(user, groupTalkRoomId);
		Integer startIndex = desireGroupResponce.getLastTalkIndex() - maxSize/2;
		List<TalkResponse> talkResponseList = groupApi.getGroupTalks(user, groupTalkRoomId, startIndex, maxSize);
		List<TalkModel> talkModelList = new ArrayList<>();
		Integer finalIndex = desireGroupApi.getDesireUser(user, groupTalkRoomId)
										   .getLastTalkIndex();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var groupModel = new GroupModel();
		groupModel.setGroupName(desireGroupResponce.getGroupName());
		groupModel.setGroupTalkRoomId(desireGroupResponce.getTalkRoomId());
		groupModel.setTalkList(talkModelList);
		groupModel.setFirstIndex(startIndex);
		groupModel.setFinalIndex(finalIndex);
		
		return groupModel;
	}

	/**
	 * startIndex以降のグループに加入してほしい申請が出されている人向けトークリストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param startIndex どこからトークを取得するかのインデックス
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 */
	public GroupModel getNextDesireDialogueTalks(UserDetailsImp user, Integer groupTalkRoomId, Integer startIndex, Integer maxSize) {
		//データの取得
		DesireUserInGroupResponce desireGroupResponce = desireGroupApi.getDesireUser(user, groupTalkRoomId);
		List<TalkResponse> talkResponseList = groupApi.getGroupTalks(user, groupTalkRoomId, startIndex, maxSize);
		List<TalkModel> talkModelList = new ArrayList<>();
		Integer finalIndex = desireGroupApi.getDesireUser(user, groupTalkRoomId)
				   .getLastTalkIndex();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var groupModel = new GroupModel();
		groupModel.setGroupName(desireGroupResponce.getGroupName());
		groupModel.setGroupTalkRoomId(desireGroupResponce.getTalkRoomId());
		groupModel.setTalkList(talkModelList);
		groupModel.setFinalIndex(
				commonUtility.min(
						startIndex + maxSize - 1, finalIndex));
		
		return groupModel;
	}

	/**
	 * lastTalkIndex以前のグループに加入してほしい申請が出されている人向けトークリストの取得
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param lastTalkIndex このインデックスが最後になるように取得する
	 * @param maxSize 最大件数
	 * @return グループトークリスト
	 */
	public GroupModel getBackDesireDialogueTalks(UserDetailsImp user, Integer groupTalkRoomId, Integer lastTalkIndex, Integer maxSize) {
		//データの取得
		DesireUserInGroupResponce desireGroupResponce = desireGroupApi.getDesireUser(user, groupTalkRoomId);
		Integer startIndex = lastTalkIndex - maxSize + 1;
		List<TalkResponse> talkResponseList = groupApi.getGroupTalks(user, groupTalkRoomId, startIndex, maxSize);
		List<TalkModel> talkModelList = new ArrayList<>();
		
		//処理
		for(TalkResponse talkResponse : talkResponseList)
			talkModelList.add( new TalkModel(talkResponse, user.getUsername() ));
		var groupModel = new GroupModel();
		groupModel.setGroupName(desireGroupResponce.getGroupName());
		groupModel.setGroupTalkRoomId(desireGroupResponce.getTalkRoomId());
		groupModel.setTalkList(talkModelList);
		groupModel.setFirstIndex(startIndex);
		
		return groupModel;
	}

	/**
	 * グループに入ってほしい申請を受ける
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 */
	public void joinDesireGroup(UserDetailsImp user, Integer groupTalkRoomId) {
		desireGroupApi.joinGroup(user, groupTalkRoomId);
	}

	/**
	 * グループに入ってほしい申請を断る
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 */
	public void deleteDesireGroup(UserDetailsImp user, Integer groupTalkRoomId) {
		desireGroupApi.deleteDesireGroup(user, groupTalkRoomId);
	}
}
