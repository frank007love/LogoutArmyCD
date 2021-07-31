package com.franklin.logoutarmycd.core;

public class AbstractCDUserComparableFactory {

	static public AbstractCDUserComparableFactory getInstance(){
		return new AbstractCDUserComparableFactory();
	}
	
	/**
	 * 根據使用者需要的type去產生對應instance
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
