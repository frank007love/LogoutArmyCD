package com.franklin.logoutarmycd;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import ntut.csie.jcis.core.util.CloseStreamUtil;


public class CommonSysConfig {

	final String FILENAME = "common_sys.config";
	private Properties m_commonProperties = null;
	
	private String APP_MODE = "APP_MODE";
	
	private CommonSysConfig(){
		loadProperties();
		doSetting();
	}

	/**
	 * Set the properties to system.
	 */
	private void doSetting(){
		String mode = getAPP_MODE();
		System.setProperty(SystemConfigEnum.AP_MODE, mode);
	}
	
	/**
	 * Load the common properties setting from file.
	 */
	private void loadProperties(){
		String configFilePath = getConfigFilePath(); 
		File file = new File(configFilePath);
		m_commonProperties = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			m_commonProperties.load(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			CloseStreamUtil.close(input);
		}
	}
	
	/**
	 * Get the app running mode.
	 * 
	 * @return
	 */
	public String getAPP_MODE(){
		return m_commonProperties.getProperty(APP_MODE);
	}
	
	/**
	 * Create the CommonSysConfig instance.
	 * 
	 * @return
	 */
	static public CommonSysConfig createCommonSysConfig(){
		return new CommonSysConfig();
	}
	
	/**
	 * Get metadata path
	 * 
	 * @return
	 */
	private String getConfigFilePath(){
		String metadataPath = SystemConfig.getMetadataPath();
		return metadataPath + File.separator + FILENAME;
	}
}
