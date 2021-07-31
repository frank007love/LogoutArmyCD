package com.franklin.logoutarmycd;

public class SystemConfig {

	static private SystemConfig m_sys_config = null;
	private ISysConfigDataSource m_dataSource = null;
	private CommonSysConfig m_commonConfig = null;
	
	private SystemConfig(){
		m_commonConfig = CommonSysConfig.createCommonSysConfig();
		SysConfigDataSourceFactory factory = SysConfigDataSourceFactory.getInstance();
		m_dataSource = factory.createSysConfigDataSource();
	}
	
	/**
	 * Get system config instance.
	 * 
	 * @return
	 */
	static public SystemConfig getInstance(){
		if( m_sys_config == null )
			m_sys_config = new SystemConfig();
		return m_sys_config;
	}
	
	/**
	 * Get the api key.
	 * 
	 * @return
	 */
	public String getApi_key(){
		return m_dataSource.getApi_key();
	}
	
	/**
	 * Get the secret key.
	 * 
	 * @return
	 */
	public String getSecret_key(){
		return m_dataSource.getSecret_key();
	}
	
	/**
	 * Get the ap id.
	 * 
	 * @return
	 */
	public String getAp_id(){
		return m_dataSource.getAp_id();
	}
	
	/**
	 * Get the facebook ap url.
	 * 
	 * @return
	 */
	public String getFacebookApUrl(){
		return m_dataSource.getFacebookApUrl();
	}
	
	/**
	 * Get the ap url.
	 * 
	 * @return
	 */
	public String getApUrl(){
		return m_dataSource.getApUrl();
	}
	
	/**
	 * Get the forum url.
	 * 
	 * @return
	 */
	public String getForumUrl(){
		return m_dataSource.getForumUrl();
	}
	
	/**
	 * Get the app's name.
	 * 
	 * @return
	 */
	public String getAP_Name(){
		return m_dataSource.getAP_Name();
	}
	
	public String getAuthorizeUrl(){
		String url = "https://graph.facebook.com/oauth/authorize?client_id=" + getAp_id() + 
		"&redirect_uri=" + getApUrl() + "authorize.jsp"+
		"&scope=user_photos,read_stream,email,publish_stream,user_website";
		return url;
	}
	
	static public String getAppMode(){
		return System.getProperty(SystemConfigEnum.AP_MODE);
	}
	
	/**
	 * Get system metadata path.
	 * 
	 * @return
	 */
	static public String getMetadataPath(){
		return System.getProperty(SystemConfigEnum.SYS_METADATA_PATH);
	}
}
