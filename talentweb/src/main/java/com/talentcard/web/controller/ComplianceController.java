package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
        return complianceService.pageQuery(reqData);
        }
    @RequestMapping("exporExcel")
    public ResultVO exporExcel(@RequestBody Map<String, Object> reqData, HttpServletResponse response) {
        return complianceService.exportExcel(reqData,response);
    }
    @RequestMapping("pushRecord")
    public ResultVO pushRecord(@RequestBody Map<String, Object> reqData) {
        Long pid=Long.parseLong(reqData.get("pid").toString());
        return complianceService.pushRecordQuery(pid);
    }
    @RequestMapping("push")
    public ResultVO push(@RequestBody Map<String, Object> reqData) {return complianceService.push(reqData);
        }

}
