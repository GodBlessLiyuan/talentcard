package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.netflix.ribbon.proxy.annotation.Http;
import com.talentcard.common.bo.EvEventLogBO;
import com.talentcard.common.bo.QueryTalentInfoBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.EventDTO;
import com.talentcard.web.service.IEventService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.utils.PolicyNameUtil;
import com.talentcard.web.vo.EventDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-27 14:41
 * @description
 */
@Service
public class EventServiceImpl implements IEventService {
    @Autowired
    EvEventMapper evEventMapper;
    @Autowired
    EvEventEnjoyMapper evEventEnjoyMapper;
    @Autowired
    EvEventTalentMapper evEventTalentMapper;
    @Autowired
    EvEventQueryMapper evEventQueryMapper;
    @Autowired
    EvEventLogMapper evEventLogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private ILogService logService;

    private static final String[] EXPORT_TITLES = {"序号", "姓名", "性别", "工作单位", "手机号码", "人才卡",
            "状态", "报名时间"};

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO add(HttpSession httpSession, EventDTO eventDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //event表
        EvEventPO evEventPO = new EvEventPO();
        evEventPO = EventDTO.setEventPO(evEventPO, eventDTO);
        evEventPO.setUserId(userId);
        evEventPO.setCreateTime(new Date());
        evEventMapper.add(evEventPO);
        //新建enjoy表
        Long eventId = evEventPO.getEventId();
        setEventEnjoy(eventDTO, eventId);
        //新建log表
        EvEventLogPO evEventLogPO = new EvEventLogPO();
        evEventLogPO.setEventId(eventId);
        evEventLogPO.setCreateTime(new Date());
        evEventLogPO.setUserId(userId);
        evEventLogPO.setType((byte) 1);
        evEventLogMapper.insertSelective(evEventLogPO);
        //新建eventQuery表
        EvEventQueryPO evEventQueryPO = new EvEventQueryPO();
        evEventQueryPO.setEventId(eventId);
        evEventQueryPO = EventDTO.setEventQueryPO(evEventQueryPO, eventDTO);
        evEventQueryMapper.add(evEventQueryPO);
        //更新event表：查询表的主键eqId
        evEventPO.setEqId(evEventQueryPO.getEqId());
        evEventMapper.updateByPrimaryKeySelective(evEventPO);

        String eventLog = eventDTO.getName() + "(" + eventDTO.getNum() + ")";
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                , "新增活动\"%s\"", eventLog);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO edit(HttpSession httpSession, EventDTO eventDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        Long eventId = eventDTO.getEventId();
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(2750, "2750：无此后台活动！");
        }
        //更新event表
        evEventPO = EventDTO.setEventPO(evEventPO, eventDTO);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        //删除enjoy表
        evEventEnjoyMapper.deleteByEventId(eventId);
        //更新enjoy表
        setEventEnjoy(eventDTO, eventId);
        //新建log表
        EvEventLogPO evEventLogPO = new EvEventLogPO();
        evEventLogPO.setEventId(eventId);
        evEventLogPO.setCreateTime(new Date());
        evEventLogPO.setUserId(userId);
        evEventLogPO.setType((byte) 1);
        evEventLogMapper.insertSelective(evEventLogPO);
        //更新eventQuery表
        EvEventQueryPO evEventQueryPO = evEventQueryMapper.selectByPrimaryKey(evEventPO.getEqId());
        if (evEventQueryPO == null) {
            return new ResultVO(2751, "活动查询总表查不到相关信息！");
        }
        evEventQueryPO.setEventId(eventId);
        evEventQueryPO = EventDTO.setEventQueryPO(evEventQueryPO, eventDTO);
        evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
        String eventLog = eventDTO.getName() + "(" + eventDTO.getNum() + ")";
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                , "编辑活动\"%s\"", eventLog);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(Long eventId) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(1000, evEventPO);
        }
        EventDetailVO eventDetailVO = EventDetailVO.convert(evEventPO);
        //日志
        List<EvEventLogBO> evEventLogBOList = evEventLogMapper.findByEventId(eventId);
        if (evEventLogBOList.size() != 0) {
            for (EvEventLogBO evEventLogBO : evEventLogBOList) {
                if (evEventLogBO.getType() == 1) {
                    evEventLogBO.setTypeName("发起活动");
                } else if (evEventLogBO.getType() == 2) {
                    evEventLogBO.setTypeName("取消活动");
                } else if (evEventLogBO.getType() == 3) {
                    evEventLogBO.setTypeName("上架");
                } else if (evEventLogBO.getType() == 4) {
                    evEventLogBO.setTypeName("下架");
                }
            }
        }
        eventDetailVO.setEvEventLogBOList(evEventLogBOList);
        //enjoy信息
        List<EvEventEnjoyPO> evEventEnjoyPOList = evEventEnjoyMapper.findByEventId(eventId);
        eventDetailVO = EventDetailVO.setEnjoy(eventDetailVO, evEventEnjoyPOList);
        return new ResultVO(1000, eventDetailVO);
    }

    @Override
    public ResultVO cancel(HttpSession httpSession, Long eventId, String opinion) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(2750, "无此后台活动！");
        }
        evEventPO.setStatus((byte) 10);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        //更新eventQuery表
        EvEventQueryPO evEventQueryPO = evEventQueryMapper.selectByPrimaryKey(evEventPO.getEqId());
        if (evEventQueryPO == null) {
            return new ResultVO(2751, "活动查询总表查不到相关信息！");
        }
        evEventQueryPO.setStatus((byte) 4);
        evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
        //新建log表
        EvEventLogPO evEventLogPO = new EvEventLogPO();
        evEventLogPO.setEventId(eventId);
        evEventLogPO.setCreateTime(new Date());
        evEventLogPO.setUserId(userId);
        evEventLogPO.setType((byte) 2);
        evEventLogPO.setOpinion(opinion);
        evEventLogMapper.insertSelective(evEventLogPO);
        String eventLog = evEventPO.getName() + "(" + evEventPO.getNum() + ")";
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                , "取消活动\"%s\"", eventLog);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upDown(HttpSession httpSession, Long eventId, Byte upDown) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(2750, "无此后台活动！");
        }
        if (upDown == 1) {
            evEventPO.setStatus((byte) 1);
        } else {
            evEventPO.setStatus((byte) 10);
        }
        //更新event表
        evEventPO.setUpDown(upDown);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        //更新eventQuery表
        EvEventQueryPO evEventQueryPO = evEventQueryMapper.selectByPrimaryKey(evEventPO.getEqId());
        if (evEventQueryPO == null) {
            return new ResultVO(2751, "活动查询总表查不到相关信息！");
        }
        evEventQueryPO.setUpDown(upDown);
        evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
        //新建log表
        EvEventLogPO evEventLogPO = new EvEventLogPO();
        evEventLogPO.setEventId(eventId);
        evEventLogPO.setCreateTime(new Date());
        evEventLogPO.setUserId(userId);
        if (upDown == 1) {
            evEventLogPO.setType((byte) 3);
        } else {
            evEventLogPO.setType((byte) 4);
        }
        evEventLogMapper.insertSelective(evEventLogPO);
        //日志
        String eventLog = evEventPO.getName() + "(" + evEventPO.getNum() + ")";
        if (upDown == 1) {
            logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                    , "上架活动\"%s\"", eventLog);
        } else {
            logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                    , "下架活动\"%s\"", eventLog);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryTalentInfo(int pageNum, int pageSize, Long eventId, String name, String workLocation, Byte sex, Byte status) {
        Page<QueryTalentInfoBO> page = PageHelper.startPage(pageNum, pageSize);
        List<QueryTalentInfoBO> queryTalentInfoBOList =
                evEventTalentMapper.queryTalentInfo(eventId, name, workLocation, sex, status);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), queryTalentInfoBOList));
    }

    @Override
    public ResultVO talentInfoExport(int pageNum, int pageSize, Long eventId, String name, String workLocation, Byte sex,
                                     Byte status, HttpServletResponse httpServletResponse) {
        List<QueryTalentInfoBO> queryTalentInfoBOList =
                evEventTalentMapper.queryTalentInfo(eventId, name, workLocation, sex, status);
        String[][] content = buildExcelContents(queryTalentInfoBOList);
        //生成Excel表格
        ExportUtil.exportExcel(null, EXPORT_TITLES, content, httpServletResponse);
        return new ResultVO(1000);
    }

    /**
     * 设置活动享受群体
     *
     * @param eventDTO
     * @param eventId
     */
    public void setEventEnjoy(EventDTO eventDTO, Long eventId) {
        //        新建setting表
        EvEventEnjoyPO evEventEnjoyPO;
        if (eventDTO.getCardId() != null) {
            for (Long cardId : eventDTO.getCardId()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setCardId(cardId);
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 1);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getCategory() != null) {
            for (Long categoryId : eventDTO.getCategory()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setCategory(categoryId);
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 2);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getEducation() != null) {
            for (Integer education : eventDTO.getEducation()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setEducation(education);
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 3);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getTitle() != null) {
            for (Integer title : eventDTO.getTitle()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setTitle(title);
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 4);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getQuality() != null) {
            for (Integer quality : eventDTO.getQuality()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setQuality(quality);
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 5);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getHonour() != null) {
            for (Long honour : eventDTO.getHonour()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setHonour(honour);
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 6);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }

    }

    /**
     * 根据 bos 构建 导出内容
     *
     * @param queryTalentInfoBOList
     * @return
     */
    private String[][] buildExcelContents(List<QueryTalentInfoBO> queryTalentInfoBOList) {
        String[][] contents = new String[queryTalentInfoBOList.size()][];
        int num = 0;
        for (QueryTalentInfoBO queryTalentInfoBO : queryTalentInfoBOList) {
            String[] content = new String[10];
            content[0] = String.valueOf(num + 1);
            content[1] = queryTalentInfoBO.getName();
            content[2] = queryTalentInfoBO.getSex() == 1 ? "男" : "女";
            content[3] = queryTalentInfoBO.getWorkLocation();
            content[4] = queryTalentInfoBO.getPhone();
            content[5] = queryTalentInfoBO.getTalentCard();
            content[6] = queryTalentInfoBO.getStatus() == 1 ? "已报名" : "已取消";
            content[7] = DateUtil.date2Str(queryTalentInfoBO.getCreateTime(), DateUtil.YMD_HMS);
            contents[num++] = content;
        }
        return contents;
    }


}
