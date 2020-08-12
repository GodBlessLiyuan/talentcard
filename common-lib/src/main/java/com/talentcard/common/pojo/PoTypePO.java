package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * po_type
 * @author 
 */
public class PoTypePO implements Serializable {
    private Long pTid;

    /**
     * 政策类型名称
     */
    private String pTypeName;

    /**
     * 政策类型互斥Id
     */
    private String excludeId;

    /**
     * 政策类型适配最好的政策Id
     */
    private String bestPolicys;

    /**
     * 1：上架；2：下架
     */
    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    /**
     * 描述
     */
    private String description;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getpTid() {
        return pTid;
    }

    public void setpTid(Long pTid) {
        this.pTid = pTid;
    }

    public String getpTypeName() {
        return pTypeName;
    }

    public void setpTypeName(String pTypeName) {
        this.pTypeName = pTypeName;
    }

    public String getExcludeId() {
        return excludeId;
    }

    public void setExcludeId(String excludeId) {
        this.excludeId = excludeId;
    }

    public String getBestPolicys() {
        return bestPolicys;
    }

    public void setBestPolicys(String bestPolicys) {
        this.bestPolicys = bestPolicys;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getDr() {
        return dr;
    }

    public void setDr(Byte dr) {
        this.dr = dr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        PoTypePO other = (PoTypePO) that;
        return (this.getpTid() == null ? other.getpTid() == null : this.getpTid().equals(other.getpTid()))
            && (this.getpTypeName() == null ? other.getpTypeName() == null : this.getpTypeName().equals(other.getpTypeName()))
            && (this.getExcludeId() == null ? other.getExcludeId() == null : this.getExcludeId().equals(other.getExcludeId()))
            && (this.getBestPolicys() == null ? other.getBestPolicys() == null : this.getBestPolicys().equals(other.getBestPolicys()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDr() == null ? other.getDr() == null : this.getDr().equals(other.getDr()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getpTid() == null) ? 0 : getpTid().hashCode());
        result = prime * result + ((getpTypeName() == null) ? 0 : getpTypeName().hashCode());
        result = prime * result + ((getExcludeId() == null) ? 0 : getExcludeId().hashCode());
        result = prime * result + ((getBestPolicys() == null) ? 0 : getBestPolicys().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDr() == null) ? 0 : getDr().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pTid=").append(pTid);
        sb.append(", pTypeName=").append(pTypeName);
        sb.append(", excludeId=").append(excludeId);
        sb.append(", bestPolicys=").append(bestPolicys);
        sb.append(", status=").append(status);
        sb.append(", dr=").append(dr);
        sb.append(", description=").append(description);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}