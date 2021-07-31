package com.franklin.logoutarmycd.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.facebook.FacebookController;
import com.franklin.logoutarmycd.SystemConfig;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.google.code.facebookapi.FacebookException;
import com.opensymphony.xwork2.Action;

public class InviteFriendAction implements ServletRequestAware {
	private HttpServletRequest request = null;
	private Logger logger = LoggerFactory.getLogger(InviteFriendAction.class);

	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}

	public String execute(){
		FacebookController fbController = (FacebookController)
			SessionUtil.getAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER);
		List<Long> friendIDList = null;
		try {
			friendIDList = fbController.getAppUserFriendIdList();
		} catch (FacebookException e) {
			logger.error("Get user's app friend list failure: {}", e.getMessage());
		}
		
		StringBuilder excludeFriendString = new StringBuilder();
		int size = friendIDList.size();
		for( int i = 0 ; i < size ; i++ ){
			Long friendID = friendIDList.get(i);
			excludeFriendString.append(friendID);
			if( (size-1) != i ){
				excludeFriendString.append(",");
			}
		}
		SystemConfig sysConfig = SystemConfig.getInstance();
		this.request.setAttribute("fb_apurl", sysConfig.getFacebookApUrl());
		this.request.setAttribute("ap_url", sysConfig.getApUrl()+ "counter.action");
		this.request.setAttribute("exclude_friend_string", excludeFriendString.toString());
		return Action.SUCCESS;
	}
}
