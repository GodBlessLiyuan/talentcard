package sbsdk.talentcard.bsnsdk.client.fabric.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResArrayModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqChainCodeQuery;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqChainCodeRegister;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqChainCodeRemove;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResChainCodeQuery;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResChainCodeRegister;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResChainCodeRemove;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;

import java.util.List;

public class ChainCodeService {

	
	
    /**
     * 链码事件注册
     * @param reqData
     */
	public static ResChainCodeRegister eventRegister(ReqChainCodeRegister reqData){
        String api =  Config.config.getApi() + "/api/fabric/v1/chainCode/event/register";
        BaseReqModel<ReqChainCodeRegister> req = new  BaseReqModel<ReqChainCodeRegister>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(reqData);
        HttpService<ReqChainCodeRegister,ResChainCodeRegister> httpService =new HttpService<ReqChainCodeRegister,ResChainCodeRegister>();
        BaseResModel<ResChainCodeRegister> res = httpService.post(req,api, Config.config.getCert(),ResChainCodeRegister.class);
        return res.getBody();
    }

    /**
     * 链码事件查询
     * @return
     */
	public static List<ResChainCodeQuery> eventQuery(){
        String api =  Config.config.getApi() + "/api/fabric/v1/chainCode/event/query";
        BaseReqModel<ReqChainCodeQuery> req = new  BaseReqModel<ReqChainCodeQuery>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqChainCodeQuery,ResChainCodeQuery> httpService =new HttpService<ReqChainCodeQuery,ResChainCodeQuery>();


        BaseResArrayModel<ResChainCodeQuery> res = httpService.arrayPost(req,api, Config.config.getCert(),ResChainCodeQuery.class);


        return res.getBody();
	}

    /**
     * 链码事件注销
     * @param reqData
     * @return
     * @throws Exception
     */
	public static ResChainCodeRemove eventRemove(ReqChainCodeRemove reqData){
        String api =  Config.config.getApi() + "/api/fabric/v1/chainCode/event/remove";
        BaseReqModel<ReqChainCodeRemove> req = new  BaseReqModel<ReqChainCodeRemove>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(reqData);
        HttpService<ReqChainCodeRemove,ResChainCodeRemove> httpService =new HttpService<ReqChainCodeRemove,ResChainCodeRemove>();
        BaseResModel<ResChainCodeRemove> res = httpService.post(req,api, Config.config.getCert(),ResChainCodeRemove.class);
        return res.getBody();
	}
    
}
