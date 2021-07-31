package com.franklin.logoutarmycd.core;

import java.util.Calendar;
import java.util.Date;

public class CountdownTimer {

	public enum CountdownType{
		SECOND, DAY 
	};
	
	static final public long SEC_MILLI = 1000;
	static final public long MIN_MIllI = 60 * SEC_MILLI;
	static final public long HR_MIllI = 60 * MIN_MIllI;
	static final public long DY_MIllI = 24 * HR_MIllI;
	private Date m_endDate = null;
	
	public CountdownTimer(int year, int month, int date){
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(year, month, date);
		m_endDate = c.getTime();
	}
	
	/**
	 * �NMilliSeconds�ର���
	 * 
	 * @param milliSeconds
	 * @return
	 */
	private long milliSecondToSeconds(long milliSeconds){
		return milliSeconds / SEC_MILLI;
	}
	
	/**
	 * �NMilliSeconds�ର�Ѽ�
	 * 
	 * @param milliSeconds
	 * @return
	 */
	private long milliSecondToDays(long milliSeconds){
		return milliSeconds / DY_MIllI;
	}
	
	/**
	 * ���o�Ѿl�ɶ�
	 * 
	 * @param type
	 * @return
	 */
	public long getLeftTime(CountdownType type){
		Date now = new Date();
		long left = m_endDate.getTime() - now.getTime();
		
		// �w�W�L�����ɶ�
		if( left <= 0 ){
			left = 0;
		}
		
		// �Nmilli seconds�নclient�n�����
		if( type.equals(CountdownType.SECOND) ){
			left = milliSecondToSeconds(left);
		} else if( type.equals(CountdownType.DAY) ) {
			left = milliSecondToDays(left);
		}
		
		return left;
	}
	
	/**
	 * ���o�Z�����ɶ�
	 * 
	 * @param type
	 * @return
	 */
	public long getDistanceTime(CountdownType type){
		Date now = new Date();
		long left = m_endDate.getTime() - now.getTime();
		
		// �Nmilli seconds�নclient�n�����
		if( type.equals(CountdownType.SECOND) ){
			left = milliSecondToSeconds(left);
		} else if( type.equals(CountdownType.DAY) ) {
			left = milliSecondToDays(left);
		}
		
		return left;
	}
}
