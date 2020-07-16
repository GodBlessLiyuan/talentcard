package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * t_user_feedback
 * @author 
 */
public class UserFeedbackPO implements Serializable {
    private Long ufId;

    private String openId;

    private Byte pageType;

    private String relateItem;

    private String chooseItem;

    private String proDescribe;

    private Date submitDate;

    private static final long serialVersionUID = 1L;

    public Long getUfId() {
        return ufId;
    }

    public void setUfId(Long ufId) {
        this.ufId = ufId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Byte getPageType() {
        return pageType;
    }

    public void setPageType(Byte pageType) {
        this.pageType = pageType;
    }

    public String getRelateItem() {
        return relateItem;
    }

    public void setRelateItem(String relateItem) {
        this.relateItem = relateItem;
    }

    public String getChooseItem() {
        return chooseItem;
    }

    public void setChooseItem(String chooseItem) {
        this.chooseItem = chooseItem;
    }

    public String getProDescribe() {
        return proDescribe;
    }

    public void setProDescribe(String proDescribe) {
        this.proDescribe = proDescribe;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
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
        UserFeedbackPO other = (UserFeedbackPO) that;
        return (this.getUfId() == null ? other.getUfId() == null : this.getUfId().equals(other.getUfId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getPageType() == null ? other.getPageType() == null : this.getPageType().equals(other.getPageType()))
            && (this.getRelateItem() == null ? other.getRelateItem() == null : this.getRelateItem().equals(other.getRelateItem()))
            && (this.getChooseItem() == null ? other.getChooseItem() == null : this.getChooseItem().equals(other.getChooseItem()))
            && (this.getProDescribe() == null ? other.getProDescribe() == null : this.getProDescribe().equals(other.getProDescribe()))
            && (this.getSubmitDate() == null ? other.getSubmitDate() == null : this.getSubmitDate().equals(other.getSubmitDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUfId() == null) ? 0 : getUfId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getPageType() == null) ? 0 : getPageType().hashCode());
        result = prime * result + ((getRelateItem() == null) ? 0 : getRelateItem().hashCode());
        result = prime * result + ((getChooseItem() == null) ? 0 : getChooseItem().hashCode());
        result = prime * result + ((getProDescribe() == null) ? 0 : getProDescribe().hashCode());
        result = prime * result + ((getSubmitDate() == null) ? 0 : getSubmitDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ufId=").append(ufId);
        sb.append(", openId=").append(openId);
        sb.append(", pageType=").append(pageType);
        sb.append(", relateItem=").append(relateItem);
        sb.append(", chooseItem=").append(chooseItem);
        sb.append(", proDescribe=").append(proDescribe);
        sb.append(", submitDate=").append(submitDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}