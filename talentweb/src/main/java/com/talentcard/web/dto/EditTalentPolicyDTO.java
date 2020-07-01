package com.talentcard.web.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-30 19:40
 * @description
 */
@Data
public class EditTalentPolicyDTO {
    private Long cardId;
    private List<Integer> educationList;
    private List<Integer> titleList ;
    private List<Integer> qualityList ;
    private List<Long> honourList;
    private String category;
}
