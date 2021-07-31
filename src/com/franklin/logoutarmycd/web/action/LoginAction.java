package com.franklin.logoutarmycd.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.facebook.FacebookControllerFactory;
import com.franklin.logoutarmycd.SystemConfig;
import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.IFacebookRestClient;
import com.opensymphony.xwork2.Action;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class LoginAction implements ServletRequestAware, ServletResponseAware {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	private void getAccessToken(String apiKey, String secret){
		IFacebookRestClient<?> client = new FacebookJaxbRestClient(apiKey,
				secret);
		FacebookWebappHelper<?> facebook = new FacebookWebappHelper(request, response, apiKey, secret, client);
		
		String authURL = SystemConfig.getInstance().getAuthorizeUrl();
		//String loginurl = facebook.getLoginUrl(authURL, true);
		//request.setAttribute("test", loginurl);
		request.setAttribute("test", authURL);
		
//		String out = "<html><head><script type=\"text/javascript\">\nwindow.top.location = \"" + loginurl + "\";\n</script></head><body></body></html>";
//		//facebook.redirect(loginurl);
//		try {
//			this.response.getWriter().print(out);
//			this.response.flushBuffer();
//		} catch (IOException e) {
//			logger.error("",e);
//		}
	}
	
	public String execute(){
		SystemConfig sysConfig = SystemConfig.getInstance();
		String apiKey = sysConfig.getApi_key();
		String secret = sysConfig.getSecret_key();

		String access_token = (String)SessionUtil.getAttribute(request, SessionKeyEnum.ACCESS_TOKEN);
		if (access_token == null) {
			getAccessToken(apiKey, secret);
			//return Action.NONE;
			return "doLogin";
		}
		
		LogoutArmyCountdownManaer lacdManager = (LogoutArmyCountdownManaer)SessionUtil.getAttribute(request, SessionKeyEnum.LGACD_MANAGER);
		if( lacdManager == null ){
			FacebookClient facebookClient = new DefaultFacebookClient(access_token);
			User user = facebookClient.fetchObject("me", User.class);
			
			SessionUtil.saveAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER, FacebookControllerFactory.createFacebookControllerInstance(request, response));
			
			logger.info("user {} login.", user.getId());
			
			SessionUtil.saveAttribute(request, SessionKeyEnum.USER_ID, user.getId());
			lacdManager = LogoutArmyCountdownManaer.createLogoutArmyCountdownManaer(user.getId()+"");
			SessionUtil.saveAttribute(request, SessionKeyEnum.LGACD_MANAGER, lacdManager);
			return Action.SUCCESS;
		}
		
		return Action.SUCCESS;
		
		/*
		
		
		
		
		
		
		SystemConfig sysConfig = SystemConfig.getInstance();
		String url = sysConfig.getAuthorizeUrl();
		
		logger.debug("Authorization url: {}", url);
		
		FacebookController fbController = (FacebookController)
		SessionUtil.getAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER);
		logger.info("fbController1={}", fbController);
		if( fbController == null )
			fbController = FacebookControllerFactory.createFacebookControllerInstance(request, response); 
		
		logger.info("fbController2={}", fbController);
		
		FacebookWebappHelper<Object> webHelper = fbController.getFacebookWebappJaxbHelper();
		
		boolean flag = true;
		try {
			logger.info("Login Redirect1.");
			flag = webHelper.requireLogin(url);
			logger.info("Login Redirect2.");
		} catch( Exception e ){
			logger.info("Login Redirect.");
			logger.error("login failed: ", e);
			return Action.ERROR;
		}
		
		logger.info("{},{}", flag, webHelper.get_loggedin_user());
		if( !flag ){
			// Set loginUser session
			long loginUser = webHelper.get_loggedin_user();
			
			
			//SessionUtil.getAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER);
			
			try {
				logger.info( "{} login the system, token={}", loginUser, webHelper.getFacebookRestClient().auth_createToken());
			} catch (FacebookException e) {
				logger.error("user {} login failed: ", e);
			}
			
			SessionUtil.saveAttribute(request, SessionKeyEnum.USER_ID, loginUser);
			// Set lacdManager session
			LogoutArmyCountdownManaer lacdManager = LogoutArmyCountdownManaer.createLogoutArmyCountdownManaer(loginUser+"");
			SessionUtil.saveAttribute(request, SessionKeyEnum.LGACD_MANAGER, lacdManager);
			
			return Action.SUCCESS;
		}
		*/
		//return Action.NONE;
	}
}
