package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * po_talent_type
 * @author 
 */
public class PoTalentTypePO implements Serializable {
    /**
     * 自增id
     */
    private Long pTalentTypeId;

    /**
     * 人才标签综合id
     */
    private String talentType;

    /**
     * 人才政策ID
     */
    private Long policyId;

    private static final long serialVersionUID = 1L;

    public Long getpTalentTypeId() {
        return pTalentTypeId;
    }

    public void setpTalentTypeId(Long pTalentTypeId) {
        this.pTalentTypeId = pTalentTypeId;
    }

    public String getTalentType() {
        return talentType;
    }

    public void setTalentType(String talentType) {
        this.talentType = talentType;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
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
        PoTalentTypePO other = (PoTalentTypePO) that;
        return (this.getpTalentTypeId() == null ? other.getpTalentTypeId() == null : this.getpTalentTypeId().equals(other.getpTalentTypeId()))
            && (this.getTalentType() == null ? other.getTalentType() == null : this.getTalentType().equals(other.getTalentType()))
            && (this.getPolicyId() == null ? other.getPolicyId() == null : this.getPolicyId().equals(other.getPolicyId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getpTalentTypeId() == null) ? 0 : getpTalentTypeId().hashCode());
        result = prime * result + ((getTalentType() == null) ? 0 : getTalentType().hashCode());
        result = prime * result + ((getPolicyId() == null) ? 0 : getPolicyId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pTalentTypeId=").append(pTalentTypeId);
        sb.append(", talentType=").append(talentType);
        sb.append(", policyId=").append(policyId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}