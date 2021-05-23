package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * グループを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class GroupTalkRoomResponse {
	private Integer talkRoomId;
	private String groupName;
	private Integer groupLastTalkIndex;
	private Integer userLastTalkIndex;
}
