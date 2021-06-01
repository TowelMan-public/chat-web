package com.example.demo;

import org.springframework.ui.Model;

import com.example.demo.form.SeeSideListModel;
import com.example.demo.form.insert.CreateGroupForm;
import com.example.demo.form.insert.InsertTalkForm;
import com.example.demo.form.insert.InsertUserForm;
import com.example.demo.form.insert.SignupForm;
import com.example.demo.form.update.UpdateIdNameForm;
import com.example.demo.form.update.UpdateNameForm;
import com.example.demo.form.update.UpdatePasswordForm;
import com.example.demo.form.update.UpdateTalkForm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ControlerクラスのModelにオブジェクトをセットすることとページの指定を支援するクラス。<br>
 * メソッドチェーンで扱う。
 */
public class ModelSetter {
	//セットするもの母体
	private final Model model;
	//このModelを渡すべきURL
	private final String url;
	
	/**
	 * ホームページ。
	 */
	public static final WebSeeContentType PAGE_HOME = new WebSeeContentType (null,WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * ユーザー自身の設定等を行うページ
	 */
	public static final WebSeeContentType PAGE_USER = new WebSeeContentType ("user",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * 新しくグループを作るページ
	 */
	public static final WebSeeContentType PAGE_CREATE_GROUP = new WebSeeContentType ("create_group",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * 新しい友達を追加するページ
	 */
	public static final WebSeeContentType PAGE_INSERT_DIALOGUE = new WebSeeContentType ("insert_dialogue",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * グループの詳細を見たりするページ
	 */
	public static final WebSeeContentType PAGE_GROUP_DETAILS = new WebSeeContentType ("group_details",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * グループトークの編集・削除をするページ
	 */
	public static final WebSeeContentType PAGE_GROUP_TALK = new WebSeeContentType ("group_talk_get",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * 友達トークの編集・削除をするページ
	 */
	public static final WebSeeContentType PAGE_DIALOGUE_TALK = new WebSeeContentType ("dialogue_talk_get",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * 友達の詳細を見るページ
	 */
	public static final WebSeeContentType PAGE_DIALOGUE_USER = new WebSeeContentType ("dialogue_user",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * 友達トークリストを見たり友達トークを送ったりするページ
	 */
	public static final WebSeeContentType PAGE_DIALOGUE = new WebSeeContentType ("dialogue",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * 友達申請してる相手のトークを見るページ
	 */
	public static final WebSeeContentType PAGE_DESIRE_DIALOGE = new WebSeeContentType ("desire_dialogue",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * グループトークリストを見たり送ったりするページ
	 */
	public static final WebSeeContentType PAGE_GROUP = new WebSeeContentType ("group",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * グループに加入してほしい申請が出されてるグループのグループトークを見る
	 */
	public static final WebSeeContentType PAGE_DESIRE_GROUP = new WebSeeContentType ("desire_group",WebSeeContentType.EXAMPLE_URL);
	
	/**
	 * ログインページ
	 */
	public static final WebSeeContentType PAGE_LOGIN = new WebSeeContentType (null,"login");
	
	/**
	 * 新規ユーザー作成ページ
	 */
	public static final WebSeeContentType PAGE_SIGNUP = new WebSeeContentType (null,"signup");
	
	/**
	 * 退会ページ
	 */
	public static final WebSeeContentType PAGE_WHITHDRAWAL = new WebSeeContentType (null,"withdrawal");
	
	/**
	 * 友達トークリストのフラグメント
	 */
	public static final WebSeeContentType FRAGMENT_DIALOGUE = new WebSeeContentType (null,
			"talkRoom/dialogue_talk_list_fragment::dialogue_talk_list_fragment");
	
	/**
	 * グループトークリストのフラグメント
	 */
	public static final WebSeeContentType FRAGMENT_GROUP = new WebSeeContentType (null,
			"talkRoom/group_talk_list_fragment::group_talk_list_fragment");
	
	/**
	 * 友達申請してる相手のトークリストのフラグメント
	 */
	public static final WebSeeContentType FRAGMENT_DESIRE_DIALOGUE = new WebSeeContentType (null,
			"talkRoom/desire_dialogue_talk_list_fragment::desire_dialogue_talk_list_fragment");
	
	/**
	 * グループに加入してほしい申請が出されてるグループのトークリストのフラグメント
	 */
	public static final WebSeeContentType FRAGMENT_DESIRE_GROUP = new WebSeeContentType (null,
			"talkRoom/desire_group_talk_list_fragment::desire_group_talk_list_fragment");
	
	/**
	 * サイドの部分のフラグメント
	 */
	public static final WebSeeContentType FRAGMENT_SEE_SIDE = new WebSeeContentType (null,
			"see_side_fragment::see_side_fragment");
	
	/**
	 * コンストラクタ<br>
	 * メソッドチェーンの始まり
	 * @param pageModel Controlerクラスのメソッドの引数に指定されてるModelクラス
	 * @param webSeeContentType このクラスに定数で指定されているページを表すもの
	 */
	public ModelSetter(Model pageModel,WebSeeContentType webSeeContentType) {
		model = pageModel; 
		model.addAttribute("WebSeeContentType", webSeeContentType.getWebSeeContentTypeName());
		
		url=webSeeContentType.getPageUrl();
	}
	
	/**
	 * 送信用URLを返す。<br>
	 * メソッドチェーンの終わり。
	 * @return
	 */
	public String buildAndReturnUrl() {
		return url;
	}
	
	/**
	 * Modelクラウスに、キー名に指定されたところにオブジェクトをセットする。<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param keyName キー名
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setOther(String keyName,Object object){
		if(!model.containsAttribute(keyName))
			model.addAttribute(keyName, object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：ContentModel<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setContentModel(Object object){
		setOther("ContentModel", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：InsertTalkForm<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setInsertTalkForm(InsertTalkForm object){
		setOther("InsertTalkForm", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：UpdateTalkForm<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setUpdateTalkForm(UpdateTalkForm object){
		setOther("UpdateTalkForm", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：UpdateName<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setUpdateName(UpdateNameForm object){
		setOther("UpdateName", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：InsertUser<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setInsertUser(InsertUserForm object){
		setOther("InsertUser", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：UpdateIdName<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param InsertUser セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setUpdateIdName(UpdateIdNameForm object){
		setOther("UpdateIdName", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：UpdatePassword<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setUpdatePassword(UpdatePasswordForm object){
		setOther("UpdatePassword", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：CreateGroup<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setCreateGroup(CreateGroupForm object){
		setOther("CreateGroup", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：SignupForm<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setSignupForm(SignupForm object){
		setOther("SignupForm", object);
		return this;
	}
	
	/**
	 * Modelクラウスにオブジェクトをセットする。<br>
	 * キー名：SeeSideListModel<br>
	 * 既に同じキー名にセットされている場合はセットしない。
	 * @param object セットしたいオブジェクト
	 * @return 自分
	 */
	public ModelSetter setSeeSideListModel(SeeSideListModel object){
		setOther("SeeSideListModel", object);
		return this;
	}
	
	/**
	 * ページを表すクラス。定数にしたもののみ外部から参照され、後はModelSetter内でのみ使われる
	 * 
	 * @see ModelSetter
	 */
	@Getter
	@AllArgsConstructor
	private static class WebSeeContentType{
		public static final String EXAMPLE_URL = "/example";
		private String webSeeContentTypeName;
		private String pageUrl;
	}
}
