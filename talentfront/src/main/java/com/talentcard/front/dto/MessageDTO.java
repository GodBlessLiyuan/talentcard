package com.talentcard.front.dto;

import lombok.Data;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-25 9:16
 * @description: TODO
 * @version: 1.0
 */
@Data
public class MessageDTO {

    /**
     *
     */
    private String openid;

    /**
     * 开头
     */
    private String first;

    /**
     * 姓名
     */
    private String keyword1;

    /**
     * 身份证号
     */
    private String keyword2;

    /**
     * 领卡机构
     */
    private String keyword3;

    /**
     * 通知时间
     */
    private String keyword4;

    /**
     * 结束
     */
    private String remark;




}
