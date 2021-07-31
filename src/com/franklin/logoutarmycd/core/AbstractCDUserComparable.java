package com.franklin.logoutarmycd.core;

public abstract class AbstractCDUserComparable implements Comparable<AbstractCDUserComparable> {

	protected LogoutArmyCDUser m_user = null;
	
	protected AbstractCDUserComparable(LogoutArmyCDUser user){
		m_user = user;
	}
	
	@Override
	abstract public int compareTo(AbstractCDUserComparable arg0);

	public LogoutArmyCDUser getUser(){
		return m_user;
	}
}
