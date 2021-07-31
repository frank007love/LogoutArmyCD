package com.franklin.logoutarmycd.core;

import java.util.Calendar;
import java.util.Date;

import com.franklin.logoutarmycd.core.CountdownTimer.CountdownType;
import com.franklin.logoutarmycd.util.CalendarUtil;

public class CountdownInfoCalculator {

	/**
	 * Calculate the duration time, if the duration smaller than zero,
	 * the method will return zero.
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	static public double calculateDuration(Date startTime, Date endTime){
		long duration = endTime.getTime() - startTime.getTime();
		return duration < 0 ? 0 : duration;
	}
	
	/**
	 * Calculate the user's army percentage.
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	static public double calcultePercentage(Date startTime, Date endTime){
		Date nowTime = new Date();
		double result = 0;
		// the user had left the amry.
		if( nowTime.after(endTime) ){
			return 100;
		}
		
		// calculate each duration
		double totalDuration = calculateDuration( startTime, endTime);
		double currentDuration = calculateDuration( startTime, nowTime);
		
		// if the totalDuration is smaller than zero, it
		// presents the user hasn't join the army.
		if( totalDuration != 0 ){
			result = (currentDuration / totalDuration)*100;
		}
		return result < 0 ? 0 : result;
	}
	
	/**
	 * 計算剩餘的天數
	 * 
	 * @param endTime
	 * @return
	 */
	static public long calculateLeftDays(Date endTime){
		Calendar c = CalendarUtil.getClearCalendar(endTime);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		CountdownTimer timer = new CountdownTimer( year, month, date);
		return timer.getLeftTime(CountdownType.DAY);
	}
	
	/**
	 * Calculate the days distance for the fixedTime.
	 * 
	 * @param fixedTime
	 * @return
	 */
	static public long calculateDistanceDays(Date fixedTime){
		Calendar c = CalendarUtil.getClearCalendar(fixedTime);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		CountdownTimer timer = new CountdownTimer( year, month, date);
		return timer.getDistanceTime(CountdownType.DAY);
	}
}
