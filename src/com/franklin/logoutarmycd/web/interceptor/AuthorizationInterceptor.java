package com.franklin.logoutarmycd.web.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.facebook.FacebookController;
import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class AuthorizationInterceptor extends AbstractInterceptor {
	private Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		logger.debug("Check app session.");
		
		Map session = arg0.getInvocationContext().getSession();
		String access_token = (String)session.get(SessionKeyEnum.ACCESS_TOKEN);
		LogoutArmyCountdownManaer lacdManager = (LogoutArmyCountdownManaer)session.get(SessionKeyEnum.LGACD_MANAGER);
		
		if( access_token == null || lacdManager == null ){
			return Action.LOGIN;
		}
		return arg0.invoke();
	}

}
