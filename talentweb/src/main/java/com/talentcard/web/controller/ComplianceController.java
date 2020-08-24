package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        if (!StringUtils.isEmpty(reqData.get("beginDate"))){
            reqData.replace("beginDate",reqData.get("beginDate") + " 00:00:00");
        }
        if (!StringUtils.isEmpty(reqData.get("endDate"))){
            reqData.replace("endDate",reqData.get("endDate") + " 23:59:59");
        }
        return complianceService.pageQuery(reqData);
        }
    @RequestMapping("exporExcel")
    public ResultVO exporExcel(@RequestBody Map<String, Object> reqData, HttpServletResponse response) {
        if (!StringUtils.isEmpty(reqData.get("beginDate"))){
            reqData.replace("beginDate",reqData.get("beginDate") + " 00:00:00");
        }
        if (!StringUtils.isEmpty(reqData.get("endDate"))){
            reqData.replace("endDate",reqData.get("endDate") + " 23:59:59");
        }
        return complianceService.exportExcel(reqData,response);
    }
    @RequestMapping("pushRecord")
    public ResultVO pushRecord(@RequestBody Map<String, Object> reqData) {
        Long pid=Long.parseLong(reqData.get("pid").toString());
        return complianceService.pushRecordQuery(pid);
    }
    @RequestMapping("push")
    public ResultVO push(HttpSession session,@RequestBody Map<String, Object> reqData) {return complianceService.push(session,reqData);}
}
