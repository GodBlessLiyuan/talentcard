package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:28 2020/8/20
 * @ Description：符合人数详情前端交互接口
 * @ Modified By：
 * @ Version:     1.0
 */

@RequestMapping("compliance")
@RestController
public class ComplianceController {
    @Autowired
    private IComplianceService complianceService;

    @RequestMapping("queryNum")
    public ResultVO queryNum(@RequestBody Map<String, Object> reqData) {
        return complianceService.queryNum(reqData);
    }

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        if (!StringUtils.isEmpty(reqData.get("beginDate"))) {
            reqData.replace("beginDate", reqData.get("beginDate") + " 00:00:00");
        }
        if (!StringUtils.isEmpty(reqData.get("endDate"))) {
            reqData.replace("endDate", reqData.get("endDate") + " 23:59:59");
        }
        return complianceService.pageQuery(reqData);
    }

    @GetMapping("exporExcel")
    public void exporExcel(@RequestParam(value = "pid", required = false) Long pid,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "wunit", required = false) String wunit,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "beginDate", required = false) String beginDate,
                               @RequestParam(value = "endDate", required = false) String endDate,
                               @RequestParam(value = "status", required = false) String status
            , HttpServletResponse response) {
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pid", pid);
        reqData.put("name", name);
        reqData.put("wunit", wunit);
        reqData.put("phone", phone);
        reqData.put("beginDate", beginDate);
        reqData.put("endDate", endDate);
        reqData.put("status", status);
        if (!StringUtils.isEmpty(reqData.get("beginDate"))) {
            reqData.replace("beginDate", reqData.get("beginDate") + " 00:00:00");
        }
        if (!StringUtils.isEmpty(reqData.get("endDate"))) {
            reqData.replace("endDate", reqData.get("endDate") + " 23:59:59");
        }
        complianceService.exportExcel(reqData, response);
    }

    @RequestMapping("pushRecordQuery")
    public ResultVO pushRecord(@RequestBody Map<String, Object> reqData) {
        Long pid = Long.parseLong(reqData.get("pid").toString());
        return complianceService.pushRecordQuery(pid);
    }

    @RequestMapping("push")
    public ResultVO push(HttpSession session, @RequestBody Map<String, Object> reqData) {
        return complianceService.push(session, reqData);
    }
    @RequestMapping("queryCertId")
    public ResultVO push(@RequestBody Map<String, Object> reqData) {
        return complianceService.queryCertId(reqData);
    }
}
