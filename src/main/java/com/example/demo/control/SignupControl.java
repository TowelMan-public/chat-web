package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ModelSetter;
import com.example.demo.client.exception.AlreadyUsedUserIdNameException;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.form.insert.SignupForm;
import com.example.demo.sevice.UserService;

/**
 * 新規ユーザー登録ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/signup")
public class SignupControl {
	
	@Autowired
	UserService userService;
	
	/**
	 * 新規ユーザー登録ページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping
	public String showSignupPage(Model model) {
		return new ModelSetter(model, ModelSetter.PAGE_SIGNUP)
				
					.setSignupForm(new SignupForm())
					
					.buildAndReturnUrl();
	}
	
	/**
	 * 新規ユーザー登録
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping
	public String insertUser(@Validated SignupForm form, BindingResult result, RedirectAttributes redirect) {
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.SignupForm", result);
			redirect.addFlashAttribute("SignupForm", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/signup";
		}
		
		//処理
		try {
			userService.insertUser(form);
		}
		catch(AlreadyUsedUserIdNameException e) {
			var error = new FieldError(result.getObjectName(), "userIdName", "そのID名は既に使われています。ほかのID名を指定してください。");
			result.addError(error);
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.SignupForm", result);
			redirect.addFlashAttribute("SignupForm", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/signup";
		}
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
}
