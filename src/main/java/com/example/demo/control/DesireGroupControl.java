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

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.exception.ValidationException;
import com.example.demo.form.select.LoadTalkForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DesireGroupService;

/**
 * グループに加入してほしい申請が出されている人向けトークページに関するURLのアクセスポイントクラス
 */
@Controller
public class DesireGroupControl {
	@Autowired
	DesireGroupService desireGroupService;
	
	/**
	 * グループに加入してほしい申請が出されている人向けトークページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @param groupTalkRoomId グループトークルームID
	 * @param user ログイン情報
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping(UrlConfig.ROOT_URL + "/desire/group/{groupTalkRoomId}")
	public String showGroupPage(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, Model model) {
		return new ModelSetter(model, ModelSetter.PAGE_DESIRE_GROUP)
				
					.setContentModel(
							desireGroupService.getDesireGroupTalks(user, groupTalkRoomId, 50))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * ajaxで以前のトークリストを取得する
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return HTMLのフラグメントのURL
	 */
	@GetMapping(UrlConfig.AJAX_ROOT_URL + "/desire/group/{groupTalkRoomId}/next")
	public String loadNextDialogueTalkList(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("groupTalkRoomId") Integer groupTalkRoomId,
			@Validated LoadTalkForm form, BindingResult result, Model model) {
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			throw new ValidationException();
		}
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_DESIRE_GROUP)
				
					.setContentModel(
							desireGroupService.getNextDesireDialogueTalks(user, groupTalkRoomId, form.getStartIndex(), 25))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * ajaxで以降ののトークリストを取得する
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return HTMLのフラグメントのURL
	 */
	@GetMapping(UrlConfig.AJAX_ROOT_URL + "/desire/group/{groupTalkRoomId}/back")
	public String loadBackDialogueTalkList(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("groupTalkRoomId") Integer groupTalkRoomId,
			@Validated LoadTalkForm form, BindingResult result, Model model) {
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			throw new ValidationException();
		}
				
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_DESIRE_GROUP)
				
					.setContentModel(
							desireGroupService.getBackDesireDialogueTalks(user, groupTalkRoomId, form.getStartIndex(), 25))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * グループに加入してほしい申請を受ける
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@PostMapping("/web/desire/group/join/{groupTalkRoomId}")
	public String joinDesireGroup(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("groupTalkRoomId") Integer groupTalkRoomId) {
		desireGroupService.joinDesireGroup(user, groupTalkRoomId);
		
		return UrlConfig.REDIRECT_ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
	}
	
	/**
	 * グループに加入してほしい申請を断る
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@PostMapping("/web/desire/group/delete/{groupTalkRoomId}")
	public String deleteDesireGroup(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("groupTalkRoomId") Integer groupTalkRoomId) {
		desireGroupService.deleteDesireGroup(user, groupTalkRoomId);
		
		return UrlConfig.REDIRECT_ROOT_URL + "/home";
	}
}
