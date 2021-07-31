package com.franklin.logoutarmycd.core;

public class AbstractCDUserComparableFactory {

	static public AbstractCDUserComparableFactory getInstance(){
		return new AbstractCDUserComparableFactory();
	}
	
	/**
	 * �ھڨϥΪ̻ݭn��type�h���͹���instance
	 * 
	 * @param targetClass
	 * @param user
	 * @return
	 */
	public AbstractCDUserComparable createInstace(Class<? extends AbstractCDUserComparable> targetClass, 
			LogoutArmyCDUser user){
		if( targetClass.equals(UserPercentageComparable.class) ){
			return UserPercentageComparable.getInstance(user);
		} else if( targetClass.equals(UserEndDateComparable.class) ){
			return UserEndDateComparable.getInstance(user);
		} else if( targetClass.equals(UserJoinDateComparable.class) ){
			return UserJoinDateComparable.getInstance(user);
		}
		// Default
		return UserLeftDaysComparable.getInstance(user);
	}
	
}
