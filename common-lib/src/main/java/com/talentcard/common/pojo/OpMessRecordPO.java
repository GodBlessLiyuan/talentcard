package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * op_mess_record
 * @author 
 */
@Data
public class OpMessRecordPO implements Serializable {
    private Long id;

    /**
     * 一键推送id
     */
    private Long sendId;

    /**
     * 人才ID
     */
    private Long talentId;

    /**
     * 人才的openId
     */
    private String openId;

    /**
     * 0：发送成功；43004：未关注公众号
     */
    private Integer status;

    private static final long serialVersionUID = 1L;


}