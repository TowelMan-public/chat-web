package com.example.demo.form;

import java.util.List;

import com.example.demo.client.api.entity.UserInGroupResponse;

import lombok.Data;

/**
 * グループ詳細ページに表示する内容をまとめたクラス
 */
@Data
public class GroupDetailsContentModel {
	private Integer groupTalkRoomId;
	private List<UserInGroupResponse> userInGroupList;
	
	/**
	 * コンストラクタ
	 * @param groupTalkRoomId グループトークルームID
	 * @param userInGroupList グループ加入者リスト
	 */
	public GroupDetailsContentModel(Integer groupTalkRoomId, List<UserInGroupResponse> userInGroupList){
		this.groupTalkRoomId = groupTalkRoomId;
		this.userInGroupList = userInGroupList;
	}
}
