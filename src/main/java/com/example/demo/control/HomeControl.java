package com.example.demo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
/**
 * ホームページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/home")
public class HomeControl {
	
	/**
	 * ホームページ（内容がないページ）を表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping
	public String showHomePage(Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_HOME)
			
					.buildAndReturnUrl();
	}
	
}
