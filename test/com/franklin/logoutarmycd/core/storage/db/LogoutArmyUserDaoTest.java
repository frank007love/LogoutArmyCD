package com.franklin.logoutarmycd.core.storage.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.storage.IUserDataManager;
import com.franklin.logoutarmycd.core.storage.StorageException;

public class LogoutArmyUserDaoTest {

	private static IUserDataManager logoutArmyUserDao = null;
	private List<LogoutArmyCDUser> mAddedArmyCDUserList = new ArrayList<LogoutArmyCDUser>();
	
	@BeforeClass
	static public void classSetUp() throws Exception {
		logoutArmyUserDao = LogoutArmyUserDao.getInstance();
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		for( LogoutArmyCDUser user : mAddedArmyCDUserList ){
			logoutArmyUserDao.deleteUser(user.getUID());
		}
	}
	
	@Test
	public void updateUser() throws StorageException{
		LogoutArmyCDUser user = new LogoutArmyCDUser("123456");
		addUserNormal(user);
		
		user = logoutArmyUserDao.getUser("123456");
		assertNotNull(user);
		assertNull(user.getJoinDate());
		assertEquals(0, user.getReduceDays());
		assertEquals(0, user.getOtherReduceDays());
		
		Calendar newTimeCalendar = Calendar.getInstance();
		newTimeCalendar.clear();
		newTimeCalendar.set(Calendar.YEAR, 2012);
		newTimeCalendar.set(Calendar.MONTH, 3);
		newTimeCalendar.set(Calendar.DATE, 5);
		Date newTime = newTimeCalendar.getTime();
		
		user.setJoinDate(newTime);
		user.setPeriodYear("1");
		user.setPeriodMonth("0");
		user.setPeriodDate("0");
		user.setReduceDays(30);
		user.setOtherReduceDays(15);
		
		// update data
		try {
			logoutArmyUserDao.updateUserData(user);
		} catch (StorageException e) {
			e.printStackTrace();
			fail();
		}
		
		// verify
		user = logoutArmyUserDao.getUser("123456");
		Date newTimeFromDB = user.getJoinDate();
		assertEquals(newTime.getTime(), newTimeFromDB.getTime());
	}

	@Test
	public void addNullUser(){
		addUserException(null);
	}
	
	private void addUserException(LogoutArmyCDUser aUser){
		try {
			logoutArmyUserDao.updateUserData(aUser);
			fail();
		} catch (StorageException e) {
			// ok
		}
	}
	
	private void addUserNormal(LogoutArmyCDUser aUser){
		try {
			logoutArmyUserDao.updateUserData(aUser);
			mAddedArmyCDUserList.add(aUser);
		} catch (StorageException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void addUser() throws StorageException{
		String idPrefix = "100";
		for( int i = 0 ; i < 10 ; i++ ){
			LogoutArmyCDUser user = new LogoutArmyCDUser(idPrefix+i);	
			addUserNormal(user);
		}

		for( int i = 0 ; i < 10 ; i++ ){
			assertNotNull( logoutArmyUserDao.getUser(idPrefix+i));
		}
	}
	
	@Test
	//@Ignore
	public void testMultiThreadOperation() throws InterruptedException{
		List<GetUserJob> getUserJobList = new ArrayList<LogoutArmyUserDaoTest.GetUserJob>();
		List<Thread> getUserJobThreadList = new ArrayList<Thread>();
		for( int i = 0 ; i < 5 ; i++ ){
			GetUserJob job = new GetUserJob();
			getUserJobList.add(job);
			Thread thread = new Thread(job);
			getUserJobThreadList.add(thread);
			thread.start();
		}
		
		for( Thread t : getUserJobThreadList ){
			t.join();
		}
	}
	
	class GetUserJob implements Runnable{

		@Override
		public void run() {
			IUserDataManager logoutArmyUserDao = LogoutArmyUserDao.getInstance();
			while( true ){
				try {
					System.out.println(Thread.currentThread().getName()+":"+logoutArmyUserDao.getUserList().size());
				} catch (StorageException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
