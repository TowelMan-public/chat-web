package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * グループ加入者リストを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class UserInGroupResponse {
	private Integer talkRoomId;
	private Integer lastTalkIndex;
	private String userIdName;
	private String userName;
}