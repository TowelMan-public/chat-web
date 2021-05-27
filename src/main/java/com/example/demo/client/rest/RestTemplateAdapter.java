package com.example.demo.client.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.demo.security.UserDetailsImp;

/**
 * RestTemplateを便利にするための
 * 
 * @see org.springframework.web.client.RestTemplate
 */
@Component
public class RestTemplateAdapter {
	
	RestTemplateExceptionHandler restTemplateExceptionHandler;
	
	private final RestTemplate restTemplate;
	
	/**
	 * デフォルトコンストラクタ<br>
	 * RestTemplateの設定を行う<br>
	 * エラーハンドリングにRestTemplateExceptionHandlerをセットしている
	 * 
	 * @see org.springframework.web.client.RestTemplate
	 * @see com.example.demo.client.rest.RestTemplateExceptionHandler
	 */
	public RestTemplateAdapter() {
		restTemplateExceptionHandler = new RestTemplateExceptionHandler();
		
		restTemplate = new RestTemplateBuilder()
							.errorHandler(restTemplateExceptionHandler)
							.build();
	}
	
	/**
	 * 認証されているときに使うPOSTメソッド実行メソッド
	 * @param <R> リクエストボディーのクラス型
	 * @param <T> レスポンスボディーのクラス型
	 * @param url apiのURL
	 * @param requestBody リクエストボディー
	 * @param responseBodyClass レスポンスボディーのクラス型
	 * @param user ユーザー情報（認証用トークンを取得するため）
	 * @return <T>に指定されたレスポンスボディー
	 */
	public <R,T> T postForObjectWhenLogined(String url, R requestBody, Class<T> responseBodyClass,UserDetailsImp user) {
		//リクエスト作成
		RequestEntity<R> requestEntity = 
		        RequestEntity
		          .post(url)
		          .header("X-AUTH-TOKEN",user.getTokenForServer())
		          .body(requestBody);
		
		//実行
		ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, responseBodyClass);
		return responseEntity.getBody();
	}
	
	/**
	 * 認証されているときに使うGETメソッド実行メソッド
	 * @param <R> リクエストボディーのクラス型
	 * @param <T> レスポンスボディーのクラス型
	 * @param url apiのURL
	 * @param requestBody リクエストボディー
	 * @param responseBodyClass レスポンスボディーのクラス型
	 * @param user ユーザー情報（認証用トークンを取得するため）
	 * @return <T>に指定されたレスポンスボディー
	 */
	public <R,T> T getForObjectWhenLogined(String url, R requestBody, Class<T> responseBodyClass,UserDetailsImp user) {
		//リクエスト作成
		RequestEntity<Void> requestEntity = 
		        RequestEntity
		          .get(
		        	  createGetUrlWithQueryString(url,requestBody))
		          .header("X-AUTH-TOKEN",user.getTokenForServer())
		          .build();
		
		//実行
		ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, responseBodyClass);
		return responseEntity.getBody();
	}
	
	/**
	 * 認証されているときに使うGETメソッド実行メソッド<br>
	 * レスポンスボディーがList<?>の時用
	 * @param <R> リクエストボディーのクラス型
	 * @param <T> レスポンスボディーの配列のクラス型
	 * @param url apiのURL
	 * @param requestBody リクエストボディー
	 * @param responseBodyClass レスポンスボディーの配列のクラス型
	 * @param user ユーザー情報（認証用トークンを取得するため）
	 * @return <T>に指定されたレスポンスボディーのList
	 */
	public <R,T> List<T> getForListWhenLogined(String url, R requestBody, Class<T[]> oneOfresponseBodyClass,UserDetailsImp user) {
		//リクエスト作成
		RequestEntity<Void> requestEntity = 
		        RequestEntity
		          .get(
		        	  createGetUrlWithQueryString(url,requestBody))
		          .header("X-AUTH-TOKEN",user.getTokenForServer())
		          .build();
		
		//実行
		return Arrays.asList(
				restTemplate.exchange(requestEntity, oneOfresponseBodyClass).getBody());
	}
	
	/**
	 * 認証されていないときに使うPOSTメソッド実行メソッド
	 * @param <R> リクエストボディーのクラス型
	 * @param <T> レスポンスボディーのクラス型
	 * @param url apiのURL
	 * @param requestBody リクエストボディー
	 * @param responseBodyClass レスポンスボディーのクラス型
	 * @return <T>に指定されたレスポンスボディーが入ってるResponseEntity
	 */
	public <R ,T> ResponseEntity<T> postForObject(String url,R requestBody, Class<T> responseBodyClass) {		
		//リクエスト作成
		RequestEntity<R> requestEntity = 
		        RequestEntity
		          .post(url)
		          .body(requestBody);
		
		//実行
		return restTemplate.exchange(requestEntity, responseBodyClass);
	}
	
	/**
	 * 認証されていないときに使うGETメソッド実行メソッド
	 * @param <R> リクエストボディーのクラス型
	 * @param <T> レスポンスボディーのクラス型
	 * @param url apiのURL
	 * @param requestBody リクエストボディー
	 * @param responseBodyClass レスポンスボディーのクラス型
	 * @return <T>に指定されたレスポンスボディーが入ってるResponseEntity
	 */
	public <R,T>  ResponseEntity<T> getForObject(String url, R requestBody, Class<T> responseBodyClass) {
		//リクエスト作成
		RequestEntity<Void> requestEntity = 
		        RequestEntity
		          .get(
		        	  createGetUrlWithQueryString(url,requestBody))
		          .build();
		
		//実行
		return restTemplate.exchange(requestEntity, responseBodyClass);
	}
	
	private <R> String createGetUrlWithQueryString(String url, R requestBody) {
		//変換
		Map<String, String> requestBodyMap;
		try {
			requestBodyMap = BeanUtils.describe(requestBody);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			requestBodyMap = new HashMap<>();
		}
		
		//URLの作成
		StringBuilder bld = new StringBuilder("");
		for(Map.Entry<String, ?> entry : requestBodyMap.entrySet()) {
			if(entry.getValue() != null)
				bld.append(entry.getKey() + "=" + entry.getValue().toString() + "&");
		}
		
		if(requestBodyMap.size() != 0) {
			bld.setLength(bld.length()-1);
			return url + "?" + bld.toString();
		}else {
			return url;
		}
	}
}