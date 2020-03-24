package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_card_user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 微信唯一标识
     */
    private String wechatId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String identityNumber;

    /**
     * 护照号
     */
    private String passportId;

    /**
     * 所在行业
     */
    private Short industry;

    /**
     * 工作单位
     */
    private String workUnit;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date upadateTime;

    /**
     * 是否删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}