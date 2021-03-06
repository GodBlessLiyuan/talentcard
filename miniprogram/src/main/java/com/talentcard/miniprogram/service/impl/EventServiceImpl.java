package com.talentcard.miniprogram.service.impl;

import com.talentcard.common.bo.MyEventBO;
import com.talentcard.common.constant.EventConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.EvEventLogPO;
import com.talentcard.common.pojo.EvEventPO;
import com.talentcard.common.pojo.EvEventTalentPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.miniprogram.dto.EventEnrollDTO;
import com.talentcard.miniprogram.service.IEventService;
import com.talentcard.miniprogram.service.ITalentService;
import com.talentcard.miniprogram.vo.EventDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-01 10:30
 * @description
 */
@Service
public class EventServiceImpl implements IEventService {
    @Autowired
    TalentMapper talentMapper;
    @Autowired
    EvEventMapper evEventMapper;
    @Autowired
    EvEventEnjoyMapper evEventEnjoyMapper;
    @Autowired
    EvEventTalentMapper evEventTalentMapper;
    @Autowired
    EvEventLogMapper evEventLogMapper;
    @Autowired
    private ITalentService iTalentService;

    //已报名
    public static final Byte SIGN_UP = 7;
    //未报名
    public static final Byte NO_SIGN_UP = 8;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO enroll(EventEnrollDTO eventEnrollDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(eventEnrollDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventEnrollDTO.getEventId());
        if (evEventPO == null) {
            return new ResultVO(2750);
        }
        Integer checkResult = check(evEventPO, talentPO.getSex());
        if (checkResult != 0) {
            return new ResultVO(checkResult);
        }
        EvEventTalentPO evEventTalentPO = evEventTalentMapper.
                findEnrollEventById(eventEnrollDTO.getOpenId(), eventEnrollDTO.getEventId());
        if (evEventTalentPO != null && evEventTalentPO.getStatus() == 1) {
            return new ResultVO(2755, "当前就是报名状态！");
        }
        //报名过
        if (evEventTalentPO == null) {
            evEventTalentPO = new EvEventTalentPO();
            //新增人才活动表
            evEventTalentPO.setCreateTime(new Date());
            evEventTalentPO.setDr((byte) 1);
            evEventTalentPO.setEventId(eventEnrollDTO.getEventId());
            evEventTalentPO.setOpenId(eventEnrollDTO.getOpenId());
            evEventTalentPO.setTalentId(talentPO.getTalentId());
            evEventTalentPO.setStatus((byte) 1);
            evEventTalentMapper.insertSelective(evEventTalentPO);
        } else {
            //更新人才活动表
            evEventTalentPO.setCreateTime(new Date());
            evEventTalentPO.setDr((byte) 1);
            evEventTalentPO.setEventId(eventEnrollDTO.getEventId());
            evEventTalentPO.setOpenId(eventEnrollDTO.getOpenId());
            evEventTalentPO.setTalentId(talentPO.getTalentId());
            evEventTalentPO.setStatus((byte) 1);
            evEventTalentMapper.updateByPrimaryKeySelective(evEventTalentPO);
        }

        //更新活动表
        if (evEventPO.getIfQuota() == 1) {
            if (talentPO.getSex() == 1) {
                evEventPO.setCurrentMale(evEventPO.getCurrentMale() + 1);
            } else {
                evEventPO.setCurrentFemale(evEventPO.getCurrentFemale() + 1);
            }
        }
        evEventPO.setCurrentNum(evEventPO.getCurrentNum() + 1);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO cancel(Long etId) {
        EvEventTalentPO evEventTalentPO = evEventTalentMapper.selectByPrimaryKey(etId);
        if (evEventTalentPO == null) {
            return new ResultVO(2752, "当前人才未参加此活动，或者状态不对！");
        }
        //判断是否取消
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(evEventTalentPO.getEventId());
        if (evEventPO == null) {
            return new ResultVO(2750);
        }
        if (evEventPO.getStatus() == EventConstant.ADMINCANCEL) {
            String opinion = "";
            EvEventLogPO evEventLogPO = evEventLogMapper.findLastCancelLog(evEventPO.getEventId());
            if (evEventLogPO != null) {
                opinion = evEventLogPO.getOpinion();
            }
            return new ResultVO(2753, opinion);
        }
        //判断活动是否已经开始
        Long currentTime = System.currentTimeMillis();
        Long startTime = evEventPO.getStartTime().getTime();
        if (currentTime >= startTime) {
            return new ResultVO(2754, "活动已经开始！");
        }
        evEventTalentPO.setStatus((byte) 2);
        evEventTalentPO.setDr((byte) 2);
        evEventTalentMapper.updateByPrimaryKeySelective(evEventTalentPO);
        //更新eventPO，人数-1
        if (evEventPO.getIfQuota() == 1) {
            TalentPO talentPO = talentMapper.selectByOpenId(evEventTalentPO.getOpenId());
            if (talentPO == null) {
                return new ResultVO(2500);
            }
            if (talentPO.getSex() == 1) {
                if (evEventPO.getCurrentMale() > 0) {
                    evEventPO.setCurrentMale(evEventPO.getCurrentMale() - 1);
                }
            } else {
                if (evEventPO.getCurrentFemale() > 0) {
                    evEventPO.setCurrentFemale(evEventPO.getCurrentFemale() - 1);
                }
            }
        }
        if (evEventPO.getCurrentNum() > 0) {
            evEventPO.setCurrentNum(evEventPO.getCurrentNum() - 1);
        }
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO check(EventEnrollDTO eventEnrollDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(eventEnrollDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventEnrollDTO.getEventId());
        if (evEventPO == null) {
            return new ResultVO(2750);
        }
        Integer checkResult = check(evEventPO, talentPO.getSex());
        if (checkResult != 0) {
            return new ResultVO(checkResult);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findAllEvent(String openId) {
        /**
         * 获取用户类型
         */
        TalentTypeVO vo = iTalentService.getTalentInfo(openId);
        if (vo == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        List<Long> eventIdList = evEventEnjoyMapper.findAllEventByFactor(vo.getCardId(), vo.getCategoryList(),
                vo.getEducationList(), vo.getTitleList(), vo.getQualityList(), vo.getHonourList());
        if (eventIdList.size() == 0) {
            //查无活动
            return new ResultVO(1000, null);
        }
        //去重
        eventIdList = eventIdList.stream().distinct().collect(Collectors.toList());
        //活动表，查询符合条件的活动和
        List<MyEventBO> myEventBOList = evEventMapper.findAllEvent(eventIdList);
        //状态赋值
        Long startTime;
        Long endTime;
        Byte status;
        Byte upDown;
        long currentTime = System.currentTimeMillis();
        for (MyEventBO myEventBO : myEventBOList) {
            startTime = myEventBO.getStartTime().getTime();
            endTime = myEventBO.getEndTime().getTime();
            status = myEventBO.getStatus();
            upDown = myEventBO.getUpDown();
            Byte actualStatus = (byte) EventConstant.getStatus(currentTime, startTime, endTime, status, upDown);
            myEventBO.setActualStatus(actualStatus);
        }
        return new ResultVO(1000, myEventBOList);
    }

    @Override
    public ResultVO findMyEvent(String openId) {
        List<MyEventBO> myEventBOList = evEventMapper.findMyEvent(openId);
        Long startTime;
        Long endTime;
        Byte status;
        Byte upDown;
        long currentTime = System.currentTimeMillis();
        for (MyEventBO myEventBO : myEventBOList) {
            startTime = myEventBO.getStartTime().getTime();
            endTime = myEventBO.getEndTime().getTime();
            status = myEventBO.getStatus();
            upDown = myEventBO.getUpDown();
            Byte actualStatus = (byte) EventConstant.getStatus(currentTime, startTime, endTime, status, upDown);
            myEventBO.setActualStatus(actualStatus);
        }
        return new ResultVO(1000, myEventBOList);
    }

    @Override
    public ResultVO findOne(String openId, Long eventId) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        //设定状态值
        Long startTime = evEventPO.getStartTime().getTime();
        Long endTime = evEventPO.getEndTime().getTime();
        Byte status = evEventPO.getStatus();
        Byte upDown = evEventPO.getUpDown();

        Byte actualStatus = (byte) EventConstant.getStatus(System.currentTimeMillis(), startTime, endTime, status, upDown);
        evEventPO.setStatus(actualStatus);
        //判断人才参加状态
        EvEventTalentPO evEventTalentPO = evEventTalentMapper.findEnrollEventById(openId, eventId);
        Byte talentStatus;
        Long etId = null;
        //已报名
        if (evEventTalentPO != null && evEventTalentPO.getStatus() == 1) {
            talentStatus = SIGN_UP;
            etId = evEventTalentPO.getEtId();
            //未报名
        } else if (evEventTalentPO != null && evEventTalentPO.getStatus() == 2) {
            talentStatus = NO_SIGN_UP;
            etId = evEventTalentPO.getEtId();
            //未报名
        } else {
            talentStatus = NO_SIGN_UP;
        }
        //查询审批意见
        String opinion = "";
        EvEventLogPO evEventLogPO1 = evEventLogMapper.findLastCancelLog(evEventPO.getEventId());
        if (evEventLogPO1 != null) {
            opinion = evEventLogPO1.getOpinion();
        }
        //构建VO
        EventDetailVO eventDetailVO = new EventDetailVO();
        eventDetailVO.setOpinion(opinion);
        eventDetailVO.setEvEventPO(evEventPO);
        eventDetailVO.setTalentStatus(talentStatus);
        eventDetailVO.setEtId(etId);
        return new ResultVO(1000, eventDetailVO);
    }

    /**
     * 校验
     *
     * @param evEventPO
     * @param sex
     * @return
     */

    public Integer check(EvEventPO evEventPO, Byte sex) {
        if (evEventPO.getUpDown() == 2) {
            return 1001;
        }
        if (evEventPO.getStatus() == 4) {
            return 1002;
        }
        Long currentTime = System.currentTimeMillis();
        Long startTime = evEventPO.getStartTime().getTime();
        Long endTime = evEventPO.getEndTime().getTime();
        if (currentTime >= endTime) {
            return 1003;
        }
        if (currentTime >= startTime) {
            return 1004;
        }
        if (evEventPO.getIfQuota() == 1) {
            if (sex == 1) {
                if (evEventPO.getMaleQuota() <= evEventPO.getCurrentMale()) {
                    return 1005;
                }
            } else {
                if (evEventPO.getFemaleQuota() <= evEventPO.getCurrentFemale()) {
                    return 1006;
                }
            }
        } else {
            if (evEventPO.getEventQuota() <= evEventPO.getCurrentNum()) {
                return 1007;
            }
        }
        return 0;
    }

}
