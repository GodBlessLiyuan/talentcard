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
                                    @RequestParam(value = "status", required = false) Byte status,
                                    @RequestParam(value = "startTime", required = false) String startTime,
                                    @RequestParam(value = "endTime", required = false) String endTime){

        Map<String, Object> reqData = new HashMap<>(8);
        reqData.put("name",name);
        reqData.put("sex",sex);
        reqData.put("pqCategory",pqCategory);
        reqData.put("ptCategory",ptCategory);
        reqData.put("education",education);
        reqData.put("status",status);
        reqData.put("startTime",startTime);
        reqData.put("endTime",endTime);
        return certService.queryCertStatus(pageNum, pageSize,reqData);
    }
}
