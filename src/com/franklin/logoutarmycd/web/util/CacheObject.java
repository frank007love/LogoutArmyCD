package com.franklin.logoutarmycd.web.util;

import java.util.Date;

public class CacheObject {
	static private long DefaultExpiredTime = 120 * 1000;
	
	private Object mCacheObject = null;
	private Date mTimeStamp = null;
	private long mExpiredTime = DefaultExpiredTime;
	
	public CacheObject(){
		
	}
	
	static public CacheObject createCacheObject(Object aObject){
		CacheObject cacheObj = new CacheObject();
		cacheObj.update(aObject);
		cacheObj.setExpiredTime(DefaultExpiredTime);
		return cacheObj; 
	}
	
	static public CacheObject createCacheObject(Object aObject, long aExpiredTime){
		CacheObject cacheObj = new CacheObject();
		cacheObj.update(aObject);
		cacheObj.setExpiredTime(aExpiredTime);
		return cacheObj; 
	}
	
	public void update(Object aObject){
		mCacheObject = aObject;
		mTimeStamp = new Date();
	}
	
	public void setExpiredTime(long aExpiredTime){
		mExpiredTime = aExpiredTime;
	}
	
	public Object getCacheObject(){
		return mCacheObject;
	}
	
	public boolean isExpired(){ 
		Date now = new Date();
		return (now.getTime() - mTimeStamp.getTime()) > mExpiredTime;
	}
}
