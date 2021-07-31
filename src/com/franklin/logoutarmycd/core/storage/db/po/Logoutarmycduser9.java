package com.franklin.logoutarmycd.core.storage.db.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 *        @hibernate.class
 *         table="logoutarmycduser_9"
 *     
*/
public class Logoutarmycduser9 implements IPoLogoutArmyCDUser,Serializable {

    /** identifier field */
    private String fbuserid;

    /** nullable persistent field */
    private Date joindate;

    /** nullable persistent field */
    private String periodyear;

    /** nullable persistent field */
    private String periodmonth;

    /** nullable persistent field */
    private String perioddate;

    /** nullable persistent field */
    private Integer reducedays;

    /** nullable persistent field */
    private Integer otherreducedays;

    /** full constructor */
    public Logoutarmycduser9(String fbuserid, Date joindate, String periodyear, String periodmonth, String perioddate, Integer reducedays, Integer otherreducedays) {
        this.fbuserid = fbuserid;
        this.joindate = joindate;
        this.periodyear = periodyear;
        this.periodmonth = periodmonth;
        this.perioddate = perioddate;
        this.reducedays = reducedays;
        this.otherreducedays = otherreducedays;
    }

    /** default constructor */
    public Logoutarmycduser9() {
    }

    /** minimal constructor */
    public Logoutarmycduser9(String fbuserid) {
        this.fbuserid = fbuserid;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="fbuserid"
     *         
     */
    public String getFbuserid() {
        return this.fbuserid;
    }

    public void setFbuserid(String fbuserid) {
        this.fbuserid = fbuserid;
    }

    /** 
     *            @hibernate.property
     *             column="joindate"
     *             length="10"
     *         
     */
    public Date getJoindate() {
        return this.joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    /** 
     *            @hibernate.property
     *             column="periodyear"
     *             length="2"
     *         
     */
    public String getPeriodyear() {
        return this.periodyear;
    }

    public void setPeriodyear(String periodyear) {
        this.periodyear = periodyear;
    }

    /** 
     *            @hibernate.property
     *             column="periodmonth"
     *             length="2"
     *         
     */
    public String getPeriodmonth() {
        return this.periodmonth;
    }

    public void setPeriodmonth(String periodmonth) {
        this.periodmonth = periodmonth;
    }

    /** 
     *            @hibernate.property
     *             column="perioddate"
     *             length="2"
     *         
     */
    public String getPerioddate() {
        return this.perioddate;
    }

    public void setPerioddate(String perioddate) {
        this.perioddate = perioddate;
    }

    /** 
     *            @hibernate.property
     *             column="reducedays"
     *             length="10"
     *         
     */
    public Integer getReducedays() {
        return this.reducedays;
    }

    public void setReducedays(Integer reducedays) {
        this.reducedays = reducedays;
    }

    /** 
     *            @hibernate.property
     *             column="otherreducedays"
     *             length="10"
     *         
     */
    public Integer getOtherreducedays() {
        return this.otherreducedays;
    }

    public void setOtherreducedays(Integer otherreducedays) {
        this.otherreducedays = otherreducedays;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("fbuserid", getFbuserid())
            .toString();
    }

}
