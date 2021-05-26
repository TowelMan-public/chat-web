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
import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.form.insert.InsertUserForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.DialogueService;

/**
 * 友達追加ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/insert/dialogue")
public class InsertDialogue {
	
	@Autowired
	DialogueService dialogueService;
	
	/**
	 * 友達追加ページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping
	public String showCreateGroupPage(Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_CREATE_GROUP)
				
					.setInsertUser(new InsertUserForm())
					
					.buildAndReturnUrl();
	}
	
	/**
	 * 友達を追加する
	 * @param user ログイン情報
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping
	public String insertDialogue(@AuthenticationPrincipal UserDetailsImp user, 
			@Validated InsertUserForm form, BindingResult result, RedirectAttributes redirect) {
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
			redirect.addFlashAttribute("InsertUser", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/insert/dialogue";
		}
		
		//処理
		try {
			dialogueService.insertDialogue(user, form.getUserIdName());
		}
		catch(AlreadyHaveUserException e) {
			var error = new FieldError(result.getObjectName(), "userIdName", "あなたが指定したユーザーは既に友達に登録されてます。");
			result.addError(error);
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
			redirect.addFlashAttribute("InsertUser", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/insert/dialogue";
		}
		catch(NotFoundException e) {
			if(e.isErrorFieldUserIdName()) {
				var error = new FieldError(result.getObjectName(), "userIdName", "あなたが指定したユーザーID名は不正です。");
				result.addError(error);
				redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
				redirect.addFlashAttribute("InsertUser", form);
				return "redirect:" + UrlConfig.ROOT_URL + "/insert/dialogue";
			}else {
				throw e;
			}
		}
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
}
