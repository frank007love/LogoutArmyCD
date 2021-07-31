package com.franklin.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.franklin.logoutarmycd.SystemConfig;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.google.code.facebookapi.FacebookException;

import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;

public class FacebookController {

	private HttpServletRequest m_request = null;
	private HttpServletResponse m_response = null;
	private SystemConfig m_systemConfig = null;
	
	private FacebookClient mFacebookClient = null;
	
	protected FacebookController(HttpServletRequest request, HttpServletResponse response){
		m_request = request;
		m_response = response;
		m_systemConfig = SystemConfig.getInstance();
		
		mFacebookClient = new DefaultFacebookClient((String)SessionUtil.getAttribute(m_request, SessionKeyEnum.ACCESS_TOKEN));
	}
	
	static public FacebookController init(HttpServletRequest request, HttpServletResponse response){
		return new FacebookController(request, response);
	}
	
	public void setHttpServletData(HttpServletRequest request, HttpServletResponse response){
		m_request = request;
		m_response = response;
	}
	
	/**
	 * Get login user.
	 * 
	 * @return 
	 */
	public User getLoginUser(){
		
		User loginUser = null;
		try {
			loginUser = mFacebookClient.fetchObject("me", User.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return loginUser;
	}
	
	/**
	 * Get friend List.
	 * 
	 * @return friend list
	 */
	public List<User> getFriendList(){
		Connection<User> myFriends = mFacebookClient.fetchConnection("me/friends", User.class);
		return myFriends.getData();
	}
	
	/**
	 * Get friend id List.
	 * 
	 * @return friend id list
	 */
	public List<Long> getFriendIdList(){
		List<User> myFriends = getAppUserFriendList();
		List<Long> friendIDList = new ArrayList<Long>(); 
		for( User friend :  myFriends ){
			friendIDList.add(Long.valueOf(friend.getId()));
		}
		return friendIDList;
	}
	
	/**
	 * Get friend List.
	 * 
	 * @return friend list
	 */
	public List<User> getAppUserFriendList(){
		Map<String, User> friendMap = getUserFriendMap();
		List<User> appFriendList = new ArrayList<User>();
		List<Long> appFriendIDList = null;
		try {
			appFriendIDList = getAppUserFriendIdList();
		} catch( FacebookException e ){
			throw new RuntimeException(e);
		}
		for( Long uid : appFriendIDList){
			User user = friendMap.get(String.valueOf(uid));
			if( user != null )
				appFriendList.add(user);
		}
		return appFriendList;
	}
	
	/**
	 * Get the user's friend map.
	 * uid vs user instance
	 * 
	 * @return
	 */
	public Map<String, User> getUserFriendMap(){
		List<User> userList = getFriendList();
		Map<String, User> userMap = new HashMap<String, User>();
		for( User user : userList ){
			userMap.put(user.getId().toString(), user);
		}
		return userMap;
	}
	
	/**
	 * Get the app user's friend map.
	 * uid vs user instance
	 * 
	 * @return
	 */
	public Map<String, User> getAppUserFriendMap(){
		List<User> userList = getAppUserFriendList();
		Map<String, User> userMap = new HashMap<String, User>();
		for( User user : userList ){
			userMap.put(user.getId().toString(), user);
		}
		return userMap;
	}
	
	/**
	 * Get application friend list.
	 * 
	 * @return
	 * @throws FacebookException 
	 */
	public List<Long> getAppUserFriendIdList() throws FacebookException{
		Connection<JsonObject> myAPPFriends  = null;

		try {
			myAPPFriends = mFacebookClient.fetchConnection(
				"me/friends", JsonObject.class,
				Parameter.with("fields", "installed"));
		} catch( Exception e ){
			throw new FacebookException(-1, e.getMessage());
		}
		
		List<Long> appUserIDList = new ArrayList<Long>();
		for( JsonObject obj : myAPPFriends.getData() ){
			try {
				obj.get("installed");
				String id = (String)obj.get("id");
				appUserIDList.add(Long.valueOf(id));
			} catch( Exception e ){
				//ignore
			}
		}
		return appUserIDList;
	}
	
	public Long getLoginUserId(){
		return Long.valueOf(getLoginUser().getId());
	}
	
	/**
	 * �N�T���o����Facebook Wall.
	 * 
	 * @param message
	 */
	public void postMessageToWall(String message){
		throw new RuntimeException("Not support!");
	}
}
