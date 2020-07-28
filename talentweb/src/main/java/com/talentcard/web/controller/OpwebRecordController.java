package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IOpwebRecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author: wangdaohang
 * @date: Created in 2020/7/27 19:45
 * @description:
 * @version: 1.0
 */

@RequestMapping("action")
@RestController
public class OpwebRecordController {

    @Autowired
    private IOpwebRecordService opwebRecordService;

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param start
     * @param end
     * @return
     */
    @PostMapping("query")
    private ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(value = "userName", defaultValue = "") String userName,
                           @RequestParam(value = "start", defaultValue = "") String start,
                           @RequestParam(value = "end", defaultValue = "") String end) {
        HashMap<String, Object> reqMap = new HashMap<>(3);
        if(!StringUtils.isEmpty(userName)){
            reqMap.put("userName", userName.replaceAll(" ", ""));
        }

        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
            reqMap.put("end", end);
        }
        if(!StringUtils.isEmpty(start)){
            start = start + " 00:00:00";
            reqMap.put("start", start);
        }

        return opwebRecordService.query(pageNum, pageSize, reqMap);

    }
}
