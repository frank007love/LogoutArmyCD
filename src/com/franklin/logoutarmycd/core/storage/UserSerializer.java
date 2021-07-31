package com.franklin.logoutarmycd.core.storage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.franklin.logoutarmycd.SystemConfig;
import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.LogoutArmyCDUserList;
import com.franklin.logoutarmycd.util.Serilizer;

public class UserSerializer implements IUserDataManager {
	private LogoutArmyCDUserList m_userList = null;
	final private String FILE_NAME = "userinfo";
	static private IUserDataManager m_instance = null;
	
	private UserSerializer(){
		load();
	}
	
	static public synchronized IUserDataManager getInstance(){
		if( m_instance == null ){
			m_instance = new UserSerializer();
		}
		return m_instance;
	}
	
	/**
	 * Load user info.
	 */
	private void load(){
		String filePath = getFilePath();
		File file = new File(filePath);

		try {
			m_userList = (LogoutArmyCDUserList)Serilizer.load(file);
		} catch (IOException e) {
			m_userList = new LogoutArmyCDUserList();
		}
	}
	
	/**
	 * Get the storage file path.
	 * 
	 * @return
	 */
	private String getFilePath(){
		String metadataPath = SystemConfig.getMetadataPath();
		StringBuffer buffer = new StringBuffer(metadataPath);
		buffer.append( File.separatorChar );
		buffer.append( FILE_NAME );
		return buffer.toString();
	}
	
	@Override
	public synchronized LogoutArmyCDUser getUser(String uid) {
		for( LogoutArmyCDUser user : m_userList ){
			if( user.getUID().equals(uid) ){
				return user;
			}
		}
		return null;
	}

	@Override
	public synchronized List<LogoutArmyCDUser> getUserList() {
		return m_userList;
	}
	
	public synchronized void removeUser(String uid){
		LogoutArmyCDUser user = getUser(uid);
		m_userList.remove(user);
	}

	@Override
	public synchronized void updateUserData(LogoutArmyCDUser user) {
		removeUser(user.getUID());
		m_userList.add(user);
		
		String filePath = getFilePath();
		File file = new File(filePath);
		Serilizer.store(m_userList, file);
	}

	@Override
	public void deleteUser(String aUid) throws StorageException {
		throw new RuntimeException("Not implement.");
	}

}
