package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * t_education
 * @author 
 */
public class EducationPO implements Serializable {
    private Long educId;

    private Integer eduction;

    private String school;

    /**
     * 1：是；2：否
     */
    private Byte fristClass;

    private String major;

    private String educPicture;

    private Long certId;

    private static final long serialVersionUID = 1L;

    public Long getEducId() {
        return educId;
    }

    public void setEducId(Long educId) {
        this.educId = educId;
    }

    public Integer getEduction() {
        return eduction;
    }

    public void setEduction(Integer eduction) {
        this.eduction = eduction;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Byte getFristClass() {
        return fristClass;
    }

    public void setFristClass(Byte fristClass) {
        this.fristClass = fristClass;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducPicture() {
        return educPicture;
    }

    public void setEducPicture(String educPicture) {
        this.educPicture = educPicture;
    }

    public Long getCertId() {
        return certId;
    }

    public void setCertId(Long certId) {
        this.certId = certId;
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
        EducationPO other = (EducationPO) that;
        return (this.getEducId() == null ? other.getEducId() == null : this.getEducId().equals(other.getEducId()))
            && (this.getEduction() == null ? other.getEduction() == null : this.getEduction().equals(other.getEduction()))
            && (this.getSchool() == null ? other.getSchool() == null : this.getSchool().equals(other.getSchool()))
            && (this.getFristClass() == null ? other.getFristClass() == null : this.getFristClass().equals(other.getFristClass()))
            && (this.getMajor() == null ? other.getMajor() == null : this.getMajor().equals(other.getMajor()))
            && (this.getEducPicture() == null ? other.getEducPicture() == null : this.getEducPicture().equals(other.getEducPicture()))
            && (this.getCertId() == null ? other.getCertId() == null : this.getCertId().equals(other.getCertId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEducId() == null) ? 0 : getEducId().hashCode());
        result = prime * result + ((getEduction() == null) ? 0 : getEduction().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getFristClass() == null) ? 0 : getFristClass().hashCode());
        result = prime * result + ((getMajor() == null) ? 0 : getMajor().hashCode());
        result = prime * result + ((getEducPicture() == null) ? 0 : getEducPicture().hashCode());
        result = prime * result + ((getCertId() == null) ? 0 : getCertId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", educId=").append(educId);
        sb.append(", eduction=").append(eduction);
        sb.append(", school=").append(school);
        sb.append(", fristClass=").append(fristClass);
        sb.append(", major=").append(major);
        sb.append(", educPicture=").append(educPicture);
        sb.append(", certId=").append(certId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}