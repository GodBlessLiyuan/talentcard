package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.chaincodeEntities.Application;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface TransactionApplyApi {
    @PostMapping("transactionApplyApi/apply")
    String apply(@RequestBody Application application, HttpServletRequest request);

    @PostMapping("transactionApplyApi/getApplicationInfo")
    String getApplicationInfo(@RequestBody Application application, HttpServletRequest request);

    @PostMapping("transactionApplyApi/getHistoryForApplication")
    String getHistoryForApplication(@RequestBody Application application, HttpServletRequest request);
}
