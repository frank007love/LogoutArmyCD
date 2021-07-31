package com.franklin.facebook;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.logoutarmycd.web.util.CacheObject;
import com.google.code.facebookapi.FacebookException;
//import com.google.code.facebookapi.schema.User;
import com.restfb.types.User;

public class FacebookControllerProxy extends FacebookController{

	private long EXPIRED_TIME = 120 * 1000; 
	
	private CacheObject mAppUserFriendIdList = null;
	private CacheObject mAppUserFriendList = null;
	private CacheObject mAppUserFriendMap = null;
	private CacheObject mLoginUser = null;
	
	private CacheObject mFriendList = null;
	private CacheObject mFriendIdList= null;
	
	private Logger logger = LoggerFactory.getLogger(FacebookControllerProxy.class);
	
	protected FacebookControllerProxy(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	static public FacebookController init(HttpServletRequest request, HttpServletResponse response){
		return new FacebookControllerProxy(request, response);
	}
	
	@Override
	public User getLoginUser(){
		if( mLoginUser == null ||
				mLoginUser.isExpired() ){
			logger.debug("Get facebook login user from api.");
			mLoginUser = CacheObject.createCacheObject(super.getLoginUser(), EXPIRED_TIME);
		} else {
			logger.debug("Get facebook login user from cache.");	
		}
		return (User)mLoginUser.getCacheObject();
	}
	
	/**
	 * Get facebook app friend list from cache. If the stamp is expired, 
	 * get the list by api.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getAppUserFriendIdList() throws FacebookException {
		if( mAppUserFriendIdList == null ||
				mAppUserFriendIdList.isExpired() ){
			logger.debug("Get facebook app friend id list from api.");
			mAppUserFriendIdList = CacheObject.createCacheObject(super.getAppUserFriendIdList(), EXPIRED_TIME);
		} else {
			logger.debug("Get facebook app friend id list from cache.");	
		}
		return (List<Long>)mAppUserFriendIdList.getCacheObject();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAppUserFriendList() {
		if( mAppUserFriendList == null ||
				mAppUserFriendList.isExpired() ){
			logger.debug("Get facebook app friend list from api.");
			mAppUserFriendList = CacheObject.createCacheObject(super.getAppUserFriendList(), EXPIRED_TIME);
		} else {
			logger.debug("Get facebook app friend list from cache.");	
		}
		return (List<User>)mAppUserFriendList.getCacheObject();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, User> getAppUserFriendMap() {
		if( mAppUserFriendMap == null ||
				mAppUserFriendMap.isExpired() ){
			logger.debug("Get facebook app friend map from api.");
			mAppUserFriendMap = CacheObject.createCacheObject(super.getAppUserFriendMap(), EXPIRED_TIME);
		} else {
			logger.debug("Get facebook app friend map from cache.");	
		}
		return (Map<String, User>)mAppUserFriendMap.getCacheObject();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFriendList() {
		if( mFriendList == null ||
				mFriendList.isExpired() ){
			logger.debug("Get facebook app friend list from api.");
			mFriendList = CacheObject.createCacheObject(super.getFriendList(), EXPIRED_TIME);
		} else {
			logger.debug("Get facebook app friend list from cache.");	
		}
		return (List<User>)mFriendList.getCacheObject();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getFriendIdList() {
		if( mFriendList == null ||
				mFriendIdList.isExpired() ){
			logger.debug("Get facebook app friend list from api.");
			mFriendIdList = CacheObject.createCacheObject(super.getFriendIdList(), EXPIRED_TIME);
		} else {
			logger.debug("Get facebook app friend list from cache.");	
		}
		return (List<Long>)mFriendIdList.getCacheObject();
	}
}
