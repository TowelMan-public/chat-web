package com.example.demo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.configurer.UrlConfig;

/**
 * ユーザー情報に関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL)
public class UserControl {
	
	/**
	 * /loginページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping("login")
	public String showLoginPage(Model model) {
		return "/login";
	}
}
