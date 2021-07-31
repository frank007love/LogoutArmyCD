package com.franklin.logoutarmycd;

public class SysConfigDataSourceFactory {

	final private String TEST_MODE = "TEST_MODE";
	final private String MORMAL_MODE = "NORMAL_MODE";
	
	private SysConfigDataSourceFactory(){
		
	}
	
	/**
	 * ���oSysConfigDataSourceFactory��instance
	 * 
	 * @return
	 */
	static public SysConfigDataSourceFactory getInstance(){
		return new SysConfigDataSourceFactory();
	}
	
	
	
	/**
	 * �ھ�AP����Ҧ��M�w���ͪ�data source
	 * 
	 * @return
	 */
	public ISysConfigDataSource createSysConfigDataSource(){
		ISysConfigDataSource dataSource = null;
		String mode = SystemConfig.getAppMode();
		
		// �T�{�B�檺�Ҧ�
		if( mode.equals(TEST_MODE) ){
			dataSource = TestSysConfigDataSource.createDataSource();
		} else if( mode.equals(MORMAL_MODE) ){
			dataSource = SysConfigDataSource.createDataSource();
		} else {
			dataSource = SysConfigDataSource.createDataSource();
		}
		
		return dataSource;
	}
}
