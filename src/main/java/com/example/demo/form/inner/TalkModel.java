package com.example.demo.form.inner;

import com.example.demo.client.api.entity.TalkResponse;

import lombok.Data;

/**
 * トーク(HTML向け)
 */
@Data
public class TalkModel {
	private boolean isMyTalk;
	private String senderUserName;
	private String talkContent;
	private String dateString;
	private Integer talkIndex;
	
	/**
	 * コンストラクタ
	 * @param response トークリスト
	 * @param myUserIdName ログイン者のユーザーID名
	 */
	public TalkModel(TalkResponse response, String myUserIdName) {
		this.senderUserName = response.getUserName();
		this.talkContent = response.getContent();
		this.talkIndex = response.getTalkIndex();
		this.isMyTalk = response.getUserIdName().equals(myUserIdName);
		this.dateString = response.getTimestampString();
	}
}
