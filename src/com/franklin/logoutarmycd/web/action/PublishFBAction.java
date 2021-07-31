package com.franklin.logoutarmycd.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.AjaxUtil;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class PublishFBAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {
	private static final long serialVersionUID = 6365106910501357784L;
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	public String enablePublishFB(){
		SessionUtil.saveAttribute(request, SessionKeyEnum.FB_PUBLISH_ENABLE_FLAG, true);
		return Action.NONE;
	}
	
	public String checkPublishFB(){
		Boolean result = (Boolean)SessionUtil.getAttribute(request, SessionKeyEnum.FB_PUBLISH_ENABLE_FLAG);
		if( result == null ){
			AjaxUtil.responseHTMLData(response, "false");
		} else {
			AjaxUtil.responseHTMLData(response, String.valueOf(result));
		}
		return Action.NONE;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}
	
}
