package com.example.demo.client.api.entity;

import lombok.Data;

@Data
public class GroupTalkRoomEntity {
	private Integer talkRoomId;
	private String groupName;
	private Integer lastTalkIndex;
	private Boolean isEnabled;
}
