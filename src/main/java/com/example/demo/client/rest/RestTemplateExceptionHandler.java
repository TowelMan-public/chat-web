package com.example.demo.client.rest;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateExceptionHandler implements  ResponseErrorHandler{
	
	@Override
	public void handleError(ClientHttpResponse error) throws IOException {
		//ErrorResponse errorResponse = getErrorResponse(error);
		
		//各種具体的にわかる例外を投げる・分別ができないときは他のやつ投げる
		//TODO
		/*
		switch(errorResponse.getErrorCode()) {
		case "AlreadyUsedException":
			throw new AlreadyUsedException(errorResponse.getMessage());
		case "HaveNotAuthorityInSpaceException":
			throw new HaveNotAuthorityInSpaceException(errorResponse.getMessage());
		case "NotFoundException":
			throw new NotFoundException(errorResponse.getMessage());
		case "SpaceIsnotPublicException":
			throw new SpaceIsnotPublicException(errorResponse.getMessage());
		case "UserAleadyJoinSpaceException":
			throw new UserAleadyJoinSpaceException(errorResponse.getMessage());
		case "IsSimpleSpaceException":
			throw new IsSimpleSpaceException(errorResponse.getMessage());
		default:
			switch(HttpStatus.valueOf(error.getRawStatusCode())) {
			case UNAUTHORIZED:
				throw new InvalidLoginException(errorResponse.getMessage());
			case FORBIDDEN:
				throw new LoginFailureException(errorResponse.getMessage());
			default:
				throw new RestClientResponseException(errorResponse.getMessage(), error.getStatusCode().value(),
														errorResponse.getErrorCode(), error.getHeaders(), error.getBody().readAllBytes(), null);
			}
		}*/
	}
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
				response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
	}
	
	//エラーボディーをこれらで解析する予定
	/*
	private ErrorResponse getErrorResponse(ClientHttpResponse e) throws IOException {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^.*\"errorCode\":\"(.*)\".*\"message\":\"(.*)\"$");
		String bodyString = new String( e.getBody().readAllBytes());
		Matcher matcher = pattern.matcher(bodyString);
		if(matcher.matches()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(matcher.group(1));
			errorResponse.setMessage(matcher.group(2));
			return errorResponse;
		}else {
			return new ErrorResponse();
		}
	}
	
	@Data
	private class ErrorResponse {
		private String errorCode;
	    private String message;
	    
	    public ErrorResponse() {
	    	errorCode = "NULL";
	    }
	}
	*/
}
