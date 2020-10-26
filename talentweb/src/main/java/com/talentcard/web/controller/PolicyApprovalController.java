package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IPolicyApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 9:06 2020/10/23
 * @ Description：政策审批
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("policyApproval")
@RestController
public class PolicyApprovalController {
    @Autowired
    private IPolicyApprovalService policyApprovalService;

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData, HttpServletRequest request) {
        return policyApprovalService.pageQuery(reqData, request.getSession());
    }

    @RequestMapping("notApprovalNum")
    public ResultVO notApprovalNum(HttpServletRequest request) {
        return policyApprovalService.notApprovalNum(request.getSession());
    }
}
