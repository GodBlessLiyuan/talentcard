package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * ev_event_time
 * @author 
 */
public class EvEventTimePO implements Serializable {
    /**
     * 活动场地占用情况Id
     */
    private Long id;

    private Long efId;

    /**
     * 场地占用日期年月日
     */
    private Date placeDate;

    /**
     * 将一天时间划分成48个小时间段
     */
    private String timeInterval;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEfId() {
        return efId;
    }

    public void setEfId(Long efId) {
        this.efId = efId;
    }

    public Date getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(Date placeDate) {
        this.placeDate = placeDate;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
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
        EvEventTimePO other = (EvEventTimePO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEfId() == null ? other.getEfId() == null : this.getEfId().equals(other.getEfId()))
            && (this.getPlaceDate() == null ? other.getPlaceDate() == null : this.getPlaceDate().equals(other.getPlaceDate()))
            && (this.getTimeInterval() == null ? other.getTimeInterval() == null : this.getTimeInterval().equals(other.getTimeInterval()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEfId() == null) ? 0 : getEfId().hashCode());
        result = prime * result + ((getPlaceDate() == null) ? 0 : getPlaceDate().hashCode());
        result = prime * result + ((getTimeInterval() == null) ? 0 : getTimeInterval().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", efId=").append(efId);
        sb.append(", placeDate=").append(placeDate);
        sb.append(", timeInterval=").append(timeInterval);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}