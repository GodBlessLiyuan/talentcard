package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_card_authority
 * @author 
 */
@Data
public class Authority implements Serializable {
    /**
     * 人才卡权限表id
     */
    private Long cardAuthorityId;

    /**
     * 会员卡类别编号
     */
    private Long cardTypeId;

    /**
     * 权限
     */
    private Byte authority;

    /**
     * 是否删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}