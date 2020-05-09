package com.talentcard.web.controller;

import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IPolicyApplyService;
import com.talentcard.web.vo.PolicyApplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 20:31
 * @description: 政策审批
 * @version: 1.0
 */
@RequestMapping("policy_apply")
@RestController
public class PolicyApplyController {
    @Autowired
    private IPolicyApplyService service;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "start", defaultValue = "") String start,
                                           @RequestParam(value = "end", defaultValue = "") String end,
                                           @RequestParam(value = "num", defaultValue = "") String num,
                                           @RequestParam(value = "name", defaultValue = "") String name,
                                           @RequestParam(value = "apply", defaultValue = "") String apply,
                                           @RequestParam(value = "status", defaultValue = "0") Byte status) {

        HashMap<String, Object> reqMap = new HashMap<>(6);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }

        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("num", num);
        reqMap.put("name", name);
        reqMap.put("apply", apply);
        reqMap.put("status", status);

        return service.query(pageNum, pageSize, reqMap);
    }

    @RequestMapping("export")
    public ResultVO export(@RequestParam(value = "start", defaultValue = "") String start,
                           @RequestParam(value = "end", defaultValue = "") String end,
                           @RequestParam(value = "num", defaultValue = "") String num,
                           @RequestParam(value = "name", defaultValue = "") String name,
                           @RequestParam(value = "apply", defaultValue = "") String apply,
                           @RequestParam(value = "status", defaultValue = "0") Byte status,
                           HttpServletResponse res) {
        HashMap<String, Object> reqMap = new HashMap<>(6);
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("name", name);
        reqMap.put("num", num);
        reqMap.put("apply", apply);
        reqMap.put("status", status);

        return service.export(reqMap, res);
    }

    @RequestMapping("approval")
    public ResultVO approval(HttpSession session,
                             @RequestParam(value = "paid") Long paid,
                             @RequestParam(value = "status", defaultValue = "") Byte status,
                             @RequestParam(value = "opinion", defaultValue = "") String opinion) {
        return service.approval(session, paid, status, opinion);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "paid") Long paid) {
        return service.detail(paid);
    }

    @RequestMapping("count")
    public ResultVO count() {
        return service.count();
    }
}
