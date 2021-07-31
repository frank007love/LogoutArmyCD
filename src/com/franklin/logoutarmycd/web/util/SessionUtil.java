package com.franklin.logoutarmycd.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	/**
	 * 將物件存入Session中
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
	 * 從Session中移除物件
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
	 * 從Session中取得物件
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
