package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * t_policy_apply
 * @author 
 */
public class PolicyApplyPO implements Serializable {
    private Long paId;

    private Long talentId;

    private String talentName;

    private Long policyId;

    private String policyName;

    private Date createTime;

    /**
     * 1：已同意；2：已驳回；3：待审批
     */
    private Byte status;

    private static final long serialVersionUID = 1L;

    public Long getPaId() {
        return paId;
    }

    public void setPaId(Long paId) {
        this.paId = paId;
    }

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public String getTalentName() {
        return talentName;
    }

    public void setTalentName(String talentName) {
        this.talentName = talentName;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        PolicyApplyPO other = (PolicyApplyPO) that;
        return (this.getPaId() == null ? other.getPaId() == null : this.getPaId().equals(other.getPaId()))
            && (this.getTalentId() == null ? other.getTalentId() == null : this.getTalentId().equals(other.getTalentId()))
            && (this.getTalentName() == null ? other.getTalentName() == null : this.getTalentName().equals(other.getTalentName()))
            && (this.getPolicyId() == null ? other.getPolicyId() == null : this.getPolicyId().equals(other.getPolicyId()))
            && (this.getPolicyName() == null ? other.getPolicyName() == null : this.getPolicyName().equals(other.getPolicyName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaId() == null) ? 0 : getPaId().hashCode());
        result = prime * result + ((getTalentId() == null) ? 0 : getTalentId().hashCode());
        result = prime * result + ((getTalentName() == null) ? 0 : getTalentName().hashCode());
        result = prime * result + ((getPolicyId() == null) ? 0 : getPolicyId().hashCode());
        result = prime * result + ((getPolicyName() == null) ? 0 : getPolicyName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paId=").append(paId);
        sb.append(", talentId=").append(talentId);
        sb.append(", talentName=").append(talentName);
        sb.append(", policyId=").append(policyId);
        sb.append(", policyName=").append(policyName);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}