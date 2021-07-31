package com.franklin.logoutarmycd.core;

public class UserJoinDateComparable extends AbstractCDUserComparable {

	private UserJoinDateComparable(LogoutArmyCDUser user){
		super(user);
	}
	
	static public AbstractCDUserComparable getInstance(LogoutArmyCDUser user){
		return new UserJoinDateComparable(user);
	}
	
	@Override
	public int compareTo(AbstractCDUserComparable arg0) {
		if( !(arg0 instanceof UserJoinDateComparable) ){
			throw new RuntimeException("Compare type is illegle.");
		}
		double user1_joinDate = m_user.getJoinDate().getTime();
		double user2_joinDate = arg0.getUser().getJoinDate().getTime();
		
		if(  user1_joinDate > user2_joinDate )
			return 1;
		else if( user1_joinDate == user2_joinDate ){
			return 0;
		}
		return -1;
	}
}
