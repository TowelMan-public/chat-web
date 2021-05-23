package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * 友達リストを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class HaveUserResponse {
	private String haveUserIdName;
	private String haveUserName;
	private Integer talkRoomId;
	private Integer talkLastTalkIndex;
	private Integer myLastTalkIndex;
}
