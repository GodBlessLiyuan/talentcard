package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * m_farmhouse_daily
 * @author 
 */
public class FarmhouseDailyPO implements Serializable {
    private Long fhD;
    private String dailyFarmHouseID;
    private Long farmhouseId;

    private String name;

    private Date dailyTime;

    private Long number;

    private Long times;
    private Date updateTime;
    private static final long serialVersionUID = 1L;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDailyFarmHouseID() {
        return dailyFarmHouseID;
    }

    public void setDailyFarmHouseID(String dailyFarmHouseID) {
        this.dailyFarmHouseID = dailyFarmHouseID;
    }

    public Long getFhD() {
        return fhD;
    }

    public void setFhD(Long fhD) {
        this.fhD = fhD;
    }

    public Long getFarmhouseId() {
        return farmhouseId;
    }

    public void setFarmhouseId(Long farmhouseId) {
        this.farmhouseId = farmhouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(Date dailyTime) {
        this.dailyTime = dailyTime;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
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
        FarmhouseDailyPO other = (FarmhouseDailyPO) that;
        return (this.getFhD() == null ? other.getFhD() == null : this.getFhD().equals(other.getFhD()))
            && (this.getFarmhouseId() == null ? other.getFarmhouseId() == null : this.getFarmhouseId().equals(other.getFarmhouseId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDailyTime() == null ? other.getDailyTime() == null : this.getDailyTime().equals(other.getDailyTime()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getTimes() == null ? other.getTimes() == null : this.getTimes().equals(other.getTimes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFhD() == null) ? 0 : getFhD().hashCode());
        result = prime * result + ((getFarmhouseId() == null) ? 0 : getFarmhouseId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDailyTime() == null) ? 0 : getDailyTime().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getTimes() == null) ? 0 : getTimes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fhD=").append(fhD);
        sb.append(", farmhouseId=").append(farmhouseId);
        sb.append(", name=").append(name);
        sb.append(", dailyTime=").append(dailyTime);
        sb.append(", number=").append(number);
        sb.append(", times=").append(times);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}