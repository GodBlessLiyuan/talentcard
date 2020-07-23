package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * m_trip_daily
 * @author
 */
public class TripDailyPO implements Serializable {
    private Long tdId;

    private String sidDaily;

    private Long sid;

    private Date daily;

    private String scenicName;

    private Long numbers;

    private Long freeTimes;

    private Long discountTimes;

    private Long totalTimes;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getTdId() {
        return tdId;
    }

    public void setTdId(Long tdId) {
        this.tdId = tdId;
    }

    public String getSidDaily() {
        return sidDaily;
    }

    public void setSidDaily(String sidDaily) {
        this.sidDaily = sidDaily;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Date getDaily() {
        return daily;
    }

    public void setDaily(Date daily) {
        this.daily = daily;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
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
        TripDailyPO other = (TripDailyPO) that;
        return (this.getTdId() == null ? other.getTdId() == null : this.getTdId().equals(other.getTdId()))
                && (this.getSidDaily() == null ? other.getSidDaily() == null : this.getSidDaily().equals(other.getSidDaily()))
                && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
                && (this.getDaily() == null ? other.getDaily() == null : this.getDaily().equals(other.getDaily()))
                && (this.getScenicName() == null ? other.getScenicName() == null : this.getScenicName().equals(other.getScenicName()))
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
        result = prime * result + ((getTdId() == null) ? 0 : getTdId().hashCode());
        result = prime * result + ((getSidDaily() == null) ? 0 : getSidDaily().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getDaily() == null) ? 0 : getDaily().hashCode());
        result = prime * result + ((getScenicName() == null) ? 0 : getScenicName().hashCode());
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
        sb.append(", tdId=").append(tdId);
        sb.append(", sidDaily=").append(sidDaily);
        sb.append(", sid=").append(sid);
        sb.append(", daily=").append(daily);
        sb.append(", scenicName=").append(scenicName);
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