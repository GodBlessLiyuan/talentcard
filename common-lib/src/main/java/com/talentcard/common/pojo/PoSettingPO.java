package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * po_setting
 * @author 
 */
public class PoSettingPO implements Serializable {
    /**
     * 政策适配人群属性id
     */
    private Long pSetingid;

    /**
     * 人才政策ID
     */
    private Long policyId;

    /**
     * 人才卡ID
     */
    private Long cardId;

    /**
     * 人才类别ID
     */
    private Long categoryId;

    /**
     * 学历ID
     */
    private Integer educationId;

    /**
     * 职称ID
     */
    private Integer titleId;

    /**
     * 人才职业资格ID
     */
    private Integer quality;

    /**
     * 人才荣誉id
     */
    private Long honourId;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
     */
    private Byte type;

    private static final long serialVersionUID = 1L;

    public Long getpSetingid() {
        return pSetingid;
    }

    public void setpSetingid(Long pSetingid) {
        this.pSetingid = pSetingid;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Long getHonourId() {
        return honourId;
    }

    public void setHonourId(Long honourId) {
        this.honourId = honourId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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
        PoSettingPO other = (PoSettingPO) that;
        return (this.getpSetingid() == null ? other.getpSetingid() == null : this.getpSetingid().equals(other.getpSetingid()))
            && (this.getPolicyId() == null ? other.getPolicyId() == null : this.getPolicyId().equals(other.getPolicyId()))
            && (this.getCardId() == null ? other.getCardId() == null : this.getCardId().equals(other.getCardId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getEducationId() == null ? other.getEducationId() == null : this.getEducationId().equals(other.getEducationId()))
            && (this.getTitleId() == null ? other.getTitleId() == null : this.getTitleId().equals(other.getTitleId()))
            && (this.getQuality() == null ? other.getQuality() == null : this.getQuality().equals(other.getQuality()))
            && (this.getHonourId() == null ? other.getHonourId() == null : this.getHonourId().equals(other.getHonourId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getpSetingid() == null) ? 0 : getpSetingid().hashCode());
        result = prime * result + ((getPolicyId() == null) ? 0 : getPolicyId().hashCode());
        result = prime * result + ((getCardId() == null) ? 0 : getCardId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getEducationId() == null) ? 0 : getEducationId().hashCode());
        result = prime * result + ((getTitleId() == null) ? 0 : getTitleId().hashCode());
        result = prime * result + ((getQuality() == null) ? 0 : getQuality().hashCode());
        result = prime * result + ((getHonourId() == null) ? 0 : getHonourId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pSetingid=").append(pSetingid);
        sb.append(", policyId=").append(policyId);
        sb.append(", cardId=").append(cardId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", educationId=").append(educationId);
        sb.append(", titleId=").append(titleId);
        sb.append(", quality=").append(quality);
        sb.append(", honourId=").append(honourId);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}