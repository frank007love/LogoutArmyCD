package com.franklin.logoutarmycd.tool;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.logoutarmycd.SystemConfigEnum;
import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.storage.IUserDataManager;
import com.franklin.logoutarmycd.core.storage.StorageException;
import com.franklin.logoutarmycd.core.storage.UserDataManagerFactory;
import com.franklin.logoutarmycd.core.storage.db.LogoutArmyUserDao;

public class UserConfigImporter {
	static private Logger logger = LoggerFactory.getLogger(UserConfigImporter.class);
	private IUserDataManager mDBUserDataManager = null;
	private IUserDataManager mConfigFileDataManager = null;
	
	public UserConfigImporter(){
		mDBUserDataManager = UserDataManagerFactory.createUserDataManager();
		mConfigFileDataManager = UserDataManagerFactory.createUserSerializer();
	}
	
	public IUserDataManager getConfigFileDataManager(){
		return mConfigFileDataManager;
	}
	
	public IUserDataManager getDBUserDataManager(){
		return mDBUserDataManager;
	}
	
	public void clearDB() throws StorageException, InterruptedException{
		List<LogoutArmyCDUser> userList = mDBUserDataManager.getUserList();
		logger.info("Start to clear user data in Database...");

		Map<Integer, List<LogoutArmyCDUser>>  groupUserDataMap = groupUserData(userList);
		
		List<Thread> deleteUserDataThread = new ArrayList<Thread>();
		for( int i = 0 ; i < 10 ; i++ ){
			System.out.println("User table " + i + " size = " + groupUserDataMap.get(i).size());
			deleteUserDataThread.add(new Thread(new DeleteUserDataJob(groupUserDataMap.get(i), i)));
		}
		
		for( Thread thread : deleteUserDataThread ){
			thread.start();
		}
		
		for( Thread thread : deleteUserDataThread ){
			thread.join();
		}
		
		logger.info("Total delete user(s): {}", userList.size());
	}
	
	public void adjustData() throws StorageException{
		List<LogoutArmyCDUser> userList = mConfigFileDataManager.getUserList();
		logger.info("Start to clear user data in Database...");
		for( LogoutArmyCDUser user : userList ){
			user.setPeriodYear(adjustVal(user.getPeriodYear(), "1"));
			user.setPeriodMonth(adjustVal(user.getPeriodMonth(), "0"));
			user.setPeriodDate(adjustVal(user.getPeriodDate(), "0"));
		}
	}
	
	private String adjustVal(String aOldData, String aReplaceData){
		if( aOldData == null ){
			return aOldData;
		}
		
		int newVal = 0;
		try {
			Integer.valueOf(aReplaceData);
		} catch( NumberFormatException e ){
			throw new RuntimeException("ReplaceData is invalid.");
		}
		
		try {
			newVal = Integer.valueOf(aOldData);
		} catch( NumberFormatException e ){
			logger.warn("Replace olddata {} to new {}", aOldData, aReplaceData);
		}
		if( newVal < 0 ){
			return aReplaceData;
		}
		return aOldData;
	}
	
	private Map<Integer, List<LogoutArmyCDUser>> groupUserData(List<LogoutArmyCDUser> aUserList){
		Map<Integer, List<LogoutArmyCDUser>> groupUserDataMap = new HashMap<Integer, List<LogoutArmyCDUser>>();
		for( int i = 0 ; i < 10 ; i++ ){
			groupUserDataMap.put(i, new ArrayList<LogoutArmyCDUser>());
		}
		for( LogoutArmyCDUser lacdUser : aUserList ){
			int index = LogoutArmyUserDao.getIndex(lacdUser.getUID());
			groupUserDataMap.get(index).add(lacdUser);
		}
		return groupUserDataMap;
	}
	
	public void execute() throws Exception {
		clearDB();
		adjustData();
		Date start = new Date();
		
		List<LogoutArmyCDUser> userList = mConfigFileDataManager.getUserList();
		Map<Integer, List<LogoutArmyCDUser>>  groupUserDataMap = groupUserData(userList);
		
		List<Thread> importUserDataThread = new ArrayList<Thread>();
		for( int i = 0 ; i < 10 ; i++ ){
			System.out.println("User table " + i + " size = " + groupUserDataMap.get(i).size());
			importUserDataThread.add(new Thread(new ImportUserDataJob(groupUserDataMap.get(i), i)));
		}
		
		for( Thread thread : importUserDataThread ){
			thread.start();
		}
		
		for( Thread thread : importUserDataThread ){
			thread.join();
		}
		
		Date end = new Date();
		long duration = end.getTime() - start.getTime();
		logger.info("Total add user(s): {}, duration: {}s", userList.size(), duration/1000);
	}
	
	class ImportUserDataJob implements Runnable {
		private List<LogoutArmyCDUser> mUserList = null;
		private int mIndex;
		
		public ImportUserDataJob(List<LogoutArmyCDUser> aUserList, int aIndex){
			mUserList = aUserList;
			mIndex = aIndex;
		}
	
		@Override
		public void run() {
			IUserDataManager DBUserDataManager = UserDataManagerFactory.createUserDataManager();
			int count = 0;
			for( LogoutArmyCDUser user : mUserList ){
				try {
					DBUserDataManager.updateUserData(user);
				} catch( Exception e ){
					logger.error("Add user {} data failed: {}", user.getUID(), e.getMessage());
				}
				count++;
				if( (count % 100) == 0 ){
					logger.info("Table " + mIndex + " complate " + count);
				}
			}
		}
	}
	
	
	class DeleteUserDataJob implements Runnable {
		private List<LogoutArmyCDUser> mUserList = null;
		private int mIndex;
		
		public DeleteUserDataJob(List<LogoutArmyCDUser> aUserList, int aIndex){
			mUserList = aUserList;
			mIndex = aIndex;
		}
	
		@Override
		public void run() {
			IUserDataManager DBUserDataManager = UserDataManagerFactory.createUserDataManager();
			int count = 0;
			for( LogoutArmyCDUser user : mUserList ){
				try {
					DBUserDataManager.deleteUser(user.getUID());
				} catch( Exception e ){
					logger.error("Delete user {} data failed: {}", user.getUID(), e.getMessage());
				}
				count++;
				if( (count % 100) == 0 ){
					logger.info("Table " + mIndex + " complate " + count);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			System.setProperty( SystemConfigEnum.SYS_METADATA_PATH, "./WebContent/_metadata");
			
			UserConfigImporter importer = new UserConfigImporter();
			importer.execute();
			System.exit(0);
		} catch( Throwable e ){
			logger.error("Import user config to db failed.", e);
			System.exit(2);
		}
	}

}
