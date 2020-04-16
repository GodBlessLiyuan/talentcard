package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * t_user_current_info
 * @author 
 */
public class UserCurrentInfoPO implements Serializable {
    private Long uciId;

    private Long talentId;

    private Byte political;

    private Integer education;

    private String school;

    /**
     * 1：是；2：否
     */
    private Byte firstClass;

    private String major;

    private Integer ptCategory;

    private String ptInfo;

    private Integer pqCategory;

    private String pqInfo;

    private String talentCategory;

    private static final long serialVersionUID = 1L;

    public Long getUciId() {
        return uciId;
    }

    public void setUciId(Long uciId) {
        this.uciId = uciId;
    }

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public Byte getPolitical() {
        return political;
    }

    public void setPolitical(Byte political) {
        this.political = political;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Byte getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(Byte firstClass) {
        this.firstClass = firstClass;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public String getTalentCategory() {
        return talentCategory;
    }

    public void setTalentCategory(String talentCategory) {
        this.talentCategory = talentCategory;
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
            && (this.getTalentId() == null ? other.getTalentId() == null : this.getTalentId().equals(other.getTalentId()))
            && (this.getPolitical() == null ? other.getPolitical() == null : this.getPolitical().equals(other.getPolitical()))
            && (this.getEducation() == null ? other.getEducation() == null : this.getEducation().equals(other.getEducation()))
            && (this.getSchool() == null ? other.getSchool() == null : this.getSchool().equals(other.getSchool()))
            && (this.getFirstClass() == null ? other.getFirstClass() == null : this.getFirstClass().equals(other.getFirstClass()))
            && (this.getMajor() == null ? other.getMajor() == null : this.getMajor().equals(other.getMajor()))
            && (this.getPtCategory() == null ? other.getPtCategory() == null : this.getPtCategory().equals(other.getPtCategory()))
            && (this.getPtInfo() == null ? other.getPtInfo() == null : this.getPtInfo().equals(other.getPtInfo()))
            && (this.getPqCategory() == null ? other.getPqCategory() == null : this.getPqCategory().equals(other.getPqCategory()))
            && (this.getPqInfo() == null ? other.getPqInfo() == null : this.getPqInfo().equals(other.getPqInfo()))
            && (this.getTalentCategory() == null ? other.getTalentCategory() == null : this.getTalentCategory().equals(other.getTalentCategory()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUciId() == null) ? 0 : getUciId().hashCode());
        result = prime * result + ((getTalentId() == null) ? 0 : getTalentId().hashCode());
        result = prime * result + ((getPolitical() == null) ? 0 : getPolitical().hashCode());
        result = prime * result + ((getEducation() == null) ? 0 : getEducation().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getFirstClass() == null) ? 0 : getFirstClass().hashCode());
        result = prime * result + ((getMajor() == null) ? 0 : getMajor().hashCode());
        result = prime * result + ((getPtCategory() == null) ? 0 : getPtCategory().hashCode());
        result = prime * result + ((getPtInfo() == null) ? 0 : getPtInfo().hashCode());
        result = prime * result + ((getPqCategory() == null) ? 0 : getPqCategory().hashCode());
        result = prime * result + ((getPqInfo() == null) ? 0 : getPqInfo().hashCode());
        result = prime * result + ((getTalentCategory() == null) ? 0 : getTalentCategory().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uciId=").append(uciId);
        sb.append(", talentId=").append(talentId);
        sb.append(", political=").append(political);
        sb.append(", education=").append(education);
        sb.append(", school=").append(school);
        sb.append(", firstClass=").append(firstClass);
        sb.append(", major=").append(major);
        sb.append(", ptCategory=").append(ptCategory);
        sb.append(", ptInfo=").append(ptInfo);
        sb.append(", pqCategory=").append(pqCategory);
        sb.append(", pqInfo=").append(pqInfo);
        sb.append(", talentCategory=").append(talentCategory);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}