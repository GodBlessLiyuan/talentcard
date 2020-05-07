package com.talentcard.web.vo;

import com.talentcard.common.bo.RoleAuthorityBO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/5/7 10:40
 * @description: 登录返回数据
 * @version: 1.0
 */
@Data
public class LoginVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String username;
    private RoleAuthorityBO role;
}
