package com.franklin.logoutarmycd.tool;

import org.junit.Test;
import static org.junit.Assert.*;

import com.franklin.logoutarmycd.SystemConfigEnum;
import com.franklin.logoutarmycd.core.storage.StorageException;


public class UserConfigImporterTest {

	@Test
	public void execute(){
		System.setProperty(SystemConfigEnum.SYS_METADATA_PATH, "testdata");
		
		UserConfigImporter importer = new UserConfigImporter();
		
		try {
			int configFileUserSize = importer.getConfigFileDataManager().getUserList().size();
			importer.execute();
			assertEquals(configFileUserSize, importer.getDBUserDataManager().getUserList().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void clear(){
		System.setProperty(SystemConfigEnum.SYS_METADATA_PATH, "testdata");
		
		UserConfigImporter importer = new UserConfigImporter();
		try {
			importer.clearDB();
			assertEquals(0, importer.getDBUserDataManager().getUserList().size());
		} catch (Exception e) {
			fail();
		}
	}
}
