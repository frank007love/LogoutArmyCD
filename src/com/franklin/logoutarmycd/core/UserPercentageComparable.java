package com.franklin.logoutarmycd.core;

public class UserPercentageComparable extends AbstractCDUserComparable {

	private UserPercentageComparable(LogoutArmyCDUser user){
		super(user);
	}
	
	static public AbstractCDUserComparable getInstance(LogoutArmyCDUser user){
		return new UserPercentageComparable(user);
	}
	
	@Override
	public int compareTo(AbstractCDUserComparable arg0) {
		if( !(arg0 instanceof UserPercentageComparable) ){
			throw new RuntimeException("Compare type is illegle.");
		}
		double user1_percentage = m_user.getPercentage();
		double user2_percentage = arg0.getUser().getPercentage();
		
		if(  user1_percentage > user2_percentage )
			return 1;
		else if( user2_percentage == user1_percentage ){
			return 0;
		}
		return -1;
	}
}
