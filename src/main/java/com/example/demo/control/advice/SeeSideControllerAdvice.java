package com.example.demo.control.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.client.api.entity.DesireHaveUserResponse;
import com.example.demo.client.api.entity.DesireUserInGroupResponce;
import com.example.demo.form.SeeSideListModel;
import com.example.demo.form.inner.GroupListModel;
import com.example.demo.form.inner.HaveUserModel;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DesireDialogueService;
import com.example.demo.sevice.DesireGroupService;
import com.example.demo.sevice.DialogueService;
import com.example.demo.sevice.GroupService;

/**
 * example.htmlファイルを使って表示させるページのサイドの部分の共通処理
 */
@ControllerAdvice
public class SeeSideControllerAdvice {
	
	@Autowired
	DialogueService dialogueService;
	@Autowired
	GroupService groupService;
	@Autowired
	DesireDialogueService desireDialogueService;
	@Autowired
	DesireGroupService desireGroupService;
	
	/**
	 * example.htmlファイルを使って表示させるページのサイドの部分に必要なオブジェクトをセットする
	 * @param user ログイン情報
	 * @return 必要なオブジェクト
	 */
	@ModelAttribute("SeeSideListModel")
	public SeeSideListModel addSeeSideListModel(@AuthenticationPrincipal UserDetailsImp user) {
		//ログインしてない
		if(user == null)
			return null;
		
		//Serviceで取得
		List<HaveUserModel> haveUserList = dialogueService.getHaveUserList(user);
		List<GroupListModel> groupList = groupService.getGroupList(user);
		List<DesireHaveUserResponse> desireHaveUserList = desireDialogueService.getDesireHaveUserList(user);
		List<DesireUserInGroupResponce> desireGroupList = desireGroupService.getDesireGroupList(user);
		
		//SeeSideListModelを出力する
		return new SeeSideListModel(haveUserList, groupList, desireHaveUserList, desireGroupList);
	}
}
