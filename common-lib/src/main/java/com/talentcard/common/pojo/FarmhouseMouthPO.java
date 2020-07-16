package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * m_farmhouse_mouth
 * @author 
 */
public class FarmhouseMouthPO implements Serializable {
    private Long fhM;

    private Long farmhouseId;

    private String name;

    private Date mouth;

    private Long number;

    private Long times;

    private static final long serialVersionUID = 1L;

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

    public Date getMouth() {
        return mouth;
    }

    public void setMouth(Date mouth) {
        this.mouth = mouth;
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
        FarmhouseMouthPO other = (FarmhouseMouthPO) that;
        return (this.getFhM() == null ? other.getFhM() == null : this.getFhM().equals(other.getFhM()))
            && (this.getFarmhouseId() == null ? other.getFarmhouseId() == null : this.getFarmhouseId().equals(other.getFarmhouseId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMouth() == null ? other.getMouth() == null : this.getMouth().equals(other.getMouth()))
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
        result = prime * result + ((getMouth() == null) ? 0 : getMouth().hashCode());
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
        sb.append(", mouth=").append(mouth);
        sb.append(", number=").append(number);
        sb.append(", times=").append(times);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}