package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * グループ加入申請リストを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class DesireUserInGroupResponce {
	private Integer talkRoomId;
	private Integer lastTalkIndex;
	private String groupName;
}
