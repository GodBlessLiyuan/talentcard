package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.EvEventEnjoyPO;
import com.talentcard.common.pojo.EvEventLogPO;
import com.talentcard.common.pojo.EvEventPO;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EventDTO;
import com.talentcard.web.service.IEventService;
import com.talentcard.web.vo.EventDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
    EvEventLogMapper evEventLogMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO add(HttpSession httpSession, EventDTO eventDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        UserPO userPO = userMapper.selectByPrimaryKey(userId);
        if (userPO == null) {
            return new ResultVO(2741, "无此用户！");
        }
        //event表
        EvEventPO evEventPO = new EvEventPO();
        evEventPO = EventDTO.dtoConvertPo(evEventPO, eventDTO);
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
        return new ResultVO(1000);
    }

    @Override
    public ResultVO edit(HttpSession httpSession, EventDTO eventDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        UserPO userPO = userMapper.selectByPrimaryKey(userId);
        if (userPO == null) {
            return new ResultVO(2741, "无此用户！");
        }
        Long eventId = eventDTO.getEventId();
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(2750, "2750：无此后台活动！");
        }
        //更新event表
        evEventPO = EventDTO.dtoConvertPo(evEventPO, eventDTO);
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
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(Long eventId) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        EventDetailVO eventDetailVO = EventDetailVO.convert(evEventPO);
        //日志
        List<EvEventLogPO> evEventLogPOList = evEventLogMapper.findByEventId(eventId);
        eventDetailVO.setEvEventLogPOList(evEventLogPOList);
        return new ResultVO(1000, eventDetailVO);
    }

    @Override
    public ResultVO cancel(Long eventId) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(2750, "无此后台活动！");
        }
        evEventPO.setStatus((byte) 10);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upDown(Long eventId, Byte upDown) {
        EvEventPO evEventPO = evEventMapper.selectByPrimaryKey(eventId);
        if (evEventPO == null) {
            return new ResultVO(2750, "无此后台活动！");
        }
        evEventPO.setStatus((byte) 10);
        evEventPO.setUpDown(upDown);
        evEventMapper.updateByPrimaryKeySelective(evEventPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryTalentInfo(int pageNum, int pageSize, String name, String workLocation, Byte sex, Byte status) {
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
            for (String cardId : eventDTO.getCardId()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setCardId(Long.parseLong(cardId));
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 1);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getCategory() != null) {
            for (String categoryId : eventDTO.getCategory()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setCategory(Long.parseLong(categoryId));
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 2);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getEducation() != null) {
            for (String education : eventDTO.getEducation()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setEducation(Integer.parseInt(education));
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 3);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getTitle() != null) {
            for (String title : eventDTO.getTitle()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setTitle(Integer.parseInt(title));
                evEventEnjoyPO.setEventId(eventId);
                evEventEnjoyPO.setType((byte) 4);
                evEventEnjoyMapper.insert(evEventEnjoyPO);
            }
        }
        if (eventDTO.getQualityIds() != null) {
            for (String quality : eventDTO.getQualityIds()) {
                evEventEnjoyPO = new EvEventEnjoyPO();
                evEventEnjoyPO.setQuality(Integer.parseInt(quality));
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

}
