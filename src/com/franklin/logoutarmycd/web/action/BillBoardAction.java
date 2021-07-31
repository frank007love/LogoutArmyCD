package com.franklin.logoutarmycd.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntut.csie.jcis.core.util.DateUtil;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.facebook.FacebookController;
import com.franklin.logoutarmycd.core.AbstractCDUserComparable;
import com.franklin.logoutarmycd.core.AbstractCDUserComparableFactory;
import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.LogoutArmyCountdownManaer;
import com.franklin.logoutarmycd.core.UserEndDateComparable;
import com.franklin.logoutarmycd.core.UserJoinDateComparable;
import com.franklin.logoutarmycd.core.UserLeftDaysComparable;
import com.franklin.logoutarmycd.core.UserPercentageComparable;
import com.franklin.logoutarmycd.web.SessionKeyEnum;
import com.franklin.logoutarmycd.web.util.AjaxUtil;
import com.franklin.logoutarmycd.web.util.SessionUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.restfb.types.User;

@SuppressWarnings("serial")
public class BillBoardAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private LogoutArmyCountdownManaer mlacdManager = null;
	private FacebookController mFbController = null;
	private Logger logger = LoggerFactory.getLogger(BillBoardAction.class);

	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response){
		this.response = response;
	}	

	private void initSessionData(){
		mlacdManager = (LogoutArmyCountdownManaer)SessionUtil.
			getAttribute(request, SessionKeyEnum.LGACD_MANAGER);
		mFbController = (FacebookController)
			SessionUtil.getAttribute(request, SessionKeyEnum.FACEBOOK_CONTROLLER);
	}

	/**
	 * Get the facebook ap user Map.
	 * 
	 * @return
	 */
	private Map<String, User> getFacebookAppUserMap(){
		logger.debug("mFbController={}", mFbController.getClass().getName());
		Map<String, User> userMap =  mFbController.getAppUserFriendMap();
		User loginUser_fb = mFbController.getLoginUser();
		userMap.put(loginUser_fb.getId().toString(), loginUser_fb);
		
		return userMap;
	}
	

	private List<AbstractCDUserComparable> getCDUserComparableList(Class<? extends AbstractCDUserComparable> targetClass,
			Map<String, User> userMap){
		List<AbstractCDUserComparable> userPercentageComparebleList = new ArrayList<AbstractCDUserComparable>();
		Set<String> apFriendSet = userMap.keySet();
		
		for( String friendId : apFriendSet ){
			LogoutArmyCDUser user = mlacdManager.getUser(friendId);
			if( user != null && !user.isNewUser() && !user.isLogout() ){
				AbstractCDUserComparable friend = AbstractCDUserComparableFactory.getInstance().createInstace(
						targetClass, user);
				userPercentageComparebleList.add(friend);
			}
		}
		return userPercentageComparebleList;
	}
	

	public String getPercentageBillboradInfo(){
		initSessionData();
		Map<String, User> userMap = getFacebookAppUserMap();
		List<AbstractCDUserComparable> userPercentageComparebleList = getCDUserComparableList(
				UserPercentageComparable.class, userMap);
		
		Collections.sort(userPercentageComparebleList);
		String xml = getBillboradInfoXml(userPercentageComparebleList, userMap, true);

		AjaxUtil.responseXmlData(response, xml);
		
		userMap = null;
		userPercentageComparebleList = null;
		return Action.NONE;
	}
	
	public String getJoinDateBillboradInfo(){
		initSessionData();
		Map<String, User> userMap = getFacebookAppUserMap();
		List<AbstractCDUserComparable> userJoinDateComparebleList = getCDUserComparableList(
				UserJoinDateComparable.class, userMap);
		
		Collections.sort(userJoinDateComparebleList);
		String xml = getBillboradInfoXml(userJoinDateComparebleList, userMap, false);

		AjaxUtil.responseXmlData(response, xml);
		
		userMap = null;
		userJoinDateComparebleList = null;
		return Action.NONE;
	}
	
	public String getLeftDaysBillboradInfo(){
		initSessionData();
	
		Map<String, User> userMap = getFacebookAppUserMap();
		
		List<AbstractCDUserComparable> userLeftDaysComparebleList = getCDUserComparableList(
				UserLeftDaysComparable.class, userMap);
		
		Collections.sort(userLeftDaysComparebleList);
		
		String xml = getBillboradInfoXml(userLeftDaysComparebleList, userMap, false);

		AjaxUtil.responseXmlData(response, xml);
		
		userMap = null;
		userLeftDaysComparebleList = null;
		return Action.NONE;
	}
	
	public String getEndDateBillboradInfo(){
		initSessionData();
	
		Map<String, User> userMap = getFacebookAppUserMap();
		
		List<AbstractCDUserComparable> userEndDateComparebleList = getCDUserComparableList(
				UserEndDateComparable.class, userMap);
		
		Collections.sort(userEndDateComparebleList);
		
		
		String xml = getBillboradInfoXml(userEndDateComparebleList, userMap, false);

		AjaxUtil.responseXmlData(response, xml);
		
		userMap = null;
		userEndDateComparebleList = null;
		return Action.NONE;
	}
	
	/**
	 * Get user xml information.
	 * 
	 * @param userComparable
	 * @param userMap
	 * @param order
	 * @return
	 */
	private StringBuilder getUserInfoXml(AbstractCDUserComparable userComparable,
			Map<String, User> userMap, int order){
		StringBuilder xml = new StringBuilder();
		LogoutArmyCDUser user = userComparable.getUser();
		User user_fb = userMap.get(user.getUID());
		LogoutArmyCDUser loginUser = mlacdManager.getLoginUser();
		
		String userName = user_fb.getName();
		
		long uid = Long.valueOf(user_fb.getId());
		double percentage = user.getPercentage();
		Date joinDate = user.getJoinDate();
		String joinDateString = DateUtil.format(joinDate, DateUtil._8DIGIT_DATE_2);
		Date endDate = user.getEndDate();
		String endDateString = DateUtil.format(endDate, DateUtil._8DIGIT_DATE_2);
		long leftDays = user.getLeftDays();
//		if( img == null ){
//			img = "images/no-face.gif";
//		} 
		xml.append("<user isLogin=\"" + user.equals(loginUser) + "\"><order>" + order++ + "</order><name>" + userName + "</name><uid>" + uid +
				"</uid><percentage>" + percentage + "</percentage><joinDate>" + joinDateString + 
				"</joinDate><endDate>" + endDateString + "</endDate><leftDays>" + leftDays + 
				"</leftDays></user>");

		return xml;
	}
	
	/**
	 * �N�Ƨǫ᪺��T�নxml
	 * 
	 * @param userComparebleList
	 * @param userMap
	 * @return
	 */
	private String getBillboradInfoXml(List<AbstractCDUserComparable> userComparebleList,
			Map<String, User> userMap, boolean reverseFlag){
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?><root>");
		LogoutArmyCDUser loginUser = mlacdManager.getLoginUser();
		
		String userType = "Normal";
		if( loginUser.isNewUser() ){
			userType = "New";
		} else if( loginUser.isLogout() ){
			userType = "Logout";
		}
		
		int order = 1, size = userComparebleList.size();
		if( reverseFlag ){
			for( int i = size - 1 ; i >= 0 ; i--, order++ ){
				AbstractCDUserComparable userComparable = userComparebleList.get(i);
				xml.append(getUserInfoXml(userComparable, userMap, order));
		}
		} else {
			for( AbstractCDUserComparable userComparable : userComparebleList ){
				xml.append(getUserInfoXml(userComparable, userMap, order++));
			}
		}

		xml.append("<userType>" + userType + "</userType></root>");
		return xml.toString();
	}
	
	/**
	 * Get the logout information include top 3 and last 3 logout friends, and less than the login user's friends. 
	 * 
	 * @return
	 */
	public String getLogoutInformation(){
		initSessionData();
		Map<String, User> userMap = getFacebookAppUserMap();
		//Map<String, User> logoutUserMap = new HashMap<String, User>();
		Set<Entry<String, User>> fbUserEntryList = userMap.entrySet();
		
		AbstractCDUserComparable loginUser = null;
		List<AbstractCDUserComparable> userJoinDateComparebleList = new ArrayList<AbstractCDUserComparable>();
		// Filter the app user list according to whether is logout.
		for( Entry<String, User> fbUserEntry : fbUserEntryList ){  
			LogoutArmyCDUser user = mlacdManager.getUser(fbUserEntry.getKey());
			if( user != null && !user.isNewUser() && user.isLogout()){
				AbstractCDUserComparable friend = AbstractCDUserComparableFactory.getInstance().createInstace(
						UserJoinDateComparable.class, user);
				if( user.getUID().equals(mFbController.getLoginUser().getId()) ){
					loginUser = friend;
				}
				userJoinDateComparebleList.add(friend);
			}
		}
		
		Collections.sort(userJoinDateComparebleList);
		int totalLogout = userJoinDateComparebleList.size();
		int logoutOrder = userJoinDateComparebleList.indexOf(loginUser);
		
		// Generate the logout information xml.
		StringBuffer xml = new StringBuffer();
		xml.append("<logoutInformation><totalLogout>");
		xml.append(totalLogout);
		xml.append("</totalLogout><logoutOrder>");
		xml.append(logoutOrder+1);
		xml.append("</logoutOrder><toplist>");
		for( int i = 0 ; i < 3 && i < totalLogout ; i++ ){
			xml.append(getUserInfoXml(userJoinDateComparebleList.get(i), userMap, i+1));
		}
		xml.append("</toplist><lastlist>");
		int startIndex = totalLogout >= 6 ? totalLogout - 3 : 3;  
		for( int i = startIndex ; i < totalLogout ; i++ ){
			xml.append(getUserInfoXml(userJoinDateComparebleList.get(i), userMap, i+1));
		}
		xml.append("</lastlist><loselist>");
		if( loginUser != null ){
			int loginUserIndex = userJoinDateComparebleList.indexOf(loginUser);
			for( int i = loginUserIndex+1 ; i < userJoinDateComparebleList.size() ; i++ ){
				xml.append(getUserInfoXml(userJoinDateComparebleList.get(i), userMap, i+1));
			}
		}
		xml.append("</loselist><loginuser>");
		xml.append(loginUser == null ? "" : getUserInfoXml(userJoinDateComparebleList.get(logoutOrder), userMap, logoutOrder));
		xml.append("</loginuser></logoutInformation>");
		logger.debug(xml.toString());
		AjaxUtil.responseXmlData(response,  xml.toString());
		return Action.NONE;
	}
}
