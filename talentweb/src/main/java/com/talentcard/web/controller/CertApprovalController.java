package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertApprovalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    ICertApprovalService certApprovalService;

    @RequestMapping("CertApprovalShow")
    public ResultVO certApprovalShow(@RequestParam(value = "talentId", required = false) Long talentId){
        return certApprovalService.certApprovalShowItems(talentId);
    }

    @RequestMapping("DetailsLookItems")
    public ResultVO detailsLookItems(@RequestParam(value = "talentId", required = false) Long talentId){
        return certApprovalService.detailsLookItems(talentId);
    }


    @RequestMapping("CertConfirm")
    public ResultVO CertConfirm(HttpSession session,
                                @RequestParam(value = "talentId", required = false) Long talentId,
                                @RequestParam(value = "certId", required = false) Long certId,
                                @RequestParam(value = "result", required = false) Byte result,
                                @RequestParam(value = "cardId",required = false) Long cardId,
                                @RequestParam(value = "category",defaultValue = "",required = false) String category,
                                @RequestParam(value = "opinion", required = false) String opinion){

        Map<String, Object> reqData = new HashMap<>(6);
        reqData.put("talentId",talentId);
        reqData.put("certId",certId);
        reqData.put("result",result);
        reqData.put("cardId",cardId);
        reqData.put("category",category);
        reqData.put("opinion",opinion);

        return certApprovalService.confirmCert(session,reqData);
    }

    /**
     * 若拥有认证审批权限，则查询当前所有待审批数目
     * @param session
     * @return
     */
    @RequestMapping("queryByNumApproval")
    public ResultVO queryByNumApproval(HttpSession session) {
        return certApprovalService.queryByNumApproval();
    }

}
