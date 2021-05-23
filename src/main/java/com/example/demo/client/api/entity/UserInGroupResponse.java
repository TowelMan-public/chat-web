package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * 友達追加申請リストを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class UserInGroupResponse {
	private String haveUserIdName;
	private String haveUserName;
	private Integer talkRoomId;
	private Integer lastTalkIndex;
}
