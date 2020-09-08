package sbsdk.controller;

import com.bsnsapi.fabric.chaincodeEntities.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sbsdk.config.MyConfig;
import sbsdk.convert.Apply2Ticket;
import sbsdk.convert.StringSToStringS;
import sbsdk.convert.TransactionStatus;
import sbsdk.talentcard.bsnsdk.client.fabric.service.TransactionService;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrow;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 4-2 人才申请政策智能合约
 * @author: liyuan
 * @data 2020-09-08 16:50
 */
@RestController
public class TransactionApplyController {
    @Autowired
    private MyConfig myConfig;
    @PostMapping("transactionApplyApi/apply")
    public String apply(@RequestBody Application application, HttpServletRequest request){
        try{
            TransactionService.reqChainCode(this.applyFunName(application,request));
            return TransactionStatus.OK.getStatus();
        }catch (Exception e){
            e.printStackTrace();
            return TransactionStatus.Error.getStatus();
        }
    }

    private ReqKeyEscrow applyFunName(Application application, HttpServletRequest request) {
        myConfig.initConfig();
        ReqKeyEscrow reqKeyEscrow = Apply2Ticket.convert(application);
        String[] url = request.getRequestURI().split("/");
        // /transactionProfile/getProfile
        reqKeyEscrow.setFuncName(url[2]);
        return reqKeyEscrow;
    }

    @PostMapping("transactionApplyApi/getApplicationInfo")
    public String getApplicationInfo(@RequestBody Application application,HttpServletRequest request){
        try{
            ResKeyEscrow resKeyEscrow = TransactionService.reqChainCode(this.applyFunName(application, request));
            return resKeyEscrow.getCcRes().getCcData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     resKeyEscrow.getCcRes().getCcData() 得到的是
     [{"txId":"63c98a94b5d6b5737ad1e39d6766bb4a274574cf1dfa54f7e06a618a00959732",
     "dataInfo":"{\"applicationUid\":\"0100A\",\"pid\":\"0100B\",\"applyFor\":\"applyFor\",\"status\":\"ok\"}",
     "txTime":"2020-09-08 10:47:55","isDelete":false}]
     * */
    @PostMapping("transactionApplyApi/getHistoryForApplication")
    public String getHistoryForApplication(@RequestBody Application application,HttpServletRequest request){
        try{
            ResKeyEscrow resKeyEscrow = TransactionService.reqChainCode(this.applyFunName(application, request));
            return StringSToStringS.convert(resKeyEscrow.getCcRes().getCcData(),"dataInfo", Application.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
