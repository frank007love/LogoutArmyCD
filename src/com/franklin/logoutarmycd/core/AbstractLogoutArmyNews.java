package com.franklin.logoutarmycd.core;

import com.franklin.logoutarmycd.INew;

public abstract class AbstractLogoutArmyNews implements INew {

	protected LogoutArmyCDUserAdapter m_userAdapter = null;
	
	
	protected AbstractLogoutArmyNews(LogoutArmyCDUserAdapter userAdapter){
		m_userAdapter = userAdapter;
	}
	
	@Override
	public String getUserName() {
		return m_userAdapter.getUserName();
	}
	
	public long getUID(){
		return m_userAdapter.getUID();
	}
}
