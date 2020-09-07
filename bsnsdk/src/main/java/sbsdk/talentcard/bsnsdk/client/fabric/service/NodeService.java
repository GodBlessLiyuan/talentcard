package sbsdk.talentcard.bsnsdk.client.fabric.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqGetBlockInformation;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqGetLedgerInfo;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqGetTransaction;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResGetBlockInformation;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResGetLedgerInfo;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResGetTransaction;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;

public class NodeService {
   
	/**
     * 获取交易信息
     * @param reqData
     */
	public static ResGetTransaction getTransaction(ReqGetTransaction reqData){
        String api =  Config.config.getApi() + "/api/fabric/v1/node/getTransaction";
        BaseReqModel<ReqGetTransaction> req = new  BaseReqModel<ReqGetTransaction>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(reqData);
        HttpService<ReqGetTransaction,ResGetTransaction> httpService =new HttpService<ReqGetTransaction,ResGetTransaction>();
        BaseResModel<ResGetTransaction> res = httpService.post(req,api, Config.config.getCert(),ResGetTransaction.class);

        return res.getBody();
	}

    /**
     * 获取块信息
     * @param reqData
     */
	public static ResGetBlockInformation getBlockInfo(ReqGetBlockInformation reqData){
        String api =  Config.config.getApi() + "/api/fabric/v1/node/getBlockInfo";
        BaseReqModel<ReqGetBlockInformation> req = new  BaseReqModel<ReqGetBlockInformation>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(reqData);
        HttpService<ReqGetBlockInformation,ResGetBlockInformation> httpService =new HttpService<ReqGetBlockInformation,ResGetBlockInformation>();
        BaseResModel<ResGetBlockInformation> res = httpService.post(req,api, Config.config.getCert(),ResGetBlockInformation.class);
         return res.getBody();
	}

    /**
     * 获取最新账本信息
     */
	
	public static ResGetLedgerInfo getLedgerInfo(){
        String api =  Config.config.getApi() + "/api/fabric/v1/node/getLedgerInfo";
        BaseReqModel<ReqGetLedgerInfo> req = new  BaseReqModel<ReqGetLedgerInfo>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqGetLedgerInfo,ResGetLedgerInfo> httpService =new HttpService<ReqGetLedgerInfo,ResGetLedgerInfo>();
        BaseResModel<ResGetLedgerInfo> res = httpService.post(req,api, Config.config.getCert(),ResGetLedgerInfo.class);

        return res.getBody();
	}

}
