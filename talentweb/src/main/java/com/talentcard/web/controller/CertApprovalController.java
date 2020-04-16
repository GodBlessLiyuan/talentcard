package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertApprocalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:21 2020/4/15
 * @version: 1.0.0
 * @description: 认证审批
 */
@RestController
@RequestMapping("certApproval")
public class CertApprovalController {
    @Resource
    ICertApprocalService certApprocalService;

    @RequestMapping("CertApprovalShow")
    public ResultVO certApprovalShow(@RequestParam(value = "talentId", required = false) Long talentId){
        return certApprocalService.certApprovalShowItems(talentId);
    }
    @RequestMapping("CertConfirm")
    public ResultVO CertConfirm(HttpSession session,
                                @RequestParam(value = "talentId", required = false) Long talentId,
                                @RequestParam(value = "createTime", required = false) Date createTime,
                                @RequestParam(value = "certId", required = false) Long certId,
                                @RequestParam(value = "result", required = false) int result,
                                @RequestParam(value = "cardId", defaultValue = "普通卡") String cardId,
                                @RequestParam(value = "category", defaultValue = "基础人才") String category,
                                @RequestParam(value = "opinion", required = false) String opinion){

        Map<String, Object> reqData = new HashMap<>(7);
        reqData.put("talentId",talentId);
        reqData.put("createTime",createTime);
        reqData.put("certId",certId);
        reqData.put("result",result);
        reqData.put("cardId",cardId);
        reqData.put("category",category);
        reqData.put("opinion",opinion);

        return certApprocalService.confirmCert(session,reqData);
    }

}
