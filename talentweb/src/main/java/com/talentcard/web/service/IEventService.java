package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EventDTO;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-27 14:40
 * @description
 */
public interface IEventService {
    ResultVO add(HttpSession httpSession, EventDTO eventDTO);
}
