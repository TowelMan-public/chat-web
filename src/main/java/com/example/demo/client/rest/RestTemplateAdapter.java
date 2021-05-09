package com.example.demo.client.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.security.UserDetailsImp;

@Component
public class RestTemplateAdapter {
	
	RestTemplateExceptionHandler restTemplateExceptionHandler;
	
	private final RestTemplate restTemplate;
	
	//RestTemplateの設定
	public RestTemplateAdapter() {
		restTemplateExceptionHandler = new RestTemplateExceptionHandler();
		
		restTemplate = new RestTemplateBuilder()
							.errorHandler(restTemplateExceptionHandler)
							.build();
	}
	
	public <R,T> T postForObjectWhenLogined(String url, R requestBody, Class<T> responseBodyClass,UserDetailsImp user) {
		//リクエスト作成
		RequestEntity<R> requestEntity = 
		        RequestEntity
		          .post(url)
		          //.contentType(MediaType.APPLICATION_JSON)
		          .header("X-AUTH-TOKEN",user.getTokenForServer())
		          .body(requestBody);
		
		//実行
		ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, responseBodyClass);
		return responseEntity.getBody();
	}
	
	public <R,T> T getForObjectWhenLogined(String url, R requestBody, Class<T> responseBodyClass,UserDetailsImp user) {
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
			url += "?" + bld.toString();
		}
		
		//リクエスト作成
		RequestEntity<Void> requestEntity = 
		        RequestEntity
		          .get(url)
		          .header("X-AUTH-TOKEN",user.getTokenForServer())
		          .build();
		
		//実行
		ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, responseBodyClass);
		return responseEntity.getBody();
	}
	
	public <R,T> List<T> getForObjectsWhenLogined(String url, R requestBody, Class<T> oneOfResponseBodyClass,UserDetailsImp user) {
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
			url += "?" + bld.toString();
		}
		
		//リクエスト作成
		RequestEntity<Void> requestEntity = 
		        RequestEntity
		          .get(url)
		          .header("X-AUTH-TOKEN",user.getTokenForServer())
		          .build();
		
		//実行
		ResponseEntity<List<T>> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<T>>() {});
		return responseEntity.getBody();
	}
	
	public <R ,T> ResponseEntity<T> postForObject(String url,R requestBody, Class<T> responseBodyClass) {		
		//リクエスト作成
		RequestEntity<R> requestEntity = 
		        RequestEntity
		          .post(url)
		          //.contentType(MediaType.MULTIPART_FORM_DATA)
		          .body(requestBody);
		
		//実行
		return restTemplate.exchange(requestEntity, responseBodyClass);
	}
	
	public <R,T>  ResponseEntity<T> gettForObject(String url, R requestBody, Class<T> responseBodyClass) {
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
			url += "?" + bld.toString();
		}
		
		//リクエスト作成
		RequestEntity<Void> requestEntity = 
		        RequestEntity
		          .get(url)
		          .build();
		
		//実行
		return restTemplate.exchange(requestEntity, responseBodyClass);
	}
}