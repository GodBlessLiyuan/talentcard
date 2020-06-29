package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:00 2020/4/15
 * @version: 1.0.0
 * @description: 用户认证表信息
 */
@RestController
@RequestMapping("cert")
public class CertController {
    @Resource
    ICertService certService;

    @RequestMapping("queryCertStatus")
    public ResultVO queryCertStatus(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "sex", required = false) Byte sex,
                                    @RequestParam(value = "pqCategory", required = false) Integer pqCategory,
                                    @RequestParam(value = "ptCategory", required = false) Integer ptCategory,
                                    @RequestParam(value = "education", required = false) Integer education,
                                    @RequestParam(value = "honour", required = false) Integer honour,
                                    @RequestParam(value = "result", required = false) String result,
                                    @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                                    @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime) {
        if (!"".equals(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        if (!"".equals(endTime)) {
            endTime = endTime + " 23:59:59";
        }
        HashMap<String, Object> hashMap = new HashMap<>(9);
        hashMap.put("name", name);
        hashMap.put("sex", sex);
        hashMap.put("pqCategory", pqCategory);
        hashMap.put("ptCategory", ptCategory);
        hashMap.put("education", education);
        hashMap.put("result", result);
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        hashMap.put("honour", honour);
        return certService.queryCertStatus(pageNum, pageSize, hashMap);
    }
}
