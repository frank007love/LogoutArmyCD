package com.franklin.logoutarmycd.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;

public class DirectForumAction implements ServletRequestAware {
	private HttpServletRequest request = null;
	private LogoutArmyCountdownManaer m_lacdManager = null;

	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public String execute(){
		m_lacdManager = (LogoutArmyCountdownManaer)SessionUtil.
			getAttribute(request, SessionKeyEnum.LGACD_MANAGER);
		String forumUrl = m_lacdManager.getSysConfig().getForumUrl();
		request.setAttribute("forum_url", forumUrl);
		return Action.SUCCESS;
	}
}
