package com.talentcard.web.vo;

import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 20:01 2020/4/15
 * @version: 1.0.0
 * @description: 审批人的审批反馈意见
 */
@Data
public class ConfirmMsgVO {
    private Byte result;
    private Long cardId;
    private String category;
    private String opinion;
}
