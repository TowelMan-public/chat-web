package com.example.demo.form;

import java.util.Comparator;
import java.util.List;

import com.example.demo.client.api.entity.DesireHaveUserResponse;
import com.example.demo.client.api.entity.DesireUserInGroupResponce;
import com.example.demo.form.inner.GroupListModel;
import com.example.demo.form.inner.HaveUserModel;

import lombok.Data;

/**
 * example.htmlを使うページのサイドに表示する内容をまとめたクラス
 */
@Data
public class SeeSideListModel {
	private Integer allNoticeCountInHaveUserList;
	private List<HaveUserModel> haveUserList;
	
	private Integer allNoticeCountInGroupList;
	private List<GroupListModel> groupList;
	
	private Integer desireHaveUserCount;
	private List<DesireHaveUserResponse> desireHaveUserList;
	
	private Integer desireGroupCount;
	private List<DesireUserInGroupResponce> desireGroupList;
	
	/**
	 * コンストラクタ<br>
	 * このクラスにセットするべきものをセットする
	 * @param haveUserList 友達リスト（html向け）
	 * @param groupList グループリスト（html向け）
	 * @param desireHaveUserList 友達追加申請者リスト
	 * @param desireGroupList グループに加入してほしい申請リスト
	 */
	public SeeSideListModel(List<HaveUserModel> haveUserList, List<GroupListModel> groupList, 
			List<DesireHaveUserResponse> desireHaveUserList, List<DesireUserInGroupResponce> desireGroupList) {
		
		haveUserList.sort(new HaveUserModelComparator());
		this.haveUserList = haveUserList;
		
		groupList.sort(new GroupListModelComparator());
		this.groupList = groupList;
		
		this.desireHaveUserCount = desireHaveUserList.size();
		this.desireHaveUserList = desireHaveUserList;
		
		this.desireGroupCount = desireGroupList.size();
		this.desireGroupList = desireGroupList;
		
		//通知数の合計値の算出
		setAllNoticeCountInHaveUserListByMember();
		setAllNoticeCountInGroupListByMember();
	}
	
	private void setAllNoticeCountInHaveUserListByMember() {
		allNoticeCountInHaveUserList = 0;
		
		for(HaveUserModel modelEntity : this.haveUserList) {
			allNoticeCountInHaveUserList += modelEntity.getNoticeCount();
			
			if(modelEntity.getNoticeCount() == 0)
				break;
		}
	}
	
	private void setAllNoticeCountInGroupListByMember() {
		allNoticeCountInGroupList = 0;
		
		for(GroupListModel modelEntity : this.groupList) {
			allNoticeCountInHaveUserList += modelEntity.getNoticeCount();
			
			if(modelEntity.getNoticeCount() == 0)
				break;
		}
	}
	
	private class HaveUserModelComparator implements Comparator<HaveUserModel> {
	    public int compare(HaveUserModel c1, HaveUserModel c2) {
	        if(c1.getNoticeCount() > c2.getNoticeCount()) {
	            return -1;
	        } else if(c1.getNoticeCount() < c2.getNoticeCount()) {
	            return 1;
	        } else {
	            return 0;
	        }
	    }
	}
	
	private class GroupListModelComparator implements Comparator<GroupListModel> {
	    public int compare(GroupListModel c1, GroupListModel c2) {
	        if(c1.getNoticeCount() > c2.getNoticeCount()) {
	            return -1;
	        } else if(c1.getNoticeCount() < c2.getNoticeCount()) {
	            return 1;
	        } else {
	            return 0;
	        }
	    }
	}
}
