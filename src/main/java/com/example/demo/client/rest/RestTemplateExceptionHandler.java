package com.example.demo.client.rest;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import com.example.demo.client.exception.AlreadyHaveUserException;
import com.example.demo.client.exception.AlreadyInsertedGroupDesireException;
import com.example.demo.client.exception.AlreadyInsertedGroupException;
import com.example.demo.client.exception.AlreadyUsedUserIdNameException;
import com.example.demo.client.exception.BadRequestFormException;
import com.example.demo.client.exception.InvalidLoginException;
import com.example.demo.client.exception.LoginException;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotHaveUserException;
import com.example.demo.client.exception.NotInsertedGroupDesireException;
import com.example.demo.client.exception.NotJoinGroupException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

/**
 * RestTemplateAdapterのエラーハンドリング<br>
 * ResponseErrorHandlerを実装
 * 
 * @see com.example.demo.client.rest.RestTemplateAdapter
 * @see org.springframework.web.client.ResponseErrorHandler
 */
public class RestTemplateExceptionHandler implements  ResponseErrorHandler{
	
	private ObjectMapper objectMapper;
	
	/**
	 * コンストラクタ
	 */
	public RestTemplateExceptionHandler(){
		objectMapper = new ObjectMapper();
	}
	
	/**
	 * エラーハンドリングするところ
	 */
	@Override
	public void handleError(ClientHttpResponse error) throws IOException {
		var errorResponse = getErrorResponse(error);
		
		//各種具体的にわかる例外を投げる・分別ができないときは他のやつ投げる
		switch(errorResponse.getErrorCode()) {
		case "NotFoundException":
			throw new NotFoundException(errorResponse.getMessage());
		case "LoginException":
			throw new LoginException(errorResponse.getMessage());
		case "AlreadyHaveUserException":
			throw new AlreadyHaveUserException();
		case "AlreadyInsertedGroupDesireException":
			throw new AlreadyInsertedGroupDesireException();
		case "AlreadyInsertedGroupException":
			throw new AlreadyInsertedGroupException();
		case "AlreadyUsedUserIdNameException":
			throw new AlreadyUsedUserIdNameException();
		case "NotHaveUserException":
			throw new NotHaveUserException();
		case "NotInsertedGroupDesireException":
			throw new NotInsertedGroupDesireException();
		case "NotJoinGroupException":
			throw new NotJoinGroupException();
		case "BadRequestFormException":
			throw new BadRequestFormException(errorResponse.getMessage());
		default:
			
			if(HttpStatus.valueOf(error.getRawStatusCode()).equals(HttpStatus.UNAUTHORIZED)) {
				throw new InvalidLoginException(errorResponse.getMessage());
			}else {
				throw new RestClientResponseException(errorResponse.getMessage(), error.getStatusCode().value(),
														errorResponse.getErrorCode(), error.getHeaders(), error.getBody().readAllBytes(), null);
			}
		}
	}
	
	/**
	 * エラーがあるかを判断する所
	 */
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
				response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
	}
	
	/**
	 * エラーレスポンスボディーを解析する
	 */
	private ErrorResponse getErrorResponse(ClientHttpResponse e) throws IOException {
		var bodyString = new String( e.getBody().readAllBytes());
		return objectMapper.readValue(bodyString, ErrorResponse.class);
	}
	
	/**
	 * エラーレスポンスボディー
	 */
	@Data
	static class ErrorResponse {
		private String errorCode;
	    private String message;
	}
}
