package com.franklin.logoutarmycd.web.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.facebook.FacebookController;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class FacebookSessionCheckerInterpreter extends AbstractInterceptor {
	private Logger logger = LoggerFactory.getLogger(FacebookSessionCheckerInterpreter.class);
	
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		logger.debug("Check facebook session.");
		
		Map<?, ?> sessionMap = arg0.getInvocationContext().getSession();
		String sessionOut = "SessionOut";
		
		FacebookController fbController = (FacebookController)sessionMap.get(SessionKeyEnum.FACEBOOK_CONTROLLER);
		Long userid = null;
		
		if( fbController != null){
			try{
				userid = fbController.getLoginUserId();
			} catch ( Exception e ){
				userid = null;	
				logger.warn("Get facebook user id failed: {}", e.getMessage());
			}
			if( userid != null ){
				return arg0.invoke();
			} 
			cleanupSession(sessionMap);
			return sessionOut;
		} 
		return arg0.invoke();
	}

	private void cleanupSession(Map<?, ?> sessionMap){
		sessionMap.remove(SessionKeyEnum.USER_ID);
		sessionMap.remove(SessionKeyEnum.LGACD_MANAGER);
		sessionMap.remove(SessionKeyEnum.FACEBOOK_CONTROLLER);
		sessionMap.remove(SessionKeyEnum.ACCESS_TOKEN);
		sessionMap.remove(SessionKeyEnum.FACEBOOK_CONTROLLER_RESET_FLAG);
	}
}
