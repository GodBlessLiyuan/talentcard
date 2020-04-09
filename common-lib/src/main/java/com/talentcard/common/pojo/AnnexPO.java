package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * t_annex
 * @author 
 */
public class AnnexPO implements Serializable {
    private Long annexId;

    private String name;

    private String location;

    private Long paId;

    private static final long serialVersionUID = 1L;

    public Long getAnnexId() {
        return annexId;
    }

    public void setAnnexId(Long annexId) {
        this.annexId = annexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getPaId() {
        return paId;
    }

    public void setPaId(Long paId) {
        this.paId = paId;
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
        AnnexPO other = (AnnexPO) that;
        return (this.getAnnexId() == null ? other.getAnnexId() == null : this.getAnnexId().equals(other.getAnnexId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getPaId() == null ? other.getPaId() == null : this.getPaId().equals(other.getPaId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAnnexId() == null) ? 0 : getAnnexId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getPaId() == null) ? 0 : getPaId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", annexId=").append(annexId);
        sb.append(", name=").append(name);
        sb.append(", location=").append(location);
        sb.append(", paId=").append(paId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}