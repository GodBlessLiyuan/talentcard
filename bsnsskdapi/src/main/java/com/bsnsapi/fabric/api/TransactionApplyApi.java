package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.chaincodeEntities.Application;
import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface TransactionApplyApi {
    @PostMapping("transactionApplyApi/apply")
    ResultVO apply(@RequestBody Application application);

    @PostMapping("transactionApplyApi/getApplicationInfo")
    ResultVO getApplicationInfo(@RequestBody Application applicatio);

    @PostMapping("transactionApplyApi/getHistoryForApplication")
    ResultVO getHistoryForApplication(@RequestBody Application application);
}
