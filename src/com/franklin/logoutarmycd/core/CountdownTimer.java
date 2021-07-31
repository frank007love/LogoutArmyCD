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
	 * 將MilliSeconds轉為秒數
	 * 
	 * @param milliSeconds
	 * @return
	 */
	private long milliSecondToSeconds(long milliSeconds){
		return milliSeconds / SEC_MILLI;
	}
	
	/**
	 * 將MilliSeconds轉為天數
	 * 
	 * @param milliSeconds
	 * @return
	 */
	private long milliSecondToDays(long milliSeconds){
		return milliSeconds / DY_MIllI;
	}
	
	/**
	 * 取得剩餘時間
	 * 
	 * @param type
	 * @return
	 */
	public long getLeftTime(CountdownType type){
		Date now = new Date();
		long left = m_endDate.getTime() - now.getTime();
		
		// 已超過結束時間
		if( left <= 0 ){
			left = 0;
		}
		
		// 將milli seconds轉成client要的單位
		if( type.equals(CountdownType.SECOND) ){
			left = milliSecondToSeconds(left);
		} else if( type.equals(CountdownType.DAY) ) {
			left = milliSecondToDays(left);
		}
		
		return left;
	}
	
	/**
	 * 取得距離的時間
	 * 
	 * @param type
	 * @return
	 */
	public long getDistanceTime(CountdownType type){
		Date now = new Date();
		long left = m_endDate.getTime() - now.getTime();
		
		// 將milli seconds轉成client要的單位
		if( type.equals(CountdownType.SECOND) ){
			left = milliSecondToSeconds(left);
		} else if( type.equals(CountdownType.DAY) ) {
			left = milliSecondToDays(left);
		}
		
		return left;
	}
}
