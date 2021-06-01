package com.example.demo.form.inner;

import com.example.demo.client.api.entity.GroupTalkRoomResponse;

import lombok.Data;

/**
 * グループリスト(html向け)
 */
@Data
public class GroupListModel {
	private Integer groupTalkRoomId;
	private String groupName;
	private Integer noticeCount;
	private Integer myLastTalkIndex;
	
	/**
	 * コンストラクタ
	 * @param responce グループリスト
	 */
	public GroupListModel(GroupTalkRoomResponse responce) {
		this.groupTalkRoomId = responce.getTalkRoomId();
		this.groupName = responce.getGroupName();
		this.myLastTalkIndex = responce.getUserLastTalkIndex();
		this.noticeCount = responce.getGroupLastTalkIndex() - responce.getUserLastTalkIndex();
	}
}
