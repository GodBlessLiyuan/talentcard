package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IInsertCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-24 10:37
 * @description
 */
@RequestMapping("insertCertification")
@RestController
public class InsertCertificationController {
    @Autowired
    IInsertCertificationService iInsertCertificationService;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "name", required = false, defaultValue = "") String name,
                          @RequestParam(value = "sex", required = false) Byte sex,
                          @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                          @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime) {
        HashMap<String, Object> hashMap = new HashMap<>(4);
        if (!startTime.equals("")) {
            startTime = startTime + " 00:00:00";
        }
        if (!endTime.equals("")) {
            endTime = endTime + " 23:59:59";
        }

        hashMap.put("name", name);
        hashMap.put("sex", sex);
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        return iInsertCertificationService.query(pageNum, pageSize, hashMap);
    }

}
