package com.talentcard.common.vo;

import lombok.Data;

import java.util.List;

@Data

public class TalentTypeVO {
    private Long cardId;
    private List<Long> categoryList;
    private Integer education;
    private Integer title;
    private Integer quality;
    private Long talentHonour;
    private String category;

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(cardId == null ? (long)0 : cardId).append("-")
                .append(category == null || "".equals(category) ? "0" : category).append("-")
                .append(education == null ? 0 : education).append("-")
                .append(title == null ? 0 : title).append("-")
                .append(quality == null ? 0 : quality).append("-")
                .append(talentHonour == null ? (long)0 : talentHonour).append("-");
        return sb.toString();
    }
}
