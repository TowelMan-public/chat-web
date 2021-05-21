package com.example.demo.control.advice;

import java.text.ParseException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.AlreadyInsertedGroupDesireException;
import com.example.demo.client.exception.AlreadyInsertedGroupException;
import com.example.demo.client.exception.AlreadyUsedUserIdNameException;
import com.example.demo.client.exception.BadRequestFormException;
import com.example.demo.client.exception.InvalidLoginException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotHaveUserException;
import com.example.demo.client.exception.NotInsertedGroupDesireException;
import com.example.demo.client.exception.NotJoinGroupException;
import com.example.demo.configurer.UrlConfig;

/**
 * エラーページを表示しないといけないようなエラー
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	
	private static final String ERROR_PAGE_LINK = "/error";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String LINK_URL = "linkUrl";
	private static final String LINK_MESSAGE = "linkMessage";
	
	/**
	 * 既に友達申請が出ている
	 * @param e
	 * @return
	 */
	@ExceptionHandler({AlreadyHaveUserException.class})
	public ModelAndView handleAlreadyHaveUserException(AlreadyHaveUserException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"既に友達登録しています。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	//既にグループに入ってほしい申請が出ている
	@ExceptionHandler({AlreadyInsertedGroupDesireException.class})
	public ModelAndView handleAlreadyInsertedGroupDesireException(AlreadyInsertedGroupDesireException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"既にグループに入ってほしい申請が出されてます。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	//既にグループに加入している
	@ExceptionHandler({AlreadyInsertedGroupException.class})
	public ModelAndView handleAlreadyInsertedGroupException(AlreadyInsertedGroupException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"既にグループに加入されてます。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * 既にユーザーID名が使われてる
	 * @param e
	 * @return
	 */
	@ExceptionHandler({AlreadyUsedUserIdNameException.class})
	public ModelAndView handleAlreadyUsedUserIdNameException(AlreadyUsedUserIdNameException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"あなたが指定されたユーザー名は既に使われています");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * 友達登録がされてない
	 * @param e
	 * @return
	 */
	@ExceptionHandler({NotHaveUserException.class})
	public ModelAndView handleNotHaveUserException(NotHaveUserException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"そのユーザーをまだ友達登録をしていません。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * グループに加入してほしい申請が出てない
	 * @param e
	 * @return
	 */
	@ExceptionHandler({NotInsertedGroupDesireException.class})
	public ModelAndView handleNotInsertedGroupDesireException(NotInsertedGroupDesireException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"グループに入ってほしい申請がまだ出ていません。グループ加入者と接触がある場合はその方とお話ししてみてはいかがでしょうか？");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * グループに加入してない
	 * @param e
	 * @return
	 */
	@ExceptionHandler({NotJoinGroupException.class})
	public ModelAndView handleNotJoinGroupException(NotJoinGroupException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"まだグループに加入していません。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * 不適切な指定
	 * @param e
	 * @return
	 */
	@ExceptionHandler({BadRequestFormException.class})
	public ModelAndView handleBadRequestFormException(BadRequestFormException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"リクエスト等に指定した値のいずれかが不適切です。（組み合わせが不適切なこともあります。）");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * URLに不備も　各種IDが不正
	 * @param e
	 * @return
	 */
	@ExceptionHandler({NotFoundException.class})
	public ModelAndView handleNotFoundException(NotFoundException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"URL等に指定されたIDのうち、いずれかが不適切なものです。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * URLに問題も  日付
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ParseException.class})
	public ModelAndView handleParseException(ParseException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"URL等に指定された日付が不正です。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * ログイン状態なし　再ログイン求む
	 * @param e
	 * @return
	 */
	@ExceptionHandler({InvalidLoginException.class})
	public ModelAndView handleInvalidLoginException(InvalidLoginException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"お手数をおかけしますが、もう一度ログインページにてログインしてください。");
		modelAndView.addObject(LINK_URL, UrlConfig.ROOT_URL + "/logout");
		modelAndView.addObject(LINK_MESSAGE,"ログインページに行きます");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * 予期できないAPI呼び出しエラー
	 * @param e
	 * @return
	 */
	@ExceptionHandler({RestClientException.class})
	public ModelAndView handleRestClientResponseException(RestClientException  e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"サーバ側で、予期せぬことが発生いたしました。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
	
	/**
	 * API呼び出し時のIOエラー 予期できないもの扱い
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ResourceAccessException.class})
	public ModelAndView handleParseException(ResourceAccessException e) {
		ModelAndView modelAndView = new ModelAndView();
		
		//セットして返却
		modelAndView.addObject(ERROR_MESSAGE,"Webサーバ側で、予期せぬことが発生いたしました。");
		modelAndView.setViewName(ERROR_PAGE_LINK);
		return modelAndView;
	}
}