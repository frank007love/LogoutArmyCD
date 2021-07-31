package com.franklin.logoutarmycd.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.util.CalendarUtil;
import com.franklin.logoutarmycd.util.CloseStreamUtils;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;

public class SaveCounterSettingAction implements ServletRequestAware,ServletResponseAware {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	

	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}	
	
	/*
	private Date getDate(String year, String month, String date){
		Calendar c = Calendar.getInstance();
		c.clear();	
		c.set(new Integer(year), new Integer(month)-1,
				new Integer(date));
		return c.getTime();
	} */
	
	public String execute(){
		String joinYear = request.getParameter("joinYear");
		String joinMonth = request.getParameter("joinMonth");
		String joinDate = request.getParameter("joinDate");
		String joinPerYear =  request.getParameter("joinPerYear");
		String joinPerMonth =  request.getParameter("joinPerMonth");
		String joinPerDate =  request.getParameter("joinPerDate");
		String reduceDays =  request.getParameter("reduceDays");
		String otherReduceDays =  request.getParameter("otherReduceDays");
		
		Date D_joinDate = CalendarUtil.getDate(joinYear, joinMonth, joinDate);
		
		LogoutArmyCountdownManaer lacdManager = (LogoutArmyCountdownManaer)SessionUtil.
		getAttribute(request, SessionKeyEnum.LGACD_MANAGER);
		lacdManager.saveCountdownSetting( D_joinDate, joinPerYear,
				joinPerMonth, joinPerDate, new Integer(reduceDays), new Integer(otherReduceDays));
		lacdManager.updateUserData();
		
	    this.response.setContentType("text/xml");        
	    this.response.setHeader("Cache-Control", "no-cache");        
	    this.response.setCharacterEncoding("UTF-8");
		
	    PrintWriter out = null;
	    try{
		    out = this.response.getWriter();
		    out.print("ok");
	    } catch( IOException e){
	    	return null;
	    } finally {
	    	CloseStreamUtils.closeWriter(out);
	    }
	    
		return Action.NONE;
	}
}
