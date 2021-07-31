package com.franklin.logoutarmycd.core;


import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;

public class CountdownInfoCalculatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test calculating the days of leaving the army.
	 * Normal Case.
	 */
	@Test
	public void getLeftTime_normalcase(){
		Date nowDate = new Date();
		long day = 100;
		long duration = CountdownTimer.DY_MIllI * day;
		Date endDate = new Date(nowDate.getTime()+ duration);
		long leftDays = CountdownInfoCalculator.calculateLeftDays(endDate);
		
		assertEquals( day, leftDays, 1);
	}
	
	/**
	 * Test calculating the days of leaving the army.
	 * Before the current time case.
	 */
	@Test
	public void getLeftTime_beforecase(){
		Date nowDate = new Date();
		long day = -100;
		long duration = CountdownTimer.DY_MIllI * day;
		Date endDate = new Date(nowDate.getTime()+ duration);
		long leftDays = CountdownInfoCalculator.calculateLeftDays(endDate);
		
		// Because the end day is front of the current time, the 
		// left days must return zero.
		assertEquals( 0, leftDays);
	}
	
	/**
	 * Test calculating the duration.
	 * Normal case.
	 */
	@Test
	public void calculteDuration_normalcase(){
		Date nowDate = new Date();
		long startDuration = 1000;
		long endDuration = 5000;
		Date startTime = new Date(nowDate.getTime() - startDuration);
		Date endTime = new Date(nowDate.getTime() + endDuration);
		double duration = CountdownInfoCalculator.calculateDuration(startTime, endTime);
		
		assertEquals( startDuration+endDuration, duration, 100 );
	}
	
	/**
	 * Test calculating the duration.
	 * The endTime is the earlier than the start time.
	 */
	@Test
	public void calculteDuration_beforeTheStartTimeCase(){
		Date nowDate = new Date();
		long startDuration = 1000;
		long endDuration = 5000;
		Date startTime = new Date(nowDate.getTime() + startDuration);
		Date endTime = new Date(nowDate.getTime() - endDuration);
		double duration = CountdownInfoCalculator.calculateDuration(startTime, endTime);
		
		assertEquals( 0, duration, 1);
	}
	
	/**
	 * Test the percentage of join duration / total duration.
	 * Normal case.
	 */
	@Test
	public void calcultePercentage_normalcase(){
		Date nowDate = new Date();
		Date startTime = new Date(nowDate.getTime() - 100);
		Date endTime = new Date(nowDate.getTime() + 100);
		double percentage = CountdownInfoCalculator.calcultePercentage(startTime, endTime);
		
		assertEquals( 50, percentage, 1 );
	}
	
	/**
	 * Test the percentage of join duration / total duration.
	 * The start time is equal to the end time case.
	 */
	@Test
	public void calcultePercentage_startEqualend(){
		Date nowDate = new Date();
		Date startTime = new Date(nowDate.getTime() - 100);
		Date endTime = startTime;
		double percentage = CountdownInfoCalculator.calcultePercentage(startTime, endTime);
		
		assertEquals( 100, percentage, 0 );
	}
	
	/**
	 * Test the percentage of join duration / total duration.
	 * The start time is after the end time case.
	 */
	@Test
	public void calcultePercentage_startAfterlend(){
		Date nowDate = new Date();
		Date startTime = new Date(nowDate.getTime() - 100);
		Date endTime = nowDate;
		double percentage = CountdownInfoCalculator.calcultePercentage(startTime, endTime);
		
		assertEquals( 100, percentage, 0 );
	}

	@Test
	public void calculateOverDays(){
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2010, 11, 10);
		Date nowDate = new Date();
		Date fixedDate = c.getTime();
		System.out.println(fixedDate.toLocaleString());
		long overDays = CountdownInfoCalculator.calculateDistanceDays(fixedDate);
		System.out.println(overDays);
	}
}
