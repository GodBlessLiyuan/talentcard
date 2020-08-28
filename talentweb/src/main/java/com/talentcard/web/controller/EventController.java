package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EventDTO;
import com.talentcard.web.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

    @PostMapping("add")
    public ResultVO add(HttpSession httpSession, @RequestBody EventDTO eventDTO) {
        return iEventService.add(httpSession, eventDTO);
    }
}
