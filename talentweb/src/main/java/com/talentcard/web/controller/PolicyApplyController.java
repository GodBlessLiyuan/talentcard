package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.dto.ApplyNumCountDTO;
import com.talentcard.web.service.IPolicyApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
    private IPolicyApplyService iPolicyApplyService;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "start", defaultValue = "") String start,
                          @RequestParam(value = "end", defaultValue = "") String end,
                          @RequestParam(value = "num", defaultValue = "") String num,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "apply", defaultValue = "") String apply,
                          @RequestParam(value = "status", defaultValue = "0") Byte status,
                          @RequestParam(value = "roleType", required = false, defaultValue = "0") Byte roleType,
                          @RequestParam(value = "responsibleUnitId", required = false) Long responsibleUnitId,
                          @RequestParam(value = "roleId", required = false) Long roleId) {

        HashMap<String, Object> reqMap = new HashMap<>(11);

        if (!StringUtils.isEmpty(start)) {
            start = start + " 00:00:00";
        }

        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("num", num);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        reqMap.put("apply", apply);
        reqMap.put("status", status);
        reqMap.put("roleType", roleType);
        reqMap.put("roleId", roleId);
        reqMap.put("responsibleUnitId", responsibleUnitId);
        return iPolicyApplyService.query(pageNum, pageSize, reqMap);
    }

    @RequestMapping("export")
    public void export(@RequestParam(value = "start", defaultValue = "") String start,
                           @RequestParam(value = "end", defaultValue = "") String end,
                           @RequestParam(value = "num", defaultValue = "") String num,
                           @RequestParam(value = "name", defaultValue = "") String name,
                           @RequestParam(value = "apply", defaultValue = "") String apply,
                           @RequestParam(value = "status", defaultValue = "0") Byte status,
                           @RequestParam(value = "roleType", required = false, defaultValue = "0") Byte roleType,
                           @RequestParam(value = "responsibleUnitId", required = false) Long responsibleUnitId,
                           @RequestParam(value = "roleId", required = false) Long roleId,
                           HttpServletResponse res) {
        HashMap<String, Object> reqMap = new HashMap<>(9);
        if (!StringUtils.isEmpty(start)) {
            start = start + " 00:00:00";
        }
        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("num", num);
        if (StringUtils.isNotBlank(name)) {
            reqMap.put("name", name.replaceAll("%", "\\\\%"));
        }
        reqMap.put("apply", apply);
        reqMap.put("status", status);
        reqMap.put("roleType", roleType);
        reqMap.put("roleId", roleId);
        reqMap.put("responsibleUnitId", responsibleUnitId);
        iPolicyApplyService.export(reqMap, res);
    }

    @RequestMapping("approval")
    public ResultVO approval(HttpServletRequest request,
                             @RequestParam(value = "paid") Long paid,
                             @RequestParam(value = "status", defaultValue = "") Byte status,
                             @RequestParam(value = "opinion", defaultValue = "") String opinion,
                             @RequestParam(value = "actualFunds") BigDecimal actualFunds) {
        return iPolicyApplyService.approval(request.getSession(), paid, status, opinion, actualFunds);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "paid") Long paid) {
        return iPolicyApplyService.detail(paid);
    }

    @RequestMapping("count")
    public ResultVO count(HttpSession httpSession) {
        return iPolicyApplyService.count(httpSession);
    }

    /**
     * 政策撤回
     *
     * @param httpSession
     * @param paId
     * @param opinion
     * @return
     */
    @RequestMapping("cancel")
    public ResultVO cancel(HttpSession httpSession,
                           @RequestParam(value = "paId") Long paId,
                           @RequestParam(value = "opinion", required = false, defaultValue = "") String opinion) {
        return iPolicyApplyService.cancel(httpSession, paId, opinion);
    }

    @RequestMapping("applyNumCount")
    public ResultVO applyNumCount(@RequestBody ApplyNumCountDTO applyNumCountDTO) {
        if (!StringUtils.isEmpty(applyNumCountDTO.getStart())) {
            String start = applyNumCountDTO.getStart() + " 00:00:00";
            applyNumCountDTO.setStart(start);
        }
        if (!StringUtils.isEmpty(applyNumCountDTO.getEnd())) {
            String end = applyNumCountDTO.getEnd() + " 23:59:59";
            applyNumCountDTO.setEnd(end);
        }
        return iPolicyApplyService.applyNumCount(applyNumCountDTO);
    }
}
