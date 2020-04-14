package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * t_user_current_info
 * @author 
 */
public class UserCurrentInfoPO implements Serializable {
    private Long uciId;

    private Long ucId;

    private Integer education;

    private Integer ptCategory;

    private String ptInfo;

    private Integer pqCategory;

    private String pqInfo;

    private static final long serialVersionUID = 1L;

    public Long getUciId() {
        return uciId;
    }

    public void setUciId(Long uciId) {
        this.uciId = uciId;
    }

    public Long getUcId() {
        return ucId;
    }

    public void setUcId(Long ucId) {
        this.ucId = ucId;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getPtCategory() {
        return ptCategory;
    }

    public void setPtCategory(Integer ptCategory) {
        this.ptCategory = ptCategory;
    }

    public String getPtInfo() {
        return ptInfo;
    }

    public void setPtInfo(String ptInfo) {
        this.ptInfo = ptInfo;
    }

    public Integer getPqCategory() {
        return pqCategory;
    }

    public void setPqCategory(Integer pqCategory) {
        this.pqCategory = pqCategory;
    }

    public String getPqInfo() {
        return pqInfo;
    }

    public void setPqInfo(String pqInfo) {
        this.pqInfo = pqInfo;
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
        UserCurrentInfoPO other = (UserCurrentInfoPO) that;
        return (this.getUciId() == null ? other.getUciId() == null : this.getUciId().equals(other.getUciId()))
            && (this.getUcId() == null ? other.getUcId() == null : this.getUcId().equals(other.getUcId()))
            && (this.getEducation() == null ? other.getEducation() == null : this.getEducation().equals(other.getEducation()))
            && (this.getPtCategory() == null ? other.getPtCategory() == null : this.getPtCategory().equals(other.getPtCategory()))
            && (this.getPtInfo() == null ? other.getPtInfo() == null : this.getPtInfo().equals(other.getPtInfo()))
            && (this.getPqCategory() == null ? other.getPqCategory() == null : this.getPqCategory().equals(other.getPqCategory()))
            && (this.getPqInfo() == null ? other.getPqInfo() == null : this.getPqInfo().equals(other.getPqInfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUciId() == null) ? 0 : getUciId().hashCode());
        result = prime * result + ((getUcId() == null) ? 0 : getUcId().hashCode());
        result = prime * result + ((getEducation() == null) ? 0 : getEducation().hashCode());
        result = prime * result + ((getPtCategory() == null) ? 0 : getPtCategory().hashCode());
        result = prime * result + ((getPtInfo() == null) ? 0 : getPtInfo().hashCode());
        result = prime * result + ((getPqCategory() == null) ? 0 : getPqCategory().hashCode());
        result = prime * result + ((getPqInfo() == null) ? 0 : getPqInfo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uciId=").append(uciId);
        sb.append(", ucId=").append(ucId);
        sb.append(", education=").append(education);
        sb.append(", ptCategory=").append(ptCategory);
        sb.append(", ptInfo=").append(ptInfo);
        sb.append(", pqCategory=").append(pqCategory);
        sb.append(", pqInfo=").append(pqInfo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}