package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.EvEventEnjoyMapper;
import com.talentcard.common.mapper.EvEventMapper;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.EvEventPO;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EventDTO;
import com.talentcard.web.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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
    UserMapper userMapper;

    @Override
    public ResultVO add(HttpSession httpSession, EventDTO eventDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        UserPO userPO = userMapper.selectByPrimaryKey(userId);
        if (userPO == null) {
            return new ResultVO(2741, "无此角色！");
        }
        EvEventPO evEventPO = new EvEventPO();
        evEventPO = EventDTO.dtoConvertPo(evEventPO, eventDTO);
        evEventMapper.insertSelective(evEventPO);
        return new ResultVO(1000);
    }


}
