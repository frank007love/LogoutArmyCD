package com.franklin.logoutarmycd.core;

public class UserEndDateComparable extends AbstractCDUserComparable {

	private UserEndDateComparable(LogoutArmyCDUser user){
		super(user);
	}
	
	static public AbstractCDUserComparable getInstance(LogoutArmyCDUser user){
		return new UserEndDateComparable(user);
	}
	
	@Override
	public int compareTo(AbstractCDUserComparable arg0) {
		if( !(arg0 instanceof UserEndDateComparable) ){
			throw new RuntimeException("Compare type is illegle.");
		}
		double user1_endDate = m_user.getEndDate().getTime();
		double user2_endDate = arg0.getUser().getEndDate().getTime();
		
		if(  user1_endDate > user2_endDate )
			return 1;
		else if( user1_endDate == user2_endDate ){
			return 0;
		}
		return -1;
	}
}
