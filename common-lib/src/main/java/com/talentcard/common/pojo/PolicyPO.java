package com.talentcard.common.pojo;

import java.io.Serializable;

/**
 * t_policy
 * @author 
 */
public class PolicyPO implements Serializable {
    private Long policyId;

    private String name;

    private String num;

    private String description;

    private String cards;

    private String categories;

    private String educations;

    /**
     * 1：需要；2：不需要
     */
    private Byte apply;

    private String frequency;

    /**
     * 1：需要；2：不需要；
     */
    private Byte bank;

    /**
     * 1：需要；2：不需要；
     */
    private Byte annex;

    private static final long serialVersionUID = 1L;

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getEducations() {
        return educations;
    }

    public void setEducations(String educations) {
        this.educations = educations;
    }

    public Byte getApply() {
        return apply;
    }

    public void setApply(Byte apply) {
        this.apply = apply;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Byte getBank() {
        return bank;
    }

    public void setBank(Byte bank) {
        this.bank = bank;
    }

    public Byte getAnnex() {
        return annex;
    }

    public void setAnnex(Byte annex) {
        this.annex = annex;
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
        PolicyPO other = (PolicyPO) that;
        return (this.getPolicyId() == null ? other.getPolicyId() == null : this.getPolicyId().equals(other.getPolicyId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCards() == null ? other.getCards() == null : this.getCards().equals(other.getCards()))
            && (this.getCategories() == null ? other.getCategories() == null : this.getCategories().equals(other.getCategories()))
            && (this.getEducations() == null ? other.getEducations() == null : this.getEducations().equals(other.getEducations()))
            && (this.getApply() == null ? other.getApply() == null : this.getApply().equals(other.getApply()))
            && (this.getFrequency() == null ? other.getFrequency() == null : this.getFrequency().equals(other.getFrequency()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getAnnex() == null ? other.getAnnex() == null : this.getAnnex().equals(other.getAnnex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPolicyId() == null) ? 0 : getPolicyId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCards() == null) ? 0 : getCards().hashCode());
        result = prime * result + ((getCategories() == null) ? 0 : getCategories().hashCode());
        result = prime * result + ((getEducations() == null) ? 0 : getEducations().hashCode());
        result = prime * result + ((getApply() == null) ? 0 : getApply().hashCode());
        result = prime * result + ((getFrequency() == null) ? 0 : getFrequency().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getAnnex() == null) ? 0 : getAnnex().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", policyId=").append(policyId);
        sb.append(", name=").append(name);
        sb.append(", num=").append(num);
        sb.append(", description=").append(description);
        sb.append(", cards=").append(cards);
        sb.append(", categories=").append(categories);
        sb.append(", educations=").append(educations);
        sb.append(", apply=").append(apply);
        sb.append(", frequency=").append(frequency);
        sb.append(", bank=").append(bank);
        sb.append(", annex=").append(annex);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}