package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DialogueService;
import com.example.demo.sevice.UserService;

/**
 * 友達の詳細ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/dialogue")
public class DialogueUserControl {

	@Autowired
	UserService userService;
	@Autowired
	DialogueService dialogueService;
	
	/**
	 * 友達の詳細ページを表示する
	 * @param user ログイン情報
	 * @param model HTMLページにオブジェクトをぶち込むためのもの
	 * @param haveUserIdName 友達のユーザーID名
	 * @return 表示するべきHTMLファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping("{haveUserIdName}")
	public String showDialogueUserPage(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName, Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_DIALOGUE_USER)
				
					.setContentModel(
							userService.getUser(user, haveUserIdName))
					
					.buildAndReturnUrl();
	}
	
	/**
	 * 友達を削除する
	 * @param user ログイン情報
	 * @param model HTMLページにオブジェクトをぶち込むためのもの
	 * @param haveUserIdName 友達のユーザーID名
	 * @return 表示するべきHTMLファイルのパスか、リダイレクト等のURL
	 */
	@PostMapping("exit/{haveUserIdName}")
	public String deleteHaveUser(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("haveUserIdName") String haveUserIdName, Model model){
		//処理
		dialogueService.deleteHaveUser(user, haveUserIdName);
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
}
