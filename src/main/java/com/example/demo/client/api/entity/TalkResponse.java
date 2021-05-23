package com.example.demo.client.api.entity;

import lombok.Data;

/**
 * トークを取得するAPIのレスポンスとして返すエンティティー
 */
@Data
public class TalkResponse {
	private Integer talkIndex;
	private String userIdName;
	private String content;
	private String timestampString;
}
