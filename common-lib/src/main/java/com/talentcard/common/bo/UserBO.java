package com.talentcard.common.bo;

import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:26 2020/4/10
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserBO {

    private String username;

    private String name;

    private String password;

    private String extra;

    private Long roleId;

}
