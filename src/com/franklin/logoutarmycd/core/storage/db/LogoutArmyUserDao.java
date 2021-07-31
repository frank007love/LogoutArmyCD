package com.franklin.logoutarmycd.core.storage.db;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.storage.HibernateUtil;
import com.franklin.logoutarmycd.core.storage.IUserDataManager;
import com.franklin.logoutarmycd.core.storage.StorageException;
import com.franklin.logoutarmycd.core.storage.db.po.IPoLogoutArmyCDUser;

public class LogoutArmyUserDao extends LogoutArmyCDDaoSupport implements IUserDataManager {
	static private Logger logger = LoggerFactory.getLogger(LogoutArmyUserDao.class);

	static private IUserDataManager m_instance = null;
	
	private LogoutArmyUserDao(){
		
	}
	
	static public synchronized IUserDataManager getInstance(){
		if( m_instance == null ){
			m_instance = new LogoutArmyUserDao();
		}
		return m_instance;
	}
	
	final static String SELECT_ALLUSER_HSQL = "from Logoutarmycduser0,Logoutarmycduser1," +
			"Logoutarmycduser2,Logoutarmycduser3,Logoutarmycduser4,Logoutarmycduser5," +
			"Logoutarmycduser6,Logoutarmycduser7,Logoutarmycduser8,Logoutarmycduser9";

	@SuppressWarnings("unchecked")
	@Override
	public List<LogoutArmyCDUser> getUserList() throws StorageException {
		List<LogoutArmyCDUser> userList = new ArrayList<LogoutArmyCDUser>();
		List<IPoLogoutArmyCDUser> poUserList = new ArrayList<IPoLogoutArmyCDUser>();
		Session session = null;
		try {
			session = getSession();
		} catch (HibernateException e) {
			throw new StorageException("Get session failed.", e);
		}
		
		for( int i = 0 ; i < 10 ; i++ ){
			String className = "com.franklin.logoutarmycd.core.storage.db.po.Logoutarmycduser" + i;
			try {
				Class<?> poUserClass = Class.forName(className);
				poUserList.addAll(session.createCriteria(poUserClass).list());
			} catch (Exception e) {
				throw new StorageException("Get user list failed.", e);
			}
		}
		
		closeSession();
		
		if( poUserList != null ){
			for( IPoLogoutArmyCDUser poUser : poUserList ){
				userList.add(traslateUserModel(poUser));
			}
		}
		return userList;
	}

	@Override
	public LogoutArmyCDUser getUser(String aUid) {
		IPoLogoutArmyCDUser poUser = getPOLogoutArmyCDUser(aUid);
		if( poUser == null ){
			return null;
		}
		return traslateUserModel(poUser);
	}
	
	private IPoLogoutArmyCDUser getPOLogoutArmyCDUser(String aUid){
		Class<IPoLogoutArmyCDUser> userClass = getIPoLogoutArmyCDUserFromUID(aUid);
		IPoLogoutArmyCDUser poUser = null;
		Session session = null;
		try {
			session = getSession();
			poUser = (IPoLogoutArmyCDUser)session.get(userClass, aUid);
			session.flush();
		} catch (HibernateException e) {
			poUser = null;
		} finally {
			closeSession();
		}
		return poUser;
	}

	/**
	 * Translate the uid to the index for db table.
	 * 
	 * @param aUid
	 * @return
	 */
	static public int getIndex(String aUid){
		if( aUid == null || aUid.isEmpty() ){
			throw new RuntimeException("Translate uid to index failed.");
		}
		char c = aUid.charAt(aUid.length()-1);
		return (c - 48) % 10;
	}
	
	@SuppressWarnings("unchecked")
	private Class<IPoLogoutArmyCDUser> getIPoLogoutArmyCDUserFromUID(String aUid){
		int index = getIndex(aUid);
		String className = "com.franklin.logoutarmycd.core.storage.db.po.Logoutarmycduser" + index;
		try {
			Class<IPoLogoutArmyCDUser> lacuserClass = (Class<IPoLogoutArmyCDUser>)Class.forName(className);
			return lacuserClass;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	private LogoutArmyCDUser traslateUserModel(IPoLogoutArmyCDUser aUser){
		LogoutArmyCDUser user = new LogoutArmyCDUser(aUser.getFbuserid());
		user.setJoinDate(aUser.getJoindate());
		user.setPeriodYear(aUser.getPeriodyear());
		user.setPeriodMonth(aUser.getPeriodmonth());
		user.setPeriodDate(aUser.getPerioddate());
		user.setReduceDays(aUser.getReducedays());
		user.setOtherReduceDays(aUser.getOtherreducedays());
		return user;
	}
	
	private IPoLogoutArmyCDUser traslateUserModel(LogoutArmyCDUser user){
		String uid = user.getUID();
		IPoLogoutArmyCDUser poLACDUser = null;
		Class<IPoLogoutArmyCDUser> lacuserClass = getIPoLogoutArmyCDUserFromUID(uid);
		if( lacuserClass == null ){
			return null;
		}
		try {
			Constructor<IPoLogoutArmyCDUser>  lacuserConstructor = lacuserClass.getConstructor(
					String.class, Date.class, String.class, String.class, String.class, Integer.class, Integer.class);
			poLACDUser = lacuserConstructor.newInstance(user.getUID(), user.getJoinDate(), user.getPeriodYear(), user.getPeriodMonth(),
					user.getPeriodDate(), user.getReduceDays(), user.getOtherReduceDays()
			);
			logger.debug("LogoutArmyCDUser data: id={},joinDate={},py={},pm={},pd={},rd={},ord={}",
					new Object[]{
					user.getUID(), user.getJoinDate(), user.getPeriodYear(), user.getPeriodMonth(),
					user.getPeriodDate(), user.getReduceDays(), user.getOtherReduceDays()
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			poLACDUser = null;
		} 
		return poLACDUser;
	}
	
	@Override
	public void updateUserData(LogoutArmyCDUser aUser) throws StorageException {
		if( aUser == null ){
			throw new StorageException("The user instance can't be null.");
		}
		
		IPoLogoutArmyCDUser user = getPOLogoutArmyCDUser(aUser.getUID());
		if( user == null){
			user = traslateUserModel(aUser);
			addUser(user);
			return;
		} 
		
		Transaction transaction = null;
		Session session = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.delete(user);
			user = traslateUserModel(aUser);
			session.save(user);
			session.flush();
			transaction.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(transaction);
			throw new StorageException("Save/Update user " + user.getFbuserid() + " failed.",e);
		} finally {
			closeSession();
		}
	}
	
	private void addUser(IPoLogoutArmyCDUser user) throws StorageException{
		Transaction transaction = null;
		Session session = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(user);
			session.flush();
			transaction.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(transaction);
			throw new StorageException("Add user " + user.getFbuserid() + " failed.",e);
		} finally {
			closeSession();
		}
	}

	@Override
	public void deleteUser(String aUid) throws StorageException {
		IPoLogoutArmyCDUser user = getPOLogoutArmyCDUser(aUid);
		if( user == null ){
			throw new StorageException("User " + aUid + " doesn't exist.");
		}
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.delete(user);
			session.flush();
			transaction.commit();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(transaction);
			throw new StorageException("Delete user " + aUid + " failed.",e);
		} finally {
			closeSession();
		}
	}

}
