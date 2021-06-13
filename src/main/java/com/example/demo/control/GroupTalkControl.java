package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.form.GroupTalkModel;
import com.example.demo.form.update.UpdateTalkForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.GroupService;

/**
 * グループトーク詳細ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/group/talk")
public class GroupTalkControl {
	@Autowired
	GroupService groupService;
	
	/**
	 * グループトーク詳細ページを表示する
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkIndex グループトークのインデックス
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@GetMapping("{groupTalkRoomId}/{talkIndex}")
	public String showGroupTalk(@AuthenticationPrincipal UserDetailsImp user, Model model,
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, @PathVariable("talkIndex") Integer talkIndex) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_GROUP_TALK)
				
					.setContentModel(
							new GroupTalkModel(groupTalkRoomId, talkIndex))
					
					.setUpdateTalkForm(
							groupService.getTalk(user, groupTalkRoomId, talkIndex))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * グループトークの変更
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkIndex グループトークのインデックス
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("update/{groupTalkRoomId}/{talkIndex}")
	public String updateGroupTalk(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, @PathVariable("talkIndex") Integer talkIndex,
			@Validated UpdateTalkForm form, BindingResult result, RedirectAttributes redirect) {
		final String REDIRECT_GROUP_TALK_PAGE = UrlConfig.REDIRECT_ROOT_URL + 
				"/group/talk/{groupTalkRoomId}/{talkIndex}"
					.replace("{groupTalkRoomId}", groupTalkRoomId.toString())
					.replace("{talkIndex}", talkIndex.toString());
		
		final String REDIRECT_GROUP_PAGE = UrlConfig.REDIRECT_ROOT_URL + 
				"/group/{groupTalkRoomId}"
					.replace("{groupTalkRoomId}", groupTalkRoomId.toString());
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdateIdName", result);
			redirect.addFlashAttribute("UpdateIdName", form);
			return REDIRECT_GROUP_TALK_PAGE;
		}
		
		//処理
		groupService.updateTalk(user, groupTalkRoomId, talkIndex, form.getTalkContent());
		
		//リダイレクト
		return REDIRECT_GROUP_PAGE;
	}
	
	/**
	 * グループトークの削除
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param talkIndex グループトークのインデックス
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("delete/{groupTalkRoomId}/{talkIndex}")
	public String deleteGroupTalk(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, @PathVariable("talkIndex") Integer talkIndex) {
		//処理
		groupService.deleteTalk(user, groupTalkRoomId, talkIndex);
		
		//リダイレクト		
		return UrlConfig.REDIRECT_ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
	}
}
