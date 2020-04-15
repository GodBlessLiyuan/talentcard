package com.talentcard.web.service;

import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.vo.ResultVO;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:43 2020/4/13
 * @version: 1.0.0
 * @description:
 */
public interface IAuthorityService {
    ResultVO updateAuthority(String roleName, String createTime, RoleAuthorityBO roleAuthorityBO);
}
