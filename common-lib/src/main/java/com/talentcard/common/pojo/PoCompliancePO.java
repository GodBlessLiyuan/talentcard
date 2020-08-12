package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * po_compliance
 * @author 
 */
public class PoCompliancePO implements Serializable {
    /**
     * 复合人才政策的自增id
     */
    private Long pCoId;

    /**
     * 人才政策ID
     */
    private Long policyId;

    private Long talentId;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 0：未申请；1：已同意；2：已驳回；3：待审批
     */
    private Byte status;

    private static final long serialVersionUID = 1L;

    public Long getpCoId() {
        return pCoId;
    }

    public void setpCoId(Long pCoId) {
        this.pCoId = pCoId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        PoCompliancePO other = (PoCompliancePO) that;
        return (this.getpCoId() == null ? other.getpCoId() == null : this.getpCoId().equals(other.getpCoId()))
            && (this.getPolicyId() == null ? other.getPolicyId() == null : this.getPolicyId().equals(other.getPolicyId()))
            && (this.getTalentId() == null ? other.getTalentId() == null : this.getTalentId().equals(other.getTalentId()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getpCoId() == null) ? 0 : getpCoId().hashCode());
        result = prime * result + ((getPolicyId() == null) ? 0 : getPolicyId().hashCode());
        result = prime * result + ((getTalentId() == null) ? 0 : getTalentId().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pCoId=").append(pCoId);
        sb.append(", policyId=").append(policyId);
        sb.append(", talentId=").append(talentId);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}