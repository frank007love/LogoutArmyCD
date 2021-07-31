package com.franklin.logoutarmycd.web.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.web.util.AjaxUtil;
import com.franklin.logoutarmycd.web.util.CacheObject;
import com.franklin.logoutarmycd.web.util.SessionUtil;

import com.restfb.types.User;
import com.opensymphony.xwork2.Action;

public class GetFriendInfoAction extends AbstractLACDAction {
	private Logger logger = LoggerFactory.getLogger(GetFriendInfoAction.class);
	private String getUserInfoXmlFromAPI(){
		List<User> fb_ap_friend_list = mFbController.getAppUserFriendList();
		int new_friends = 0;
		int logout_friends = 0;
		int army_friends = 0;
		for( User fb_user : fb_ap_friend_list ){
			long userid = Long.valueOf(fb_user.getId());
			LogoutArmyCDUser user = mLacdManager.getUser(userid);
			if( user == null ){
				logger.warn("User {} doen't exist.", userid);
			} else if( user.isNewUser() ){
				new_friends++;
			} else if( user.isLogout() ){
				logout_friends++;
			} else {
				army_friends++;
			}
		}
		
		int total_ap_friends = new_friends + logout_friends + army_friends;
		StringBuffer xml = new StringBuffer("<friendinfo>");
		xml.append("<new>").append(new_friends).append("</new>");
		xml.append("<logout>").append(logout_friends).append("</logout>");
		xml.append("<army>").append(army_friends).append("</army>");
		xml.append("<total>").append(total_ap_friends).append("</total>");
		xml.append("</friendinfo>");
		
		return xml.toString();
	}
	
	private String getUserInfoXml(){
		String sessionKey = mLacdManager.getLoginUser().getUID() + "-friendinfo";
	
		Object sessionObj = SessionUtil.getAttribute(request, sessionKey);
		CacheObject cacheObject = sessionObj == null ? null : (CacheObject)sessionObj;
		
		if( cacheObject == null || cacheObject.isExpired() ){
			logger.debug("Get friend info from api.");
			String xml = getUserInfoXmlFromAPI();
			cacheObject = CacheObject.createCacheObject(xml);
			SessionUtil.saveAttribute(request, sessionKey, cacheObject);
		} else {
			logger.debug("Get friend info from cache.");
		}
		
		return (String)cacheObject.getCacheObject();
	}
	
	public String execute(){
		try {
			initSessionData();		
			AjaxUtil.responseXmlData(response, getUserInfoXml());
		} catch( Exception e ){
			logger.error("Get friend information failed.", e);
			return Action.NONE;
		}
		return Action.NONE;
	}
}
