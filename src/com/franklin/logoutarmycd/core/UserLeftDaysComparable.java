package com.franklin.logoutarmycd.core;

public class UserLeftDaysComparable extends AbstractCDUserComparable {

	private UserLeftDaysComparable(LogoutArmyCDUser user){
		super(user);
	}
	
	static public AbstractCDUserComparable getInstance(LogoutArmyCDUser user){
		return new UserLeftDaysComparable(user);
	}
	
	@Override
	public int compareTo(AbstractCDUserComparable arg0) {
		if( !(arg0 instanceof UserLeftDaysComparable) ){
			throw new RuntimeException("Compare type is illegle.");
		}
		double user1_leftDays = m_user.getLeftDays();
		double user2_leftDays = arg0.getUser().getLeftDays();
		
		if(  user1_leftDays > user2_leftDays )
			return 1;
		else if( user1_leftDays == user2_leftDays ){
			return 0;
		}
		return -1;
	}
}
