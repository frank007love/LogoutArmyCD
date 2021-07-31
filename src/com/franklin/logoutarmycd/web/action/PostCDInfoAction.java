package com.franklin.logoutarmycd.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.facebook.FacebookController;
import com.franklin.logoutarmycd.SystemConfig;
import com.franklin.logoutarmycd.util.CloseStreamUtils;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;

public class PostCDInfoAction implements ServletRequestAware,ServletResponseAware {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Logger logger = LoggerFactory.getLogger(PostCDInfoAction.class);

	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}	
	
	public String execute(){
		String message = request.getParameter("cd_message");
		FacebookController fbController = (FacebookController)
			SessionUtil.getAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER);
		
		String response_message = "";
		try {
			fbController.postMessageToWall(message);
			response_message = getNormalMessage();
		} catch ( Exception e ){
			response_message = getErrorMessage();
		}
		writeMessage(response_message);
		
		logger.debug(response_message);
		
		return Action.NONE;
	}
	
	private String getErrorMessage(){
		String authUrl = SystemConfig.getInstance().getAuthorizeUrl();
		return authUrl;
	}
	
	private String getNormalMessage(){
		return "success";
	}
	
	private void writeMessage(String message){
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
	    PrintWriter out = null;
	    try{
		    out = this.response.getWriter();
		    out.print(message);
	    } catch( IOException e){
	    	throw new RuntimeException(e);
	    } finally {
	    	CloseStreamUtils.closeWriter(out);
	    }
	}
}
