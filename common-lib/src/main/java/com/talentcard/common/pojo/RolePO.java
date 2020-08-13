package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_role
 * @author 
 */
@Data
public class RolePO implements Serializable {
    private Long roleId;

    private String name;

    private String extra;

    private Date createTime;

    /**
     * 1. 正常角色；2.政策角色
     */
    private Byte roleType;

    private static final long serialVersionUID = 1L;
}