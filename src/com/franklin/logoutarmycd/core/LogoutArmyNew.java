package com.franklin.logoutarmycd.core;

public class LogoutArmyNew extends AbstractLogoutArmyNews {

	public LogoutArmyNew(LogoutArmyCDUserAdapter userAdapter){
		super(userAdapter);
	}

	@Override
	public String getMessage() {
		return "";
	}
	
	public boolean isNewUser(){
		return m_userAdapter.isNewUser();
	}
	
	public long getLeftDays(){
		return m_userAdapter.getLeftDays();
	}
	
	public String getUserName(){
		return m_userAdapter.getUserName();
	}
	
	public long getJoinDays(){
		return m_userAdapter.getJoinDays();
	}
	
	public long getUID(){
		return m_userAdapter.getUID();
	}
}
