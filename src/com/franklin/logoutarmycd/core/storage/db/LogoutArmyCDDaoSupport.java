package com.franklin.logoutarmycd.core.storage.db;

import com.franklin.logoutarmycd.core.storage.HibernateUtil;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

public class LogoutArmyCDDaoSupport {
	
	public LogoutArmyCDDaoSupport(){
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		closeSession();
	}
	
	public Session getSession() throws HibernateException{
		return HibernateUtil.currentSession();
	}
	
	public void closeSession(){
		HibernateUtil.closeSession();
	}
}
