package com.franklin.logoutarmycd.core.storage.db.po;

import java.util.Date;

public interface IPoLogoutArmyCDUser {
    String getFbuserid();
    void setFbuserid(String fbuserid);
    Date getJoindate();
    void setJoindate(Date joindate);
    String getPeriodyear();
    void setPeriodyear(String periodyear);
    String getPeriodmonth();
    void setPeriodmonth(String periodmonth);
    String getPerioddate();
    void setPerioddate(String perioddate);
    Integer getReducedays();
    void setReducedays(Integer reducedays);
    Integer getOtherreducedays();
    void setOtherreducedays(Integer otherreducedays);
}
