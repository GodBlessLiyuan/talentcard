package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertApprocalService;
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
    ICertApprocalService certApprocalService;

    @RequestMapping("CertApprovalShow")
    public ResultVO certApprovalShow(@RequestParam(value = "talentId", required = false) Long talentId){
        return certApprocalService.certApprovalShowItems(talentId);
    }
    @RequestMapping("CertConfirm")
    public ResultVO CertConfirm(HttpSession session,
                                @RequestParam(value = "talentId", required = false) Long talentId,
                                @RequestParam(value = "certId", required = false) Long certId,
                                @RequestParam(value = "result", required = false) Byte result,
                                @RequestParam(value = "cardId", defaultValue = "1") Long cardId,
                                @RequestParam(value = "cardName", required = false) String cardName,
                                @RequestParam(value = "category", defaultValue = "1") String category,
                                @RequestParam(value = "opinion", required = false) String opinion){

        Map<String, Object> reqData = new HashMap<>(7);
        reqData.put("talentId",talentId);
        reqData.put("certId",certId);
        reqData.put("result",result);
        reqData.put("cardId",cardId);
        reqData.put("cardName",cardName);
        reqData.put("category",category);
        reqData.put("opinion",opinion);

        return certApprocalService.confirmCert(session,reqData);
    }

    /**
     * 若拥有认证审批权限，则查询当前所有待审批数目
     * @param session
     * @return
     */
    @RequestMapping("queryByNumApproval")
    public ResultVO queryByNumApproval(HttpSession session) {
        return certApprocalService.queryByNumApproval();
    }

}
