package com.franklin.logoutarmycd.core;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class OldLogoutArmyCDUser implements Serializable {
	private String m_uid = "";
	private Date m_joinDate = null;
	private String m_periodYear = null;
	private String m_periodMonth = null;
	private String m_periodDate = null;
	private int m_reduceDays = 0;
	
	public OldLogoutArmyCDUser(String uid){
		m_uid = uid;
	}
	
	/**
	 * Get the user's id.
	 * 
	 * @return
	 */
	public String getUID(){
		return m_uid;
	}
	
	/**
	 * Set the period year that user left the army.
	 * 
	 * @param year
	 * @return
	 */
	public void setPeriodYear(String year){
		m_periodYear = year;
	}
	
	/**
	 * Set the period month that user left the army.
	 * 
	 * @param month
	 * @return
	 */
	public void setPeriodMonth(String month){
		m_periodMonth = month;
	}

	/**
	 * Set the period date that user left the army.
	 * 
	 * @param date
	 * @return
	 */
	public void setPeriodDate(String date){
		m_periodDate = date;
	}

	/**
	 * Get the period year that user left the army.
	 * 
	 * @return
	 */
	public String getPeriodYear(){
		return m_periodYear;
	}
	
	/**
	 * Get the period month that user left the army.
	 * 
	 * @return
	 */
	public String getPeriodMonth(){
		return m_periodMonth;
	}
	
	/**
	 * Get the period date that user left the army.
	 * 
	 * @return
	 */
	public String getPeriodDate(){
		return m_periodDate;
	}
	
	/**
	 * Get the reduce days.
	 * 
	 * @return
	 */
	public int getReduceDays(){
		return m_reduceDays;
	}
	
	/**
	 * Set the reduce days.
	 * 
	 * @param days
	 */
	public void setReduceDays(int days){
		m_reduceDays = days;
	}
	
	/**
	 * Set the date that user join the army.
	 * 
	 * @param date
	 */
	public void setJoinDate(Date date){
		m_joinDate = date;
	}

	/**
	 * Get the date that user join the army.
	 * 
	 * @return
	 */
	public Date getJoinDate(){
		return m_joinDate;
	}
	
	/**
	 * 由於uid是獨一無二的, 因此uid相同則代表User相同
	 */
	@Override
	public boolean equals(Object obj) {
		if(  obj instanceof LogoutArmyCDUser ){
			LogoutArmyCDUser user = (LogoutArmyCDUser)obj;
			return user.getUID().equals(m_uid);
		}
		return false;
	}
}
