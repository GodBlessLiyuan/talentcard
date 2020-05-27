package com.talentcard.front.vo;

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

}
