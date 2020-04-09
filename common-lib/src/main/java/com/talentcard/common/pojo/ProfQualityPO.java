package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * t_prof_quality
 * @author 
 */
public class ProfQualityPO implements Serializable {
    private Long pqId;

    private Integer category;

    private String info;

    private String picture;

    private Long certId;

    private static final long serialVersionUID = 1L;

    public Long getPqId() {
        return pqId;
    }

    public void setPqId(Long pqId) {
        this.pqId = pqId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        ProfQualityPO other = (ProfQualityPO) that;
        return (this.getPqId() == null ? other.getPqId() == null : this.getPqId().equals(other.getPqId()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getInfo() == null ? other.getInfo() == null : this.getInfo().equals(other.getInfo()))
            && (this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()))
            && (this.getCertId() == null ? other.getCertId() == null : this.getCertId().equals(other.getCertId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPqId() == null) ? 0 : getPqId().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getInfo() == null) ? 0 : getInfo().hashCode());
        result = prime * result + ((getPicture() == null) ? 0 : getPicture().hashCode());
        result = prime * result + ((getCertId() == null) ? 0 : getCertId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pqId=").append(pqId);
        sb.append(", category=").append(category);
        sb.append(", info=").append(info);
        sb.append(", picture=").append(picture);
        sb.append(", certId=").append(certId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}