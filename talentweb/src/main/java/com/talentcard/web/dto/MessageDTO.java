package com.talentcard.web.dto;

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
     * 时间
     */
    private String keyword2;

    /**
     * 审核状态
     */
    private String keyword3;

    /**
     * 原因说明
     */
    private String keyword4;

    /**
     * 结束
     */
    private String remark;

    /**
     * url
     */
    private String url;


    /**
     * 模版编号
     * 1是领卡
     * 2是驳回
     */

    private Integer templateId;
}
