package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * t_card
 * @author 
 */
public class CardPO implements Serializable {
    private Long cardId;

    private String title;

    private String name;

    private String initialNum;

    private Long currNum;

    private String description;

    private String picture;

    private Date createTime;

    /**
     * 1：默认；2：非默认
     */
    private Byte status;

    private static final long serialVersionUID = 1L;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitialNum() {
        return initialNum;
    }

    public void setInitialNum(String initialNum) {
        this.initialNum = initialNum;
    }

    public Long getCurrNum() {
        return currNum;
    }

    public void setCurrNum(Long currNum) {
        this.currNum = currNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        CardPO other = (CardPO) that;
        return (this.getCardId() == null ? other.getCardId() == null : this.getCardId().equals(other.getCardId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getInitialNum() == null ? other.getInitialNum() == null : this.getInitialNum().equals(other.getInitialNum()))
            && (this.getCurrNum() == null ? other.getCurrNum() == null : this.getCurrNum().equals(other.getCurrNum()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCardId() == null) ? 0 : getCardId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getInitialNum() == null) ? 0 : getInitialNum().hashCode());
        result = prime * result + ((getCurrNum() == null) ? 0 : getCurrNum().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPicture() == null) ? 0 : getPicture().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cardId=").append(cardId);
        sb.append(", title=").append(title);
        sb.append(", name=").append(name);
        sb.append(", initialNum=").append(initialNum);
        sb.append(", currNum=").append(currNum);
        sb.append(", description=").append(description);
        sb.append(", picture=").append(picture);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}