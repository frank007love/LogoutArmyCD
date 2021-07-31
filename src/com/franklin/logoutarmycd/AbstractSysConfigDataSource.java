package com.franklin.logoutarmycd;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ntut.csie.jcis.core.util.CloseStreamUtil;

public abstract class AbstractSysConfigDataSource implements ISysConfigDataSource {
	
	protected Properties properties = null;
	
	final private String APP_URL = "AP_URL";
	final private String APP_ID= "AP_ID";
	final private String API_KEY = "API_KEY";
	final private String FACEBOOK_APPURL = "FACEBOOK_APURL";
	final private String AP_NAME = "AP_NAME";
	final private String SECRET_KEY = "SECRET_KEY";
	final private String FORUM_URL = "FORUM_URL";
	
	protected AbstractSysConfigDataSource(){
		properties = new Properties();
	}
	
	/**
	 * 從檔案中讀取Properties資訊
	 * 
	 * @param filePath
	 */
	protected void loadProperties(String filePath){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			CloseStreamUtil.close(inputStream);
		}
	}
	
	@Override
	public String getApUrl() {
		return properties.getProperty(APP_URL);
	}

	@Override
	public String getAp_id() {
		return properties.getProperty(APP_ID);
	}

	@Override
	public String getApi_key() {
		return properties.getProperty(API_KEY);
	}

	@Override
	public String getFacebookApUrl() {
		return properties.getProperty(FACEBOOK_APPURL);
	}

	@Override
	public String getSecret_key() {
		return properties.getProperty(SECRET_KEY);
	}
	
	@Override
	public String getAP_Name(){
		return properties.getProperty(AP_NAME);
	}
	
	@Override
	public String getForumUrl(){
		return properties.getProperty(FORUM_URL);
	}
}
