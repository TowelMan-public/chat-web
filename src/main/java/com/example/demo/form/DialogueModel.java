package com.example.demo.form;

import java.util.List;

import com.example.demo.form.inner.TalkModel;

import lombok.Data;

/**
 * 友達トークページに表示する内容をまとめたクラス
 */
@Data
public class DialogueModel {
	private String haveUserName;
	private String haveUserIdName;
	private Integer firstIndex;
	private Integer finalIndex;
	private List<TalkModel> talkList;
}
