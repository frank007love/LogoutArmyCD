package com.franklin.logoutarmycd.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	/**
	 * �N����s�JSession��
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	static public void saveAttribute(HttpServletRequest request,
			String key, Object value){
		HttpSession session =  request.getSession();
		session.setAttribute(key, value);
	}
	
	/**
	 * �qSession����������
	 * 
	 * @param request
	 * @param key
	 */
	static public void removeAttribute(HttpServletRequest request,
			String key){
		HttpSession session =  request.getSession();
		session.removeAttribute(key);
	}
	
	/**
	 * �qSession�����o����
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	static public Object getAttribute(HttpServletRequest request,
			String key){
		HttpSession session =  request.getSession();
		return session.getAttribute(key);
	}
}
