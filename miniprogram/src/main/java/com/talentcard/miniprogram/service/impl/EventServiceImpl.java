package com.talentcard.miniprogram.service.impl;

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
                evEventPO.setMaleQuota(evEventPO.getMaleQuota() - 1);
            } else {
                evEventPO.setFemaleQuota(evEventPO.getFemaleQuota() - 1);
            }
        }
        evEventPO.setEventQuota(evEventPO.getEventQuota() - 1);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO cancel(Long etId) {
        EvEventTalentPO evEventTalentPO = evEventTalentMapper.selectByPrimaryKey(etId);
        if (evEventTalentPO == null) {
            return new ResultVO(2752, "当前人才未参加此活动，或者状态不对！");
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
        return new ResultVO(1000, null);
    }

    @Override
    public ResultVO findOne(Long eventId) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        return new ResultVO(1000, evEventPO);
    }

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
                if (evEventPO.getMaleQuota() <= 0) {
                    return 1005;
                }
            } else {
                if (evEventPO.getFemaleQuota() <= 0) {
                    return 1006;
                }
            }
        } else {
            if (evEventPO.getEventQuota() <= 0) {
                return 1007;
            }
        }
        return 0;
    }
}
