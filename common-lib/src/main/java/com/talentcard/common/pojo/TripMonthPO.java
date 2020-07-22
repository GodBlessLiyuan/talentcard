package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * m_trip_month
 * @author
 */
public class TripMonthPO implements Serializable {
    private Long tmId;

    private String sidMonth;

    private Long sid;

    private Date month;

    private String name;

    private Long numbers;

    private Long freeTimes;

    private Long discountTimes;

    private Long totalTimes;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getTmId() {
        return tmId;
    }

    public void setTmId(Long tmId) {
        this.tmId = tmId;
    }

    public String getSidMonth() {
        return sidMonth;
    }

    public void setSidMonth(String sidMonth) {
        this.sidMonth = sidMonth;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumbers() {
        return numbers;
    }

    public void setNumbers(Long numbers) {
        this.numbers = numbers;
    }

    public Long getFreeTimes() {
        return freeTimes;
    }

    public void setFreeTimes(Long freeTimes) {
        this.freeTimes = freeTimes;
    }

    public Long getDiscountTimes() {
        return discountTimes;
    }

    public void setDiscountTimes(Long discountTimes) {
        this.discountTimes = discountTimes;
    }

    public Long getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Long totalTimes) {
        this.totalTimes = totalTimes;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TripMonthPO other = (TripMonthPO) that;
        return (this.getTmId() == null ? other.getTmId() == null : this.getTmId().equals(other.getTmId()))
                && (this.getSidMonth() == null ? other.getSidMonth() == null : this.getSidMonth().equals(other.getSidMonth()))
                && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
                && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getNumbers() == null ? other.getNumbers() == null : this.getNumbers().equals(other.getNumbers()))
                && (this.getFreeTimes() == null ? other.getFreeTimes() == null : this.getFreeTimes().equals(other.getFreeTimes()))
                && (this.getDiscountTimes() == null ? other.getDiscountTimes() == null : this.getDiscountTimes().equals(other.getDiscountTimes()))
                && (this.getTotalTimes() == null ? other.getTotalTimes() == null : this.getTotalTimes().equals(other.getTotalTimes()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTmId() == null) ? 0 : getTmId().hashCode());
        result = prime * result + ((getSidMonth() == null) ? 0 : getSidMonth().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNumbers() == null) ? 0 : getNumbers().hashCode());
        result = prime * result + ((getFreeTimes() == null) ? 0 : getFreeTimes().hashCode());
        result = prime * result + ((getDiscountTimes() == null) ? 0 : getDiscountTimes().hashCode());
        result = prime * result + ((getTotalTimes() == null) ? 0 : getTotalTimes().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tmId=").append(tmId);
        sb.append(", sidMonth=").append(sidMonth);
        sb.append(", sid=").append(sid);
        sb.append(", month=").append(month);
        sb.append(", name=").append(name);
        sb.append(", numbers=").append(numbers);
        sb.append(", freeTimes=").append(freeTimes);
        sb.append(", discountTimes=").append(discountTimes);
        sb.append(", totalTimes=").append(totalTimes);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}