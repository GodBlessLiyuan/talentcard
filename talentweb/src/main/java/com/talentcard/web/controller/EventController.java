package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EventDTO;
import com.talentcard.web.service.IEventService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-27 14:47
 * @description
 */
@RestController
@RequestMapping("event")
public class EventController {
    @Autowired
    private IEventService iEventService;

    /**
     * 新增
     *
     * @param httpSession
     * @param eventDTO
     * @return
     */
    @RequestMapping("add")
    public ResultVO add(HttpSession httpSession, @RequestBody EventDTO eventDTO) {
        return iEventService.add(httpSession, eventDTO);
    }

    /**
     * 编辑
     *
     * @param httpSession
     * @param eventDTO
     * @return
     */
    @RequestMapping("edit")
    public ResultVO edit(HttpSession httpSession, @RequestBody EventDTO eventDTO) {
        return iEventService.edit(httpSession, eventDTO);
    }

    /**
     * 查看详情
     *
     * @param eventId
     * @return
     */
    @RequestMapping("findOne")
    public ResultVO findOne(@RequestParam("eventId") Long eventId) {
        return iEventService.findOne(eventId);
    }

    /**
     * 撤回取消
     *
     * @param eventId
     * @return
     */
    @RequestMapping("cancel")
    public ResultVO cancel(HttpSession httpSession,
                           @RequestParam("eventId") Long eventId,
                           @RequestParam("opinion") String opinion) {
        return iEventService.cancel(httpSession, eventId, opinion);
    }

    /**
     * 上下架
     *
     * @param eventId
     * @param upDown
     * @return
     */
    @RequestMapping("upDown")
    public ResultVO upDown(HttpSession httpSession,
                           @RequestParam("eventId") Long eventId,
                           @RequestParam("upDown") Byte upDown) {
        return iEventService.upDown(httpSession, eventId, upDown);
    }

    @RequestMapping("talentInfoQuery")
    public ResultVO talentInfoQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "eventId") Long eventId,
                                    @RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "workLocation", defaultValue = "") String workLocation,
                                    @RequestParam(value = "sex", required = false) Byte sex,
                                    @RequestParam(value = "status", required = false) Byte status) {
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll(" ", "");
        }
        if (!StringUtils.isEmpty(workLocation)) {
            workLocation = workLocation.replaceAll(" ", "");
        }
        return iEventService.queryTalentInfo(pageNum, pageSize, eventId, name, workLocation, sex, status);
    }

    @RequestMapping("talentInfoExport")
    public ResultVO talentInfoExport(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "eventId") Long eventId,
                                     @RequestParam(value = "name", defaultValue = "") String name,
                                     @RequestParam(value = "workLocation", defaultValue = "") String workLocation,
                                     @RequestParam(value = "sex", required = false) Byte sex,
                                     @RequestParam(value = "status", required = false) Byte status,
                                     HttpServletResponse httpServletResponse) {
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll(" ", "");
        }
        if (!StringUtils.isEmpty(workLocation)) {
            workLocation = workLocation.replaceAll(" ", "");
        }
        return iEventService.talentInfoExport(pageNum, pageSize, eventId, name, workLocation, sex, status, httpServletResponse);
    }


    /**
     * 根据id查询当前活动所占用的时间
     *
     * @param map
     * @return
     */
    @RequestMapping("findEventTime")
    public ResultVO findEventTime(@RequestBody Map<String, Object> map) {
        return iEventService.findEventTime(map);
    }
}
