package com.franklin.logoutarmycd.core;

import com.franklin.logoutarmycd.INew;

public class LogoutNewAdapter implements INew {

	private LogoutArmyNew m_laNew = null;
	
	public LogoutNewAdapter(LogoutArmyNew laNew){
		m_laNew = laNew;
	}

	@Override
	public String getUserName() {
		return m_laNew.getUserName();
	}

	@Override
	public String getMessage() {
		return m_laNew.getMessage();
	}
	
	public long getLeftDays(){
		return m_laNew.getLeftDays();
	}
	
	public long getJoinDays(){
		return m_laNew.getJoinDays();
	}
	
	public boolean isNewUser(){
		return m_laNew.isNewUser();
	}
	
	public long getUID(){
		return m_laNew.getUID();
	}
	
	public String getXMLString(){
		StringBuffer xml = new StringBuffer("<new>\n");
		xml.append("<user>" + getUserName() + "</user>\n");
		xml.append("<uid>" + getUID() + "</uid>\n");
		xml.append("<isNewUser>" + isNewUser() + "</isNewUser>\n");
		xml.append("<leftdays>" + getLeftDays() + "</leftdays>\n");
		xml.append("<joindays>" + getJoinDays() + "</joindays>\n");
		xml.append("</new>\n");
		return xml.toString();
	}
}
