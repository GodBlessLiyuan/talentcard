package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_staff
 * @author 
 */
@Data
public class StaffPO implements Serializable {
    private Long staffId;

    private String openId;

    private String name;

    /**
     * 1：男；2：女
     */
    private Byte sex;

    private String idCard;

    private String phone;

    private Date createTime;

    /**
     * 1正在使用
2删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}