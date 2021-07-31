package com.franklin.logoutarmycd.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.franklin.facebook.FacebookController;
import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;

public abstract class AbstractLACDAction implements ServletRequestAware,ServletResponseAware {
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected LogoutArmyCountdownManaer mLacdManager = null;
	protected FacebookController mFbController = null;

	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}	
	
	protected void initSessionData(){
		mLacdManager = (LogoutArmyCountdownManaer)SessionUtil.
			getAttribute(request, SessionKeyEnum.LGACD_MANAGER);
		mFbController = (FacebookController)
			SessionUtil.getAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER);
		mFbController.setHttpServletData(request, response);
	}
}
