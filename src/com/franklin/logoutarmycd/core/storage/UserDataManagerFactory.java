package com.franklin.logoutarmycd.core.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

import com.franklin.logoutarmycd.core.storage.db.LogoutArmyUserDao;

public class UserDataManagerFactory {
	static private Logger logger = LoggerFactory.getLogger(UserDataManagerFactory.class);
	
	static public synchronized IUserDataManager createLogoutArmyUserDao(){
		IUserDataManager userDataManager = null;
		
		try {
			userDataManager = LogoutArmyUserDao.getInstance();
//			if(((LogoutArmyUserDao)userDataManager).getSession() == null){
//				Configuration config = new Configuration().configure();
//				SessionFactory factory = config.buildSessionFactory();
//				Session session = factory.openSession();
//				((LogoutArmyUserDao)userDataManager).setSession(session);
//			}
		} catch( Exception e){
			userDataManager = null;
			logger.error("Init LogoutArmyUserDao instance failed.",e);
		}
		
		return userDataManager;
	}
	
	static public IUserDataManager createUserSerializer(){
		return UserSerializer.getInstance();
	}
	
	static public synchronized IUserDataManager createUserDataManager(){
		//IUserDataManager userDataManager = null;//createLogoutArmyUserDao();
		IUserDataManager userDataManager = createLogoutArmyUserDao();
		
		if( userDataManager == null ){
			userDataManager = createUserSerializer();
		}
		
		return userDataManager;
	}
	
}
