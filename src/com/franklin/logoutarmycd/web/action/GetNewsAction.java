package com.franklin.logoutarmycd.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.franklin.logoutarmycd.core.LogoutArmyCDUser;
import com.franklin.logoutarmycd.core.LogoutArmyCDUserAdapter;
import com.franklin.logoutarmycd.core.LogoutArmyNew;
import com.franklin.logoutarmycd.core.LogoutNewProxy;
import com.franklin.logoutarmycd.web.util.AjaxUtil;
//import com.google.code.facebookapi.schema.User;
import com.opensymphony.xwork2.Action;
import com.restfb.types.User;

public class GetNewsAction extends AbstractLACDAction {
	private Logger logger = LoggerFactory.getLogger(GetNewsAction.class);

	private boolean isNeededToShow(LogoutNewProxy userNewAdapter){
		if( userNewAdapter.isNewUser() ) return false;
		long leftDays = userNewAdapter.getLeftDays();
		long joinDays = userNewAdapter.getJoinDays();
		boolean checkLeft = (leftDays < 10 && leftDays > -10) ||
			( leftDays < 110 && leftDays > 90);
		boolean checkJoin = joinDays < 37 && joinDays > -10;
		return checkLeft || checkJoin ; 
	}
	
	/**
	 * Get seven friend info by random method.
	 * 
	 * @return
	 */
	public String execute(){
		initSessionData();
		logger.debug("Start to get news action for {}", mLacdManager.getLoginUser().getUID());
		List<User> userList = mFbController.getAppUserFriendList();
		
		List<LogoutNewProxy> newList = new ArrayList<LogoutNewProxy>();
		for( User user : userList ){
			LogoutArmyCDUser lacdUser = mLacdManager.getUser(Long.valueOf(user.getId()));
			LogoutArmyCDUserAdapter userAdapter = 
				new LogoutArmyCDUserAdapter(lacdUser, user);
			LogoutArmyNew logoutUserNew = new LogoutArmyNew(userAdapter);
			LogoutNewProxy userNewAdapter = new LogoutNewProxy(logoutUserNew);
			if( isNeededToShow(userNewAdapter) ){
				newList.add(userNewAdapter);
			}
		}
		
		int userSize = newList.size();
		
		// Get 5 friends.
		List<LogoutNewProxy> randomUserList = new ArrayList<LogoutNewProxy>();
		Random rd = new Random();
		for( int i = 0 ; i < 5 && i < userSize ; ){
			int index = rd.nextInt(userSize);
			LogoutNewProxy user = newList.get(index);
			if( randomUserList.indexOf(user) == -1){
				randomUserList.add(user);
				i++;
			}
		}

		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<root>");
		for( LogoutNewProxy newAdpter : randomUserList ){
			xml.append(newAdpter.getXMLString());
		}
		xml.append("</root>\n");
		
		logger.debug("Get user[{}] news: \n {}", mLacdManager.getLoginUser().getUID(), xml);
		
		AjaxUtil.responseXmlData(response, xml.toString());
		return Action.NONE;
	}
}
