package com.franklin.logoutarmycd.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.util.CloseStreamUtils;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;

public class GetArmyInfoAction implements ServletRequestAware,ServletResponseAware {
	private Logger logger = LoggerFactory.getLogger(GetArmyInfoAction.class);
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private LogoutArmyCountdownManaer m_lacdManager = null;
	
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}	

	private String getJoinDateString(){
		LogoutArmyCDUser user = m_lacdManager.getLoginUser();
		Date join_date = user.getJoinDate();
		
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(join_date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int date = c.get(Calendar.DATE);
		
		return year + "-" + month + "-" + date;
	}
	
	private String[] getJoinPeriodArray(){
		
		LogoutArmyCDUser user = m_lacdManager.getLoginUser();
	
		String []joinPeriodArray = new String[3];
		joinPeriodArray[0] = user.getPeriodYear();
		joinPeriodArray[1] = user.getPeriodMonth();
		joinPeriodArray[2] = user.getPeriodDate();
		
		return joinPeriodArray;
	}

	private String getReduceDays(){
		LogoutArmyCDUser user = m_lacdManager.getLoginUser();
		return user.getReduceDays() +"";	
	}
	
	private String getOtherReduceDays(){
		LogoutArmyCDUser user = m_lacdManager.getLoginUser();
		return user.getOtherReduceDays() +"";	
	}
	
	private String getUserInfoXml(){
		String joinDate = "";
		String []joinPeriodDateArray = null;
		String reduceDays = "";
		String otherReduceDays = "";
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><root>";
		if( m_lacdManager.isNewUser() ){
			xml += "NewUser";
		} else {
			joinDate = getJoinDateString();
			joinPeriodDateArray = getJoinPeriodArray();
			reduceDays = getReduceDays();
			otherReduceDays = getOtherReduceDays();
			
		    xml+= "<joinDate>" + joinDate + "</joinDate>";
		    xml+= "<joinPeriodDate><year>" + joinPeriodDateArray[0] + "</year><month>" +
		    	joinPeriodDateArray[1] + "</month><date>" + joinPeriodDateArray[2] +
		    		"</date></joinPeriodDate>";
		    xml+= "<reduceDays>" + reduceDays + "</reduceDays>";
		    xml+= "<otherReduceDays>" + otherReduceDays + "</otherReduceDays>";
		}
		xml += "</root>";
		
		return xml;
	}
	
	public String execute(){	
		this.response.setContentType("text/xml; charset=UTF-8");          
		this.response.setHeader("Cache-Control", "no-cache");
		
		m_lacdManager = (LogoutArmyCountdownManaer)SessionUtil.
			getAttribute(request, SessionKeyEnum.LGACD_MANAGER);

		String xml = getUserInfoXml();
	
		PrintWriter out = null;
	    try{
		    out = this.response.getWriter();
		    out.print(xml);
	    } catch( IOException e){
	    	logger.error("Response army info to web failure: {}", e.getMessage());
	    } finally {
	    	CloseStreamUtils.closeWriter(out);
	    }
	    
		return Action.NONE;
	}
}
