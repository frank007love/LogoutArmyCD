package com.franklin.facebook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacebookControllerFactory {

	static public FacebookController createFacebookControllerInstance(HttpServletRequest aRequest, 
			HttpServletResponse aResponse){
		return FacebookControllerProxy.init(aRequest, aResponse);
	}
	
}
