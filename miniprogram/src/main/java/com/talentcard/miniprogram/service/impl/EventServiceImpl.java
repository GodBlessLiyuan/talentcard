package com.talentcard.miniprogram.service.impl;

import com.talentcard.common.bo.MyEventBO;
import com.talentcard.common.mapper.EvEventMapper;
import com.talentcard.common.mapper.EvEventTalentMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.EvEventPO;
import com.talentcard.common.pojo.EvEventTalentPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.EventEnrollDTO;
import com.talentcard.miniprogram.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    EvEventTalentMapper evEventTalentMapper;
    //报名已结束
    public static final Byte SIGN_UP_END = 2;
    //已结束
    public static final Byte END = 3;
    //已下架
    public static final Byte DOWN = 4;
    //已取消
    public static final Byte CANCEL = 5;
    //报名中
    public static final Byte SIGN_UP_IN_PROGRESS = 6;
    //已报名
    public static final Byte SIGN_UP = 7;
    //未报名
    public static final Byte NO_SIGN_UP = 8;
    //异常状态
    public static final Byte ERROR_STATUS = 10;

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
        //新增人才表
        EvEventTalentPO evEventTalentPO = new EvEventTalentPO();
        evEventTalentPO.setCreateTime(new Date());
        evEventTalentPO.setDr((byte) 1);
        evEventTalentPO.setEventId(eventEnrollDTO.getEventId());
        evEventTalentPO.setOpenId(eventEnrollDTO.getOpenId());
        evEventTalentPO.setTalentId(talentPO.getTalentId());
        evEventTalentPO.setStatus((byte) 1);
        evEventTalentMapper.insertSelective(evEventTalentPO);
        //更新活动表
        if (evEventPO.getIfQuota() == 1) {
            if (talentPO.getSex() == 1) {
                evEventPO.setMaleQuota(evEventPO.getCurrentMale() + 1);
            } else {
                evEventPO.setFemaleQuota(evEventPO.getCurrentFemale() + 1);
            }
        }
        evEventPO.setEventQuota(evEventPO.getCurrentNum() + 1);
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
        if (evEventPO.getStatus() == 4) {
            return new ResultVO(2753, "活动已经取消！");
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
        return null;
    }

    @Override
    public ResultVO findMyEvent(String openId) {
        List<MyEventBO> myEventBOList = evEventMapper.findMyEvent(openId);
        Long startTime;
        Long endTime;
        Byte status;
        Byte upDown;
        for (MyEventBO myEventBO : myEventBOList) {
            startTime = myEventBO.getStartTime().getTime();
            endTime = myEventBO.getEndTime().getTime();
            status = myEventBO.getStatus();
            upDown = myEventBO.getUpDown();
            Byte actualStatus = getActualStatus(startTime, endTime, status, upDown);
            myEventBO.setActualStatus(actualStatus);
        }
        return new ResultVO(1000, null);
    }

    @Override
    public ResultVO findOne(String openId, Long eventId) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        //设定状态值
        Long startTime = evEventPO.getStartTime().getTime();
        Long endTime = evEventPO.getEndTime().getTime();
        Byte status = evEventPO.getStatus();
        Byte upDown = evEventPO.getUpDown();
        Byte actualStatus = getActualStatus(startTime, endTime, status, upDown);
        if (actualStatus.equals(SIGN_UP_IN_PROGRESS)) {
            Integer checkIfEnrollEvent = evEventTalentMapper.checkIfEnrollEvent(openId, eventId);
            if (checkIfEnrollEvent == 0) {
                actualStatus = NO_SIGN_UP;
            } else {
                actualStatus = SIGN_UP;
            }
        }
        evEventPO.setStatus(actualStatus);
        return new ResultVO(1000, evEventPO);
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

    /**
     * 根据条件判断实际状态值
     *
     * @param startTime
     * @param endTime
     * @param status
     * @param upDown
     * @return
     */
    public Byte getActualStatus(Long startTime, Long endTime, Byte status, Byte upDown) {
        Long currentTime = System.currentTimeMillis();
        //报名已结束，前台、后台；
        if (currentTime >= startTime && currentTime < endTime
                && status == 2 && upDown == 1) {
            return SIGN_UP_END;
            //已结束，前台、后台；
        } else if (status == 2 && endTime <= currentTime) {
            return END;
            //已下架，后台；
        } else if (upDown == 2) {
            return DOWN;
            //已取消，前台、后台；
        } else if (status == 4 || status == 5) {
            return CANCEL;
            //报名中，后台；
        } else if (status == 2 && currentTime < startTime) {
            return SIGN_UP_IN_PROGRESS;
        }
        return ERROR_STATUS;
    }
}
