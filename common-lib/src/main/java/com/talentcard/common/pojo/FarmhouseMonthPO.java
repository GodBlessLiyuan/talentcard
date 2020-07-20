package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * m_farmhouse_month
 * @author 
 */
public class FarmhouseMonthPO implements Serializable {
    private Long fhM;
    private String monthFarmhouseID;
    private Long farmhouseId;

    private String name;

    private Date month;

    private Long number;

    private Long times;

    private static final long serialVersionUID = 1L;

    public String getMonthFarmhouseID() {
        return monthFarmhouseID;
    }

    public void setMonthFarmhouseID(String monthFarmhouseID) {
        this.monthFarmhouseID = monthFarmhouseID;
    }

    public Long getFhM() {
        return fhM;
    }

    public void setFhM(Long fhM) {
        this.fhM = fhM;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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
        FarmhouseMonthPO other = (FarmhouseMonthPO) that;
        return (this.getFhM() == null ? other.getFhM() == null : this.getFhM().equals(other.getFhM()))
            && (this.getFarmhouseId() == null ? other.getFarmhouseId() == null : this.getFarmhouseId().equals(other.getFarmhouseId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getTimes() == null ? other.getTimes() == null : this.getTimes().equals(other.getTimes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFhM() == null) ? 0 : getFhM().hashCode());
        result = prime * result + ((getFarmhouseId() == null) ? 0 : getFarmhouseId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
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
        sb.append(", fhM=").append(fhM);
        sb.append(", farmhouseId=").append(farmhouseId);
        sb.append(", name=").append(name);
        sb.append(", month=").append(month);
        sb.append(", number=").append(number);
        sb.append(", times=").append(times);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}