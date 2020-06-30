package com.talentcard.common.vo;

import lombok.Data;


import java.util.List;

@Data

public class TalentTypeVO {
    private Long cardId;
    private List<Long> categoryList;
    private List<Long> educationList;
    private List<Long> titleList;
    private List<Long> qualityList;
    private List<Long> honourList;
    private Integer education;
    private Integer title;
    private Integer quality;
    private Long talentHonour;
    private String category;
    private String educationString;
    private String titleString;
    private String qualityString;
    private String honourString;
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(cardId == null ? (long)0 : cardId).append("-")
                .append(category == null || category.length() == 0 ? "0" : category).append("-")
                .append(educationString == null || educationString.length() == 0 ? "0" : educationString).append("-")
                .append(titleString == null || titleString.length() == 0 ? "0" : titleString).append("-")
                .append(qualityString == null || qualityString.length() == 0 ? "0" : qualityString).append("-")
                .append(honourString == null || honourString.length() == 0 ? "0" : honourString).append("-");
        return sb.toString();
    }


    public String toMiddleString(){
        String middleTableString;
        if (cardId == null) {
            cardId = (long) 0;
        }
        if (category == null || category.length() == 0) {
            category = "0";
        }
        if (education == null) {
            education = 0;
        }
        if (title == null) {
            title = 0;
        }
        if (quality == null) {
            quality = 0;
        }
        if (talentHonour == null) {
            talentHonour = (long) 0;
        }
        middleTableString = "" + cardId + "-" + category + "-"
                + education + "-" + title + "-" + quality + "-" + talentHonour;
        return middleTableString;
    }
}
