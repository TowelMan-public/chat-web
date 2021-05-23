package com.example.demo.client.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.TalkResponse;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotHaveUserException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * 友達トークに関するAPIを呼び出すクラス
 */
@Component
public class DialogueApi {
	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/diarogue";
	
	/**
	 * 友達トークリストの取得をするAPI
	 * @param user
	 * @param userIdName
	 * @param maxSize
	 * @param startIndex
	 * @return 友達トークリスト
	 * @throws NotFoundException
	 * @throws NotHaveUserException
	 */
	public List<TalkResponse> getDiarogueTalks(UserDetailsImp user, String userIdName, Integer maxSize, Integer startIndex) 
			throws NotFoundException, NotHaveUserException {
		final String URL = ROOT_URL + "/gets/talks";
		
		var dto = new Dto();
		dto.setUserIdName(userIdName);
		dto.setStartIndex(startIndex);
		dto.setMaxSize(maxSize);
		
		return restTemplateAdapter.getForObjectsWhenLogined(URL, dto, TalkResponse.class, user);
	}
	
	/**
	 * 友達トークに関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private String userIdName;
		private Integer maxSize;
		private Integer startIndex;
	}
}
