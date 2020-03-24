package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_card_authentication_attachment
 * @author 
 */
@Data
public class AuthenticationAttachment implements Serializable {
    /**
     * 人才卡权限表id
     */
    private Long cardAuthorityId;

    /**
     * 认证表id
     */
    private Long authId;

    /**
     * 职称扫描件
     */
    private String professionalTitleUrl;

    /**
     * 职业资格信息证书扫描件
     */
    private String professionQualification;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date upadateTime;

    private static final long serialVersionUID = 1L;
}