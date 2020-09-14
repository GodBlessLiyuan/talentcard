package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvEventLogBO;
import com.talentcard.common.bo.QueryTalentInfoBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.StringSortUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.EventDTO;
import com.talentcard.web.service.IEventService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.vo.EventDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

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
    EvEventTimeMapper evEventTimeMapper;
    @Autowired
    private ILogService logService;
    @Autowired
    private IWxOfficalAccountService iWxOfficalAccountService;

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

        /**
         * 活动重名
         */
        Map<String, Object> map = new HashMap<>(1);
        map.put("num", eventDTO.getNum());
        List<EvEventPO> list = evEventMapper.query(map);
        if (list != null && list.size() > 0) {
            return new ResultVO(2801);
        }
        //event表
        EvEventPO evEventPO = new EvEventPO();
        evEventPO = EventDTO.setEventPO(evEventPO, eventDTO);
        evEventPO.setUserId(userId);
        evEventPO.setCreateTime(new Date());
        evEventPO.setCurrentNum(0);
        evEventPO.setCurrentMale(0);
        evEventPO.setCurrentFemale(0);
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
        //更新time表
        addEventUpdateEventTime(eventDTO, null);
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

        /**
         * 活动重名
         */
        Map<String, Object> map = new HashMap<>(1);
        map.put("num", eventDTO.getNum());
        List<EvEventPO> list = evEventMapper.query(map);
        if (list != null && list.size() > 1) {
            return new ResultVO(2801);
        }

        String oldTimeInterval = evEventPO.getTimeInterval();
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
        //更新time表
        addEventUpdateEventTime(eventDTO, oldTimeInterval);
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
    @Transactional(rollbackFor = Exception.class)
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
        evEventPO.setStatus((byte) 4);
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

        Map<String, Object> map = new HashMap<>(1);
        map.put("eventId", eventId);
        map.put("status", 1);
        List<EvEventTalentPO> list = evEventTalentMapper.query(map);
        for (EvEventTalentPO po : list) {
            iWxOfficalAccountService.messToBackEndEventCancel(po.getOpenId(), po.getEventId(), evEventPO.getName(), opinion);
        }
//
        //更新time表
        cancelEventUpdateEventTime(evEventPO);
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

    @Override
    public ResultVO findEventTime(Map<String, Object> map) {
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);
        if (map.containsKey("eventId")) {
            Object eventId = map.get("eventId");
            if (eventId != null) {

            }
        }
        return new ResultVO(1000, evEventTimePO);
    }

    /**
     * 设置活动享受群体
     *
     * @param eventDTO
     * @param eventId
     */
    public void setEventEnjoy(EventDTO eventDTO, Long eventId) {
        // 新建enjoy表
        EvEventEnjoyPO evEventEnjoyPO;
        if (eventDTO.getCard() != null) {
            for (Long cardId : eventDTO.getCard()) {
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

    /**
     * 新增/编辑活动时，更新活动场地时间表
     *
     * @param eventDTO
     */
    public void addEventUpdateEventTime(EventDTO eventDTO, String oldTimeInterval) {
        //根据活动场地和活动的日期进行活动场地占用情况的插入或更新
        //判断是插入还是更新
        //如果根据活动场地和日期没有查到数据，则进行插入操作,否则进行时间段合并进行更新
        Map<String, Object> map = new HashMap(2);
        map.put("efid", eventDTO.getEventField());
        map.put("date", eventDTO.getDate());
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);

        if (evEventTimePO == null) {
            EvEventTimePO evEventTimePO1 = new EvEventTimePO();
            evEventTimePO1.setEfId(eventDTO.getEventField());
            try {
                evEventTimePO1.setPlaceDate(DateUtil.YMD.parse(eventDTO.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            evEventTimePO1.setTimeInterval(eventDTO.getTime());
            evEventTimeMapper.insert(evEventTimePO1);
        } else {
            /**
             * 重新计算占用场地时间
             */
            String newTimeinterval = StringSortUtil.sort(evEventTimePO.getTimeInterval(), oldTimeInterval, eventDTO.getTime());
            EvEventTimePO evEventTimePO2 = new EvEventTimePO();
            evEventTimePO2.setId(evEventTimePO.getId());
            evEventTimePO2.setTimeInterval(newTimeinterval);
            evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO2);
        }

    }

    /**
     * 取消活动时，更新活动场地时间表
     *
     * @param evEventPO
     */
    public void cancelEventUpdateEventTime(EvEventPO evEventPO) {
        //取消活动后释放场地占用时间段
        //根据场地和日期查询所有占用的时间段
        Map<String, Object> reqData = new HashMap<>(2);
        reqData.put("efid", evEventPO.getEfId());
        reqData.put("date", evEventPO.getDate());
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(reqData);
        if (evEventTimePO == null) {
            return;
        }

        String newInterval = StringSortUtil.sort(evEventTimePO.getTimeInterval(), evEventPO.getTime(), "");
        //将新的时间段更新会时间占用表中
        evEventTimePO.setTimeInterval(newInterval);
        evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);

    }

}
