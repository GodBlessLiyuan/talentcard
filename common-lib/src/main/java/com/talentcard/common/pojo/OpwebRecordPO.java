package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * t_opweb_record
 * @author 
 */
public class OpwebRecordPO implements Serializable {
    private Long opId;

    private Long useId;

    private String username;

    private String fristMenu;

    private String secondMenu;

    private String thirdMenu;

    private String detailInfo;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }

    public Long getUseId() {
        return useId;
    }

    public void setUseId(Long useId) {
        this.useId = useId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFristMenu() {
        return fristMenu;
    }

    public void setFristMenu(String fristMenu) {
        this.fristMenu = fristMenu;
    }

    public String getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(String secondMenu) {
        this.secondMenu = secondMenu;
    }

    public String getThirdMenu() {
        return thirdMenu;
    }

    public void setThirdMenu(String thirdMenu) {
        this.thirdMenu = thirdMenu;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        OpwebRecordPO other = (OpwebRecordPO) that;
        return (this.getOpId() == null ? other.getOpId() == null : this.getOpId().equals(other.getOpId()))
            && (this.getUseId() == null ? other.getUseId() == null : this.getUseId().equals(other.getUseId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getFristMenu() == null ? other.getFristMenu() == null : this.getFristMenu().equals(other.getFristMenu()))
            && (this.getSecondMenu() == null ? other.getSecondMenu() == null : this.getSecondMenu().equals(other.getSecondMenu()))
            && (this.getThirdMenu() == null ? other.getThirdMenu() == null : this.getThirdMenu().equals(other.getThirdMenu()))
            && (this.getDetailInfo() == null ? other.getDetailInfo() == null : this.getDetailInfo().equals(other.getDetailInfo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOpId() == null) ? 0 : getOpId().hashCode());
        result = prime * result + ((getUseId() == null) ? 0 : getUseId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getFristMenu() == null) ? 0 : getFristMenu().hashCode());
        result = prime * result + ((getSecondMenu() == null) ? 0 : getSecondMenu().hashCode());
        result = prime * result + ((getThirdMenu() == null) ? 0 : getThirdMenu().hashCode());
        result = prime * result + ((getDetailInfo() == null) ? 0 : getDetailInfo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", opId=").append(opId);
        sb.append(", useId=").append(useId);
        sb.append(", username=").append(username);
        sb.append(", fristMenu=").append(fristMenu);
        sb.append(", secondMenu=").append(secondMenu);
        sb.append(", thirdMenu=").append(thirdMenu);
        sb.append(", detailInfo=").append(detailInfo);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}