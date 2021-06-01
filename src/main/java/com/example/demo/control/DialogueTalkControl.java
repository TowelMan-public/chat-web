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
import com.example.demo.form.DialogueTalkModel;
import com.example.demo.form.update.UpdateTalkForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DialogueService;

/**
 * 友達トーク詳細ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/dialogue/talk")
public class DialogueTalkControl {
	
	@Autowired
	DialogueService dialogueService;
	
	/**
	 * 友達トーク詳細ページを表示する
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 * @param talkIndex 友達トークのインデックス
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@GetMapping("{haveUserIdName}/{talkIndex}")
	public String showDialogueTalk(@AuthenticationPrincipal UserDetailsImp user, Model model,
			@PathVariable("haveUserIdName") String haveUserIdName, @PathVariable("talkIndex") Integer talkIndex) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_DIALOGUE_TALK)
				
					.setContentModel(
							new DialogueTalkModel(haveUserIdName, talkIndex))
					
					.setUpdateTalkForm(
							dialogueService.getTalk(user, haveUserIdName, talkIndex))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * 友達トークの変更
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 * @param talkIndex 友達トークのインデックス
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("update/{haveUserIdName}/{talkIndex}")
	public String updateDialogueTalk(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("haveUserIdName") String haveUserIdName, @PathVariable("talkIndex") Integer talkIndex,
			@Validated UpdateTalkForm form, BindingResult result, RedirectAttributes redirect) {
		final String REDIRECT_DIALOGUE_TALK_URL = UrlConfig.REDIRECT_ROOT_URL +
				"/dialogue/talk/{haveUserIdName}/{talkIndex}"
					.replace("{haveUserIdName}", haveUserIdName)
					.replace("{talkIndex}", talkIndex.toString());
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdateIdName", result);
			redirect.addFlashAttribute("UpdateIdName", form);
			return REDIRECT_DIALOGUE_TALK_URL;
		}
		
		//処理
		dialogueService.updateTalk(user, haveUserIdName, talkIndex, form.getTalkContent());
		
		//リダイレクト
		return REDIRECT_DIALOGUE_TALK_URL;
	}
	
	/**
	 * 友達トークの削除
	 * @param user ログイン情報
	 * @param haveUserIdName 友達のユーザーID名
	 * @param talkIndex 友達トークのインデックス
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("delete/{haveUserIdName}/{talkIndex}")
	public String deleteDialogueTalk(@AuthenticationPrincipal UserDetailsImp user, 
			@PathVariable("haveUserIdName") String haveUserIdName, @PathVariable("talkIndex") Integer talkIndex) {		
		//処理
		dialogueService.deleteTalk(user, haveUserIdName, talkIndex);
		
		//リダイレクト		
		return UrlConfig.REDIRECT_ROOT_URL + "/dialogue/{haveUserIdName}".replace("{haveUserIdName}", haveUserIdName);
	}
}
