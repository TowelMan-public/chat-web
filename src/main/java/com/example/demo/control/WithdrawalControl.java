package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.UserService;

/**
 * ユーザー脱退ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/withdrawal")
public class WithdrawalControl {
	
	@Autowired
	UserService userService;
	
	/**
	 * ユーザー脱退ページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping
	public String showWithdrawalPage(Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_WHITHDRAWAL)
				
					.buildAndReturnUrl();
	}
	
	/**
	 * ユーザーの脱退
	 * @param user ログイン情報
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping
	public String updateUserIdName(@AuthenticationPrincipal UserDetailsImp user) {
		userService.deleteUser(user);
		return UrlConfig.REDIRECT_ROOT_URL + "/logout";
	}
}
