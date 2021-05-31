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
import com.example.demo.sevice.DesireDialogueService;

/**
 * 友達申請が出されている人のトークページに関するURLのアクセスポイントクラス
 */
@Controller
public class DesireDialogueControl {
	
	@Autowired
	DesireDialogueService desireDialogueService;
	
	/**
	 * 友達申請トークページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @param user ログイン情報
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping(UrlConfig.ROOT_URL + "/desire/dialogue/{haveUserIdName}")
	public String showDesireDialoguePage(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("haveUserIdName") String haveUserIdName, Model model) {
		return new ModelSetter(model, ModelSetter.PAGE_DESIRE_DIALOGE)
				
					.setContentModel(
							desireDialogueService.getDesireDialogueTalks(user, haveUserIdName, 50))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * ajaxで以前のトークリストを取得する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ID名
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return HTMLのフラグメントのURL
	 */
	@GetMapping("/load/desire/dialogue/{haveUserIdName}/next")
	public String loadNextDesireDialogueTalkList(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName,
			@Validated LoadTalkForm form, BindingResult result, Model model) {
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			throw new ValidationException();
		}
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_DESIRE_DIALOGUE)
				
				.setContentModel(
						desireDialogueService.getNextDialogueTalks(user, haveUserIdName, form.getStartIndex(), 25))
				
				.buildAndReturnUrl();
	}
	
	/**
	 * ajaxで以降ののトークリストを取得する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ID名
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return HTMLのフラグメントのURL
	 */
	@GetMapping("/load/desire/dialogue/{haveUserIdName}/back")
	public String loadBackDesireDialogueTalkList(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName,
			@Validated LoadTalkForm form, BindingResult result, Model model) {
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			throw new ValidationException();
		}
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_DESIRE_DIALOGUE)
				
				.setContentModel(
						desireDialogueService.getBackDialogueTalks(user, haveUserIdName, form.getStartIndex(), 25))
				
				.buildAndReturnUrl();
	}
	
	/**
	 * 友達追加申請を受ける
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ID名
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@PostMapping("/web/desire/dialogue/join/{haveUserIdName}")
	public String joinDesireHaveUser(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName) {
		desireDialogueService.joinDesireHaveUser(user, haveUserIdName);
		
		return "redirect:" + UrlConfig.ROOT_URL + "/dialogue/{haveUserIdName}".replace("{haveUserIdName}", haveUserIdName);
	}
	
	/**
	 * 友達追加申請を断る
	 * @param user ログイン情報
	 * @param haveUserIdName 友達ID名
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@PostMapping("/web/desire/dialogue/delete/{haveUserIdName}")
	public String deleteDesireHaveUser(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName) {
		desireDialogueService.deleteDesireHaveUser(user, haveUserIdName);
		
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
}
