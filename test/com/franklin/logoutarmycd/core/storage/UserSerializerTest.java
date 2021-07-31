package com.franklin.logoutarmycd.core.storage;

import java.util.List;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.franklin.logoutarmycd.SystemConfigEnum;
import com.franklin.logoutarmycd.core.LogoutArmyCDUser;

public class UserSerializerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLoadFile(){
		System.setProperty(SystemConfigEnum.SYS_METADATA_PATH, "./testdata");
		IUserDataManager us = UserSerializer.getInstance();
		try {
			LogoutArmyCDUser user = us.getUser("100001917998807");
			//System.out.println(user.getJoinDays());
			System.out.println(user.getJoinDate());
			System.out.println(user.getEndDate());
			System.out.println(user.getPeriodYear());
			System.out.println(user.getPeriodMonth());
			System.out.println(user.getPeriodDate());
			System.out.println(user.getReduceDays());

		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		
	}

}
