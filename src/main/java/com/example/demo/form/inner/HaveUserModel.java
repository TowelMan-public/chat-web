package com.example.demo.form.inner;

import com.example.demo.client.api.entity.HaveUserResponse;

import lombok.Data;

/**
 * 友達リスト(html向け)
 */
@Data
public class HaveUserModel {
	private String userIdName;
	private String userName;
	private Integer noticeCount;
	private Integer myLastTalkIndex;
	
	/**
	 * コンストラクタ
	 * @param responce 友達リスト
	 */
	public HaveUserModel(HaveUserResponse responce) {
		this.userIdName = responce.getHaveUserIdName();
		this.userName = responce.getHaveUserName();
		this.myLastTalkIndex = responce.getMyLastTalkIndex();
		this.noticeCount = responce.getTalkLastTalkIndex() - responce.getMyLastTalkIndex();
	}
}
