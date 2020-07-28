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
     * @param user_name
     * @param first_menu
     * @param second_menu
     * @param start_time
     * @param end_time
     * @return
     */
    @PostMapping("query")
    private ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(value = "user_name", required = false, defaultValue = "") String user_name,
                           @RequestParam(value = "first_menu", required = false, defaultValue = "") String first_menu,
                           @RequestParam(value = "second_menu", required = false, defaultValue = "") String second_menu,
                           @RequestParam(value = "start_time", required = false, defaultValue = "") String start_time,
                           @RequestParam(value = "end_time", required = false, defaultValue = "") String end_time) {
        HashMap<String, Object> reqMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(user_name)) {
            reqMap.put("user_name", user_name.replaceAll(" ", ""));
        }
        reqMap.put("first_menu", first_menu);
        reqMap.put("second_menu", second_menu);
        if (!StringUtils.isEmpty(end_time)) {
            end_time = end_time + " 23:59:59";
            reqMap.put("end_time", end_time);
        }
        if (!StringUtils.isEmpty(start_time)) {
            start_time = start_time + " 00:00:00";
            reqMap.put("start_time", start_time);
        }

        return opwebRecordService.query(pageNum, pageSize, reqMap);

    }
}
