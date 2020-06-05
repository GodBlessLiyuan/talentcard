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

    private String driverCard;

    /**
     * 1身份证2护照3驾照
     */
    private Byte cardType;

    private String workUnit;

    private Integer industry;

    private Integer industrySecond;

    private String phone;

    private Byte political;

    private String category;

    private String workLocation;

    /**
     * 1国内；
2海外；
     */
    private Byte workLocationType;

    private Long cardId;

    /**
     * 1 认证通过
2 认证没通过
     */
    private Byte status;

    private Date createTime;

    /**
     * 1正在使用
2删除
     */
    private Byte dr;

    private String unionId;

    private static final long serialVersionUID = 1L;
}