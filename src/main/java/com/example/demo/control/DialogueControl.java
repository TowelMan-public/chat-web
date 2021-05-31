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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.exception.ValidationException;
import com.example.demo.form.insert.InsertTalkForm;
import com.example.demo.form.select.LoadTalkForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DialogueService;

/**
 * 友達トークページに関するURLのアクセスポイントクラス
 */
@Controller
public class DialogueControl {
	
	@Autowired
	DialogueService dialogueService;
	
	/**
	 * 友達トークページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @param haveUserIdName 友達ID名
	 * @param user ログイン情報
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping(UrlConfig.ROOT_URL + "/dialogue/{haveUserIdName}")
	public String showDialoguePage(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("haveUserIdName") String haveUserIdName, Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_DIALOGUE)
				
					.setContentModel(
							dialogueService.getDialogueTalks(user, haveUserIdName, 50))
					
					.setInsertTalkForm(new InsertTalkForm())
					
					.buildAndReturnUrl();
	}
	
	/**
	 * トークの作成
	 * @param user ログイン情報
	 * @param form リクエストパラメータ
	 * @param haveUserIdName 友達ID名
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping(UrlConfig.ROOT_URL + "/dialogue/talk/insert/{haveUserIdName}")
	public String insertTalk(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName,
			@Validated InsertTalkForm form, BindingResult result, RedirectAttributes redirect) {
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.CreateGInsertTalkFormroup", result);
			redirect.addFlashAttribute("InsertTalkForm", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/dialogue/{haveUserIdName}".replace("{haveUserIdName}", haveUserIdName);
		}
		
		//処理
		dialogueService.insertTalk(user, haveUserIdName, form.getTalkContent());
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/dialogue/{haveUserIdName}".replace("{haveUserIdName}", haveUserIdName);
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
	@GetMapping("/load/dialogue/{haveUserIdName}/next")
	public String loadNextDialogueTalkList(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName,
			@Validated LoadTalkForm form, BindingResult result, Model model) {
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			throw new ValidationException();
		}
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_DIALOGUE)
				
					.setContentModel(
							dialogueService.getNextDialogueTalks(user, haveUserIdName, form.getStartIndex(), 25))
					
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
	@GetMapping("/load/dialogue/{haveUserIdName}/back")
	public String loadBackDialogueTalkList(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName,
			@Validated LoadTalkForm form, BindingResult result, Model model) {
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			throw new ValidationException();
		}
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.FRAGMENT_DIALOGUE)
				
					.setContentModel(
							dialogueService.getBackDialogueTalks(user, haveUserIdName, form.getStartIndex(), 25))
					
					.buildAndReturnUrl();
	}
}
