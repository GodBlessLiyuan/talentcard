package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EventDTO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-27 14:40
 * @description
 */
public interface IEventService {
    /**
     * 新增
     *
     * @param httpSession
     * @param eventDTO
     * @return
     */
    ResultVO add(HttpSession httpSession, EventDTO eventDTO);

    /**
     * 编辑
     *
     * @param httpSession
     * @param eventDTO
     * @return
     */
    ResultVO edit(HttpSession httpSession, EventDTO eventDTO);

    /**
     * 查找详情
     *
     * @param eventId
     * @return
     */
    ResultVO findOne(Long eventId);

    /**
     * 取消
     *
     * @param eventId
     * @return
     */
    ResultVO cancel(Long eventId);

    /**
     * 上下架
     *
     * @param eventId
     * @return
     */
    ResultVO upDown(Long eventId, Byte upDown);

    /**
     * 人才信息查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param workLocation
     * @param sex
     * @param status
     * @return
     */
    ResultVO queryTalentInfo(int pageNum, int pageSize, String name, String workLocation, Byte sex, Byte status);

    /**
     * 人才信息查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param workLocation
     * @param sex
     * @param status
     * @return
     */
    ResultVO talentInfoExport(int pageNum, int pageSize, String name, String workLocation, Byte sex, Byte status, HttpServletResponse httpServletResponse);
}
