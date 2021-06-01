package com.example.demo.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.ModelSetter;
import com.example.demo.client.api.entity.DesireHaveUserResponse;
import com.example.demo.client.api.entity.DesireUserInGroupResponce;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.form.SeeSideListModel;
import com.example.demo.form.inner.GroupListModel;
import com.example.demo.form.inner.HaveUserModel;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DesireDialogueService;
import com.example.demo.sevice.DesireGroupService;
import com.example.demo.sevice.DialogueService;
import com.example.demo.sevice.GroupService;

/**
 * ホームページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.AJAX_ROOT_URL + "/see/side")
public class SeeSideControl {
	@Autowired
	DialogueService dialogueService;
	@Autowired
	GroupService groupService;
	@Autowired
	DesireDialogueService desireDialogueService;
	@Autowired
	DesireGroupService desireGroupService;
	
	@GetMapping
	public String showGroupPage(@AuthenticationPrincipal UserDetailsImp user, Model model) {
		//Serviceで取得
		List<HaveUserModel> haveUserList = dialogueService.getHaveUserList(user);
		List<GroupListModel> groupList = groupService.getGroupList(user);
		List<DesireHaveUserResponse> desireHaveUserList = desireDialogueService.getDesireHaveUserList(user);
		List<DesireUserInGroupResponce> desireGroupList = desireGroupService.getDesireGroupList(user);
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_SEE_SIDE)
				
					.setSeeSideListModel(
							new SeeSideListModel(haveUserList, groupList, desireHaveUserList, desireGroupList))
					
					.buildAndReturnUrl();
	}
}
