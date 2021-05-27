package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ModelSetter;
import com.example.demo.client.exception.AlreadyInsertedGroupDesireException;
import com.example.demo.client.exception.AlreadyInsertedGroupException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.configurer.UrlConfig;
import com.example.demo.form.GroupDetailsContentModel;
import com.example.demo.form.insert.InsertUserForm;
import com.example.demo.form.update.UpdateNameForm;
import com.example.demo.security.UserDetailsImp;
import com.example.demo.sevice.GroupService;
import com.example.demo.sevice.UserInGroupService;

/**
 * グループの詳細ページに関するURLのアクセスポイントクラス
 */
@Controller
@RequestMapping(UrlConfig.ROOT_URL + "/group")
public class GroupDeiailsControl {
	@Autowired
	GroupService groupService;
	@Autowired
	UserInGroupService userInGroupService;
	
	/**
	 * グループの詳細ページを表示する
	 * @param user ログイン情報
	 * @param model HTMLページにオブジェクトをぶち込むためのもの
	 * @param haveUserIdName 友達のユーザーID名
	 * @return 表示するべきHTMLファイルのパスか、リダイレクト等のURL
	 */
	@GetMapping("{groupTalkRoomId}")
	public String showGroupDeiailsPage(@AuthenticationPrincipal UserDetailsImp user, @PathVariable("groupTalkRoomId") Integer groupTalkRoomId, Model model) {
		//ページ作成
		return new ModelSetter(model, ModelSetter.PAGE_GROUP_DETAILS)
				
					.setUpdateName(
							new UpdateNameForm(
									groupService.getGroupName(user, groupTalkRoomId)))
					
					.setInsertUser(
							new InsertUserForm())
					
					.setContentModel(
							new GroupDetailsContentModel(groupTalkRoomId,
									userInGroupService.getUserInGroupList(user, groupTalkRoomId)))
					
					.buildAndReturnUrl();
	 }
	
	/**
	 * グループ名の変更
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("update/name/{groupTalkRoomId}")
	public String updateGroupName(@AuthenticationPrincipal UserDetailsImp user,
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, @Validated UpdateNameForm form, BindingResult result, RedirectAttributes redirect){
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.UpdateName", result);
			redirect.addFlashAttribute("UpdateName", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
		}
		
		//処理
		groupService.updateGroupName(user, groupTalkRoomId, form.getName());
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
	}
	
	/**
	 * グループの削除
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("delete/{groupTalkRoomId}")
	public String deleteGroup(@AuthenticationPrincipal UserDetailsImp user,	@PathVariable("groupTalkRoomId") Integer groupTalkRoomId){		
		//処理
		groupService.deleteGroup(user, groupTalkRoomId);
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
	
	/**
	 * グループにユーザーを勧誘をする
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param form リクエストパラメータ
	 * @param result バリデーションチェックの結果
	 * @param redirect リダイレクト時に反映させるModel
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("user/insert/{groupTalkRoomId}")
	public String insertUserInGroup(@AuthenticationPrincipal UserDetailsImp user,
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, @Validated InsertUserForm form, BindingResult result, RedirectAttributes redirect){
		
		//入力ﾁｪｯｸ
		if(result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
			redirect.addFlashAttribute("InsertUser", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
		}
		
		//処理
		try {
			userInGroupService.insertUserInGroup(user, groupTalkRoomId, form.getUserIdName());
		}
		catch(AlreadyInsertedGroupException e) {
			var error = new FieldError(result.getObjectName(), "userIdName", "あなたが指定したユーザーは既にグループに加入してます");
			result.addError(error);
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
			redirect.addFlashAttribute("InsertUser", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
		}
		catch(AlreadyInsertedGroupDesireException e) {
			var error = new FieldError(result.getObjectName(), "userIdName", "あなたが指定したユーザーは今現在勧誘中です。");
			result.addError(error);
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
			redirect.addFlashAttribute("InsertUser", form);
			return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
		}
		catch(NotFoundException e) {
			if(e.isErrorFieldUserIdName()) {
				var error = new FieldError(result.getObjectName(), "userIdName", "あなたが指定したユーザーID名は不正です。");
				result.addError(error);
				redirect.addFlashAttribute("org.springframework.validation.BindingResult.InsertUser", result);
				redirect.addFlashAttribute("InsertUser", form);
				return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
			}else {
				throw e;
			}
		}
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/group/{groupTalkRoomId}".replace("{groupTalkRoomId}", groupTalkRoomId.toString());
	}
	
	/**
	 * グループの加入者を脱退させる
	 * @param user ログイン情報
	 * @param groupTalkRoomId グループトークルームID
	 * @param userIdName グループ加入者のユーザーID名
	 * @return 表示するべきページのURLか、リダイレクト等のURL
	 */
	@PostMapping("user/delete/{groupTalkRoomId}/{userIdName}")
	public String deleteUserInGroup(@AuthenticationPrincipal UserDetailsImp user,	
			@PathVariable("groupTalkRoomId") Integer groupTalkRoomId, @PathVariable("userIdName") String userIdName){		
		//処理
		userInGroupService.deleteUserInGroup(user, groupTalkRoomId, userIdName);
		
		//リダイレクト
		return "redirect:" + UrlConfig.ROOT_URL + "/home";
	}
}