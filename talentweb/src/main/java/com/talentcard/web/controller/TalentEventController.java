package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITalentEventService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-31 14:46
 * @description
 */
@RestController
@RequestMapping("talentEvent")
public class TalentEventController {
    @Autowired
    private ITalentEventService iTalentEventService;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "type", required = false) Byte type,
                          @RequestParam(value = "status", required = false) Byte status) {
        if (!StringUtils.isEmpty(name)) {
            name = name.replaceAll(" ", "");
        }
        return iTalentEventService.query(pageNum, pageSize, name, type, status);
    }
}
