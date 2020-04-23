package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent
 * @author 
 */
@Data
public class TalentPO implements Serializable {
    private Long talentId;

    private String openId;

    private String name;

    /**
     * 1：男；2：女
     */
    private Byte sex;

    private String idCard;

    private String passport;

    private String workUnit;

    private Integer industry;

    private String phone;

    private Date createTime;

    private String category;

    /**
     * 1 正常使用
2 注册未认证
3 认证未审批/待审批
4 已有卡，且审批通过但未领卡
5 未注册
6 失效
用户当前的卡套号，状态2、3为基本卡；状态4为要换的新卡、状态1为当前持有卡；状态5、6无卡
     */
    private Byte status;

    private Long cardId;

    private static final long serialVersionUID = 1L;
}