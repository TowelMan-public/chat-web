package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.example.demo.form.update.UpdateIdNameForm;
import com.example.demo.form.update.UpdateNameForm;
import com.example.demo.form.update.UpdatePasswordForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.UserService;

/**
 * ユーザーページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/user")
public class UserControl {
	
	@Autowired
	UserService userService;
	
	/**
	 * ユーザーページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @param user ログイン情報
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping
	public String showUserPage(@AuthenticationPrincipal UserDetailsImp user, Model model) {
		
		//ユーザー情報の取得
		var userEntity = userService.getUser(user, user.getUsername());
		
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_USER)
				
					.setUpdateName(new UpdateNameForm(userEntity.getUserName()))
					
					.setUpdateIdName(new UpdateIdNameForm(userEntity.getUserIdName()))
					
					.setUpdatePassword(new UpdatePasswordForm())
					
					.buildAndReturnUrl();
	}
	
	/**
	 * ユーザーID名の変更
	 * @param user ログイン情報
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("update/id")
	public String updateUserIdName(@AuthenticationPrincipal UserDetailsImp user, 
			@Validated UpdateIdNameForm form, BindingResult result, RedirectAttributes redirect) {
		final String REDIRECT_USER_PAGE = UrlConfig.REDIRECT_ROOT_URL + "/user";
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdateIdName", result);
			redirect.addFlashAttribute("UpdateIdName", form);
			return REDIRECT_USER_PAGE;
		}
		
		//処理
		try {
			userService.updateUserIdName(user, form.getIdName());
		}
		catch(AlreadyUsedUserIdNameException e) {
			var error = new FieldError(result.getObjectName(), "idName", "そのID名は既に使われています。ほかのID名を指定してください。");
			result.addError(error);
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdateIdName", result);
			redirect.addFlashAttribute("UpdateIdName", form);
			return REDIRECT_USER_PAGE;
		}
		
		//リダイレクト
		return REDIRECT_USER_PAGE;
	}
	
	/**
	 * ユーザー名の変更
	 * @param user ログイン情報
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("update/name")
	public String updateUserName(@AuthenticationPrincipal UserDetailsImp user, 
			@Validated UpdateNameForm form, BindingResult result, RedirectAttributes redirect) {
		final String REDIRECT_USER_PAGE = UrlConfig.REDIRECT_ROOT_URL + "/user";
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdateName", result);
			redirect.addFlashAttribute("UpdateName", form);
			return REDIRECT_USER_PAGE;
		}
		
		//処理
		userService.updateUserName(user, form.getName());
		
		//リダイレクト
		return REDIRECT_USER_PAGE;
	}
	
	/**
	 * パスワードの変更
	 * @param user ログイン情報
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("update/password")
	public String updatePassword(@AuthenticationPrincipal UserDetailsImp user, 
			@Validated UpdatePasswordForm form, BindingResult result, RedirectAttributes redirect) {
		
		final String REDIRECT_USER_PAGE = UrlConfig.REDIRECT_ROOT_URL + "/user";
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdatePassword", result);
			redirect.addFlashAttribute("UpdatePassword", form);
			return REDIRECT_USER_PAGE;
		}
		
		//処理
		userService.updatePassword(user, form.getPassword());
		
		//リダイレクト
		return REDIRECT_USER_PAGE;
	}
}
