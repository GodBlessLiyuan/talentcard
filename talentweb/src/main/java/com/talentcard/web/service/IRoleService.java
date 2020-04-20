package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:11 2020/4/13
 * @version: 1.0.0
 * @description:
 */
public interface IRoleService {
    /**
     * 根据角色名和角色创建时间的开始结束时间间隔进行角色用户的查询
     * @param roleName
     * @param startTime
     * @param endTime
     * @return
     */
    ResultVO queryByRole(String roleName, String startTime, String endTime);

}
