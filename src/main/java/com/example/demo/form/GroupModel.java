package com.example.demo.form;

import java.util.List;

import com.example.demo.form.inner.TalkModel;

import lombok.Data;

/**
 * グループトークページに表示する内容をまとめたクラス
 */
@Data
public class GroupModel {
	private Integer groupTalkRoomId;
	private String groupName;
	private Integer firstIndex;
	private Integer finalIndex;
	private List<TalkModel> talkList;
}
