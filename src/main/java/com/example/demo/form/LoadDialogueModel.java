package com.example.demo.form;

import java.util.List;

import com.example.demo.form.inner.TalkModel;

import lombok.Data;

@Data
public class LoadDialogueModel {
	private Integer firstIndex;
	private Integer finalIndex;
	private String haveUserIdName;
	private List<TalkModel> talkList;
}
