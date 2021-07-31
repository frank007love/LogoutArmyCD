package com.franklin.logoutarmycd.web.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.logoutarmycd.SystemConfigEnum;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class PrepareInterceptor extends AbstractInterceptor {
	private Logger logger = LoggerFactory.getLogger(PrepareInterceptor.class);

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		logger.debug("Do preparation.");
		ServletContext application = ServletActionContext.getServletContext();
		String webRootPath = application.getRealPath("/");
		System.setProperty( SystemConfigEnum.WEB_PATH, webRootPath);
		System.setProperty( SystemConfigEnum.SYS_METADATA_PATH, webRootPath + "/_metadata");	
		String result = arg0.invoke();
		
		return result;
	}
}
