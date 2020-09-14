package com.talentcard.web.talentcardFeign.controller;

import com.bsnsapi.fabric.chaincodeEntities.Application;
import com.bsnsapi.fabric.vo.ResultVO;
import com.talentcard.web.client.TransactionApplyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-14 16:49
 */
@RestController
public class FeignTransactionApplyController {
    @Autowired
    private TransactionApplyClient transactionApplyClient;
    @PostMapping("transactionApplyApi/apply")
    ResultVO apply(@RequestBody Application application, HttpServletRequest request){
        return transactionApplyClient.apply(application);
    }

    @PostMapping("transactionApplyApi/getApplicationInfo")
    ResultVO getApplicationInfo(@RequestBody Application application, HttpServletRequest request){
        return transactionApplyClient.getApplicationInfo(application);
    }

    @PostMapping("transactionApplyApi/getHistoryForApplication")
    ResultVO getHistoryForApplication(@RequestBody Application application, HttpServletRequest request){
        return transactionApplyClient.getHistoryForApplication(application);
    }
}
