package com.franklin.logoutarmycd.core;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.franklin.logoutarmycd.util.CalendarUtil;


public class LogoutArmyCDUser implements Serializable {
	private static final long serialVersionUID = 7878523447210580258L;
	private String m_uid = "";
	private Date m_joinDate = null;
	private String m_periodYear = null;
	private String m_periodMonth = null;
	private String m_periodDate = null;
	private int m_reduceDays = 0;
	private int m_otherReduceDays = 0;
	
	public LogoutArmyCDUser(String uid){
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
	 * Get other reduce days.
	 * 
	 * @return
	 */
	public int getOtherReduceDays(){
		return m_otherReduceDays;
	}
	
	/**
	 * Set other reduce days.
	 * 
	 * @param days
	 */
	public void setOtherReduceDays(int days){
		m_otherReduceDays = days;
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
	 * Get the date that the user left the army.
	 * 
	 * @return
	 */
	public Date getEndDate(){
		Calendar c = CalendarUtil.getClearCalendar(m_joinDate);
		// Calculate the end time

		c.add(Calendar.YEAR, new Integer(getPeriodYear()));
		c.add(Calendar.MONTH, new Integer(getPeriodMonth()));
		c.add(Calendar.DATE, new Integer(getPeriodDate()) - getReduceDays() - getOtherReduceDays());
		
		Date endDate = c.getTime();
		// The end time isn't bigger than the join army time.
		if( (endDate.getTime() - m_joinDate.getTime()) >= 0 )
			return endDate;
		
		return m_joinDate;
	}
	
	/**
	 * Get the days of the user left the army.
	 * 
	 * @return
	 */
	public long getLeftDays(){
		if( isNewUser() ){
			return 0;
		}
		return CountdownInfoCalculator.calculateDistanceDays(getEndDate());
	}
	
	/**
	 * Get join the army days.
	 * 
	 * @return
	 */
	public long getJoinDays(){
		if( isNewUser() ){
			return 0;
		}
		return CountdownInfoCalculator.calculateDistanceDays(getJoinDate())*-1;
	}
	
	/**
	 * If the percentage is 100, the user had logout.
	 * 
	 * @return
	 */
	public boolean isLogout(){
		return new Double(getPercentage()).equals(100.0);
	}
	
	/**
	 * Verify a user is a new user.
	 * 
	 * @return
	 */
	public boolean isNewUser(){
		return m_joinDate == null;
	}
	
	/**
	 * Get the percentage of the total duration divide the current duration.
	 * 
	 * @return
	 */
	public double getPercentage(){
		if( isNewUser() ){
			return 0;
		}
		
		return CountdownInfoCalculator.calcultePercentage(m_joinDate, getEndDate());
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
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UserID: ").append(m_uid);
		sb.append(",JoinDate: ").append(m_joinDate != null ? m_joinDate.toString() : null);
		sb.append(",PYear: ").append(m_periodYear);
		sb.append(",PMonth: ").append(m_periodMonth);
		sb.append(",PDay: ").append(m_periodDate);
		sb.append(",RDay: ").append(m_reduceDays);
		sb.append(",RODay: ").append(m_otherReduceDays);
		return sb.toString();
	}
}
