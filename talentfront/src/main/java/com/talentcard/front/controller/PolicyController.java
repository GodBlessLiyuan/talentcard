package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.dto.PolicyApplyDTO;
import com.talentcard.front.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 15:22
 * @description: 政策权益
 * @version: 1.0
 */
@RequestMapping("policy")
@RestController
public class PolicyController {
    @Autowired
    private IPolicyService service;

    /**
     * 我的权益
     *
     * @param openid
     * @return
     */
    @RequestMapping("policies")
    public ResultVO policies(@RequestParam(name = "openid") String openid) {
        return service.policies(openid);
    }

    /**
     * 我的权益 - 申请
     * @param openid 人才Id
     * @param pid 政策Id
     * @param card 银行卡号
     * @param bank 开户行号
     * @param files 附件
     * @return
     */
    @RequestMapping("apply")
    public ResultVO apply(@RequestParam(name = "openid") String openid,
                          @RequestParam(name = "pid") Long pid,
                          @RequestParam(name = "card", required = false) String card,
                          @RequestParam(name = "bank", required = false) String bank,
                          @RequestParam(value = "files", required = false) MultipartFile[] files) {
        PolicyApplyDTO dto = new PolicyApplyDTO(openid, pid, card, bank, files);
        return service.apply(dto);
    }


    /**
     * 我的申请
     *
     * @param openid 人才ID
     * @return
     */
    @RequestMapping("applies")
    public ResultVO applies(@RequestParam(name = "openid") String openid) {
        return service.applies(openid);
    }

    /**
     * 我的申请 - 详情
     *
     * @param paid 申请ID
     * @return
     */
    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(name = "paid") Long paid) {
        return service.detail(paid);
    }

    /**
     * 獲得銀行卡信息
     * @param openId
     * @return
     */
    @RequestMapping("bankInfo")
    public ResultVO queryBankCardInfo(@RequestParam(name = "openId") String openId){
        return service.queryBankCardInfo(openId);
    }

    /**
     * 根据policyId查找单个政策信息
     * @param policyId
     * @return
     */
    @RequestMapping("policyFindOne")
    public ResultVO policyFindOne(@RequestParam(name = "policyId") Long policyId){
        return service.policyFindOne(policyId);
    }
}
