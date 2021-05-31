package com.example.demo.client.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.client.api.entity.DesireUserInGroupResponce;
import com.example.demo.client.exception.NotFoundException;
import com.example.demo.client.exception.NotInsertedGroupDesireException;
import com.example.demo.client.rest.RestTemplateAdapter;
import com.example.demo.security.UserDetailsImp;

import lombok.Data;

/**
 * グループ加入してほしい申請に関するAPIを呼び出すクラス
 */
@Component
public class DesireGroupApi {

	@Autowired
	RestTemplateAdapter restTemplateAdapter;
	
	private static final String ROOT_URL = ApiUrlRootConfing.ROOT_URL + "/desire/group";
	
	/**
	 * グループに加入してほしい申請リストの取得
	 * @param user ログイン情報
	 * @return グループに加入してほしい申請リスト
	 */
	public List<DesireUserInGroupResponce> getDesireUserList(UserDetailsImp user) {
		final String URL = ROOT_URL + "/gets";
		
		return restTemplateAdapter.getForListWhenLogined(URL, null, DesireUserInGroupResponce[].class, user);
	}
	
	/**
	 * グループに加入してほしい申請を取得する
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルーム
	 * @return グループに加入してほしい申請
	 * @throws NotFoundException
	 */
	public List<DesireUserInGroupResponce> getDesireUser(UserDetailsImp user, Integer talkRoomId)
			throws NotFoundException{
		final String URL = ROOT_URL + "/get";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		
		return restTemplateAdapter.getForListWhenLogined(URL, dto, DesireUserInGroupResponce[].class, user);
	}
	
	/**
	 * グループ加入してほしい申請を断る
	 * @param user ログイン情報
	 * @param talkRoomId グループトークルームID
	 * @throws NotFoundException
	 * @throws NotInsertedGroupDesireException 
	 */
	public void deleteDesireGroup(UserDetailsImp user, Integer talkRoomId)
			throws NotFoundException, NotInsertedGroupDesireException{
		final String URL = ROOT_URL + "/delete";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * グループ加入してほしい申請を受ける
	 * @param userログイン情報
	 * @param talkRoomId グループトークルームID
	 * @throws NotFoundException
	 * @throws NotInsertedGroupDesireException 
	 */
	public void joinGroup(UserDetailsImp user, Integer talkRoomId)
			throws NotFoundException, NotInsertedGroupDesireException{
		final String URL = ROOT_URL + "/join";
		
		var dto = new Dto();
		dto.setTalkRoomId(talkRoomId);
		
		restTemplateAdapter.postForObjectWhenLogined(URL, dto, null, user);
	}
	
	/**
	 * 友達追加申請に関するAPIのパラメターを送るためのDtoクラス
	 */
	@Data
	public class Dto{
		private Integer talkRoomId;
	}
}
