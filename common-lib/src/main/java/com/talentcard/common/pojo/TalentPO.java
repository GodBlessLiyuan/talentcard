package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * t_talent
 * @author 
 */
public class TalentPO implements Serializable {
    private Long talentId;

    private String openId;

    private String name;

    /**
     * 1：男；2：女
     */
    private Byte sex;

    private String idCard;

    private String passport;

    private String driverCard;

    /**
     * 1身份证2护照3驾照
     */
    private Byte cardType;

    private String workUnit;

    private Integer industry;

    private Integer industrySecond;

    private String phone;

    private Date createTime;

    private String category;

    private String workLocation;

    /**
     * 1国内；
2海外；
     */
    private Byte workLocationType;

    /**
     * 1 认证通过
2 认证没通过
     */
    private Byte status;

    private Long cardId;

    /**
     * 1正在使用
2删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getDriverCard() {
        return driverCard;
    }

    public void setDriverCard(String driverCard) {
        this.driverCard = driverCard;
    }

    public Byte getCardType() {
        return cardType;
    }

    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Integer getIndustrySecond() {
        return industrySecond;
    }

    public void setIndustrySecond(Integer industrySecond) {
        this.industrySecond = industrySecond;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public Byte getWorkLocationType() {
        return workLocationType;
    }

    public void setWorkLocationType(Byte workLocationType) {
        this.workLocationType = workLocationType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Byte getDr() {
        return dr;
    }

    public void setDr(Byte dr) {
        this.dr = dr;
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
        TalentPO other = (TalentPO) that;
        return (this.getTalentId() == null ? other.getTalentId() == null : this.getTalentId().equals(other.getTalentId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
            && (this.getPassport() == null ? other.getPassport() == null : this.getPassport().equals(other.getPassport()))
            && (this.getDriverCard() == null ? other.getDriverCard() == null : this.getDriverCard().equals(other.getDriverCard()))
            && (this.getCardType() == null ? other.getCardType() == null : this.getCardType().equals(other.getCardType()))
            && (this.getWorkUnit() == null ? other.getWorkUnit() == null : this.getWorkUnit().equals(other.getWorkUnit()))
            && (this.getIndustry() == null ? other.getIndustry() == null : this.getIndustry().equals(other.getIndustry()))
            && (this.getIndustrySecond() == null ? other.getIndustrySecond() == null : this.getIndustrySecond().equals(other.getIndustrySecond()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getWorkLocation() == null ? other.getWorkLocation() == null : this.getWorkLocation().equals(other.getWorkLocation()))
            && (this.getWorkLocationType() == null ? other.getWorkLocationType() == null : this.getWorkLocationType().equals(other.getWorkLocationType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCardId() == null ? other.getCardId() == null : this.getCardId().equals(other.getCardId()))
            && (this.getDr() == null ? other.getDr() == null : this.getDr().equals(other.getDr()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTalentId() == null) ? 0 : getTalentId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getPassport() == null) ? 0 : getPassport().hashCode());
        result = prime * result + ((getDriverCard() == null) ? 0 : getDriverCard().hashCode());
        result = prime * result + ((getCardType() == null) ? 0 : getCardType().hashCode());
        result = prime * result + ((getWorkUnit() == null) ? 0 : getWorkUnit().hashCode());
        result = prime * result + ((getIndustry() == null) ? 0 : getIndustry().hashCode());
        result = prime * result + ((getIndustrySecond() == null) ? 0 : getIndustrySecond().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getWorkLocation() == null) ? 0 : getWorkLocation().hashCode());
        result = prime * result + ((getWorkLocationType() == null) ? 0 : getWorkLocationType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCardId() == null) ? 0 : getCardId().hashCode());
        result = prime * result + ((getDr() == null) ? 0 : getDr().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", talentId=").append(talentId);
        sb.append(", openId=").append(openId);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", idCard=").append(idCard);
        sb.append(", passport=").append(passport);
        sb.append(", driverCard=").append(driverCard);
        sb.append(", cardType=").append(cardType);
        sb.append(", workUnit=").append(workUnit);
        sb.append(", industry=").append(industry);
        sb.append(", industrySecond=").append(industrySecond);
        sb.append(", phone=").append(phone);
        sb.append(", createTime=").append(createTime);
        sb.append(", category=").append(category);
        sb.append(", workLocation=").append(workLocation);
        sb.append(", workLocationType=").append(workLocationType);
        sb.append(", status=").append(status);
        sb.append(", cardId=").append(cardId);
        sb.append(", dr=").append(dr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}