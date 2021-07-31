package com.franklin.logoutarmycd;

import java.io.File;

public class TestSysConfigDataSource extends AbstractSysConfigDataSource {
	
	private String fileName = "testsys.config";
	
	private TestSysConfigDataSource(){
		String metadata = SystemConfig.getMetadataPath();
		String filePath = metadata + File.separator + fileName;
		loadProperties(filePath);
	}
	
	static public ISysConfigDataSource createDataSource(){
		return new TestSysConfigDataSource();
	}
	


}
