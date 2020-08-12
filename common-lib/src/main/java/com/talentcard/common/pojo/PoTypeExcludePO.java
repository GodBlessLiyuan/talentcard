package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * po_type_exclude
 * @author 
 */
public class PoTypeExcludePO implements Serializable {
    /**
     * 互斥id
     */
    private Long excludeId;

    /**
     * 政策类型id1
     */
    private Long pTid1;

    /**
     * 政策类型id2
     */
    private Long pTid2;

    private static final long serialVersionUID = 1L;

    public Long getExcludeId() {
        return excludeId;
    }

    public void setExcludeId(Long excludeId) {
        this.excludeId = excludeId;
    }

    public Long getpTid1() {
        return pTid1;
    }

    public void setpTid1(Long pTid1) {
        this.pTid1 = pTid1;
    }

    public Long getpTid2() {
        return pTid2;
    }

    public void setpTid2(Long pTid2) {
        this.pTid2 = pTid2;
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
        PoTypeExcludePO other = (PoTypeExcludePO) that;
        return (this.getExcludeId() == null ? other.getExcludeId() == null : this.getExcludeId().equals(other.getExcludeId()))
            && (this.getpTid1() == null ? other.getpTid1() == null : this.getpTid1().equals(other.getpTid1()))
            && (this.getpTid2() == null ? other.getpTid2() == null : this.getpTid2().equals(other.getpTid2()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExcludeId() == null) ? 0 : getExcludeId().hashCode());
        result = prime * result + ((getpTid1() == null) ? 0 : getpTid1().hashCode());
        result = prime * result + ((getpTid2() == null) ? 0 : getpTid2().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", excludeId=").append(excludeId);
        sb.append(", pTid1=").append(pTid1);
        sb.append(", pTid2=").append(pTid2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}