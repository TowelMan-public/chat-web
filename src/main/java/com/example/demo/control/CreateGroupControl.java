package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ModelSetter;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.form.insert.CreateGroupForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.GroupService;

/**
 * グループ作成ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/create/group")
public class CreateGroupControl {
	
	@Autowired
	GroupService groupService;
	
	/**
	 * グループ作成ページを表示する
	 * @param model htmlページにオブジェクトをぶち込むためのもの
	 * @return 表示するべきhtmlファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping
	public String showCreateGroupPage(Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_CREATE_GROUP)
				
					.setCreateGroup(new CreateGroupForm())
					
					.buildAndReturnUrl();
	}
	
	/**
	 * 新しくグループを作る
	 * @param user ログイン情報
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@GetMapping
	public String createGroup(@AuthenticationPrincipal UserDetailsImp user, 
			@Validated CreateGroupForm form, BindingResult result, RedirectAttributes redirect) {
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.CreateGroup", result);
			redirect.addFlashAttribute("CreateGroup", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/create/group";
		}
		
		//処理
		groupService.createGroup(user, form.getGroupName());
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
}
