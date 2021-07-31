package com.franklin.logoutarmycd.core;

import java.util.Date;

import com.franklin.logoutarmycd.SystemConfig;
import com.franklin.logoutarmycd.core.storage.IUserDataManager;
import com.franklin.logoutarmycd.core.storage.StorageException;
import com.franklin.logoutarmycd.core.storage.UserDataManagerFactory;

public class LogoutArmyCountdownManaer {
	private LogoutArmyCDUser m_user = null;
	private IUserDataManager userDataManager = null;
	private SystemConfig m_sysConfig = null;
	
	private LogoutArmyCountdownManaer(String uid){
		userDataManager = UserDataManagerFactory.createUserDataManager();
		try {
			m_user = userDataManager.getUser(uid);
		} catch (StorageException e) {
			throw new ApplicationException("Get user failed.", e);
		}
		m_sysConfig = SystemConfig.getInstance();
		if( m_user == null ){
			m_user = new LogoutArmyCDUser(uid);
			updateUserData();
		}
	}
	
	static public LogoutArmyCountdownManaer createLogoutArmyCountdownManaer(String uid){
		return new LogoutArmyCountdownManaer(uid);
	}
	
	public void saveCountdownSetting(Date joinDate, String periodYear,
			String periodMonth, String periodDate, int reduceDays, int otherReduceDays){
		m_user.setJoinDate(joinDate);
		m_user.setPeriodYear(periodYear);
		m_user.setPeriodMonth(periodMonth);
		m_user.setPeriodDate(periodDate);
		m_user.setReduceDays(reduceDays);
		m_user.setOtherReduceDays(otherReduceDays);
	}
	
	public void updateUserData(){
		try {
			userDataManager.updateUserData(m_user);
		} catch (StorageException e) {
			throw new ApplicationException("Update user failed.", e);
		}
	}
	
	public LogoutArmyCDUser getLoginUser(){
		return m_user;
	}
	
	/**
	 * Get the app user by the uid.
	 * 
	 * @param id
	 * @return
	 */
	public LogoutArmyCDUser getUser(String id){
		try {
			return userDataManager.getUser(id);
		} catch (StorageException e) {
			throw new ApplicationException("Get user failed.", e);
		}
	}
	
	/**
	 * �T�{�O�_���s�ϥΪ�
	 * 
	 * @return
	 */
	public boolean isNewUser(){
		return m_user.getJoinDate() == null;
	}

	
	/**
	 * ��o�n�J�ϥΪ̪��h��Ѿl�Ѽ�
	 * 
	 * @return
	 */
	public long getUserLeftDays(){
		return getUserLeftDays(new Long(m_user.getUID()));
	}
	
	/**
	 * Get the percentage of the total duration divide the current duration.
	 * 
	 * @return
	 */
	public double getUserPercentage(){
		return m_user.getPercentage();
	}
	
	/**
	 * Get the percentage of the total duration divide the current duration.
	 * 
	 * @param userId
	 * @return
	 */
	public double getUserPercentage(long userId){
		LogoutArmyCDUser user = getUser(userId);
		return user.getPercentage();
	}
	
	/**
	 * Get app user by uid.
	 * 
	 * @param uid
	 * @return
	 */
	public LogoutArmyCDUser getUser(long uid){
		try {
			return  userDataManager.getUser(new Long(uid).toString());
		} catch (StorageException e) {
			throw new ApplicationException("Get user failed.", e);
		}
	}
	
	/**
	 * ��ouserId�����ϥΪ̪��h��Ѿl�Ѽ�
	 * 
	 * @param userId
	 * @return
	 */
	public long getUserLeftDays(long userId){
		LogoutArmyCDUser user = getUser(userId);
		return user.getLeftDays();
	}
	
	/**
	 * Get the system config instance.
	 * 
	 * @return
	 */
	public SystemConfig getSysConfig(){
		return m_sysConfig;
	}
}
