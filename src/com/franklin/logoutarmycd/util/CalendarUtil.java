package com.franklin.logoutarmycd.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * Get the date instance by year, month, and date.
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	static public Date getDate(int year, int month, int date){
		Calendar c = Calendar.getInstance();
		c.clear();
	
		c.set(year, month-1, date);
		return c.getTime();
	}
	
	/**
	 * Get the date instance by year, month, and date. {String type}
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	static public Date getDate(String year, String month, String date){
		return getDate(new Integer(year), new Integer(month), new Integer(date));
	}
	
	/**
	 * Get a clear calendar instance and set the date.
	 * 
	 * @param date
	 * @return
	 */
	static public Calendar getClearCalendar(Date date){
		Calendar c = getClearCalendar();
		c.setTime(date);
		return c;
	}
	
	/**
	 * Get a clear calendar instance.
	 * 
	 * @param date
	 * @return
	 */
	static public Calendar getClearCalendar(){
		Calendar c = Calendar.getInstance();
		c.clear();
		return c;
	}
	
	/**
	 * Get the time's year.
	 * 
	 * @param time
	 * @return
	 */
	static public int getYear(Date time){
		Calendar c = getClearCalendar(time);
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * Get the time's month.
	 * 
	 * @param time
	 * @return
	 */
	static public int getMonth(Date time){
		Calendar c = getClearCalendar(time);
		return c.get(Calendar.MONTH);
	}
	
	/**
	 * Get the time's date.
	 * 
	 * @param time
	 * @return
	 */
	static public int getDate(Date time){
		Calendar c = getClearCalendar(time);
		return c.get(Calendar.DATE);
	}
}
