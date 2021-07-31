package com.franklin.logoutarmycd.core;

import com.restfb.types.User;

public class LogoutArmyCDUserAdapter {

	private LogoutArmyCDUser m_lacdUser = null;
	private User m_fbUser = null;
	
	public LogoutArmyCDUserAdapter(LogoutArmyCDUser lacdUser, User fbUser){
		m_lacdUser = lacdUser;
		m_fbUser = fbUser;
	}
	
	/**
	 * Get the days that the user left the army.
	 * 
	 * @return
	 */
	public long getLeftDays(){
		if( m_lacdUser == null ) return 0;
		return m_lacdUser.getLeftDays();
	}
	
	/**
	 * Get the days left the join army date.
	 * 
	 * @return
	 */
	public long getJoinDays(){
		if( m_lacdUser == null ) return 0;
		return m_lacdUser.getJoinDays();
	}
	
	/**
	 * Get facebook user's name.
	 * 
	 * @return
	 */
	public String getUserName(){
		return m_fbUser.getName();
	}
	
	public boolean isNewUser(){
		if( m_lacdUser == null ) return true;
		return m_lacdUser.isNewUser();
	}
	
	public long getUID(){
		return Long.valueOf(m_fbUser.getId());
	}
}
