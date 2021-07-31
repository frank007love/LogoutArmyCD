package com.franklin.logoutarmycd;

public class SysConfigDataSourceFactory {

	final private String TEST_MODE = "TEST_MODE";
	final private String MORMAL_MODE = "NORMAL_MODE";
	
	private SysConfigDataSourceFactory(){
		
	}
	
	/**
	 * 取得SysConfigDataSourceFactory的instance
	 * 
	 * @return
	 */
	static public SysConfigDataSourceFactory getInstance(){
		return new SysConfigDataSourceFactory();
	}
	
	
	
	/**
	 * 根據AP執行模式決定產生的data source
	 * 
	 * @return
	 */
	public ISysConfigDataSource createSysConfigDataSource(){
		ISysConfigDataSource dataSource = null;
		String mode = SystemConfig.getAppMode();
		
		// 確認運行的模式
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
