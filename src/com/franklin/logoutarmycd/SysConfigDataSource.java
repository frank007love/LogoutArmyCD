package com.franklin.logoutarmycd;

import java.io.File;

public class SysConfigDataSource extends AbstractSysConfigDataSource {

	private String fileName = "sys.config";
	
	private SysConfigDataSource(){
		String metadata = SystemConfig.getMetadataPath();
		String filePath = metadata + File.separator + fileName;
		loadProperties(filePath);
	}
	
	static public ISysConfigDataSource createDataSource(){
		return new SysConfigDataSource();
	}

}
