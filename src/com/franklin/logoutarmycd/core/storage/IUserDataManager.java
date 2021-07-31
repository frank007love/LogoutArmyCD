package com.franklin.logoutarmycd.core.storage;

import java.util.List;

import com.franklin.logoutarmycd.core.LogoutArmyCDUser;

public interface IUserDataManager {
	List<LogoutArmyCDUser> getUserList() throws StorageException;
	LogoutArmyCDUser getUser(String aUid) throws StorageException;
	void updateUserData(LogoutArmyCDUser aUser) throws StorageException;
	void deleteUser(String aUid) throws StorageException;
}
