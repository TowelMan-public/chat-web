package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * グループ加入者リストを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class UserInGroupResponse {
	private String haveUserIdName;
	private String haveUserName;
	private Integer talkRoomId;
	private Integer lastTalkIndex;
}