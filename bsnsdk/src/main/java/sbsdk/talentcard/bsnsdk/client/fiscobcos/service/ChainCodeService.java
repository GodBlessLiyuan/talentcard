package sbsdk.talentcard.bsnsdk.client.fiscobcos.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResArrayModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fiscobcos.ReqChainCodeCancel;
import sbsdk.talentcard.bsnsdk.entity.req.fiscobcos.ReqChainCodeQuery;
import sbsdk.talentcard.bsnsdk.entity.req.fiscobcos.ReqChainCodeRegister;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResChainCodeCancel;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResChainCodeQuery;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResChainCodeRegister;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;

import java.util.List;

public class ChainCodeService {

    /**
     * 链码事件注册
     *
     * @param reqData
     */
    public static ResChainCodeRegister eventRegister(ReqChainCodeRegister reqData) {
        String api = Config.config.getApi() + "/api/fiscobcos/v1/event/register";
        BaseReqModel<ReqChainCodeRegister> req = new BaseReqModel<ReqChainCodeRegister>(reqData);
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqChainCodeRegister, ResChainCodeRegister> httpService = new HttpService<ReqChainCodeRegister, ResChainCodeRegister>();
        BaseResModel<ResChainCodeRegister> res = httpService.post(req, api, Config.config.getCert(), ResChainCodeRegister.class);
        return res.getBody();
    }

    /**
     * 链码事件查询
     *
     * @return
     */
    public static List<ResChainCodeQuery> eventQuery() throws Exception {
        String api = Config.config.getApi() + "/api/fiscobcos/v1/event/query";
        BaseReqModel<ReqChainCodeQuery> req = new BaseReqModel<ReqChainCodeQuery>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqChainCodeQuery, ResChainCodeQuery> httpService = new HttpService<ReqChainCodeQuery, ResChainCodeQuery>();
        BaseResArrayModel<ResChainCodeQuery> res = httpService.arrayPost(req, api, Config.config.getCert(), ResChainCodeQuery.class);
        return res.getBody();
    }

    /**
     * 链码事件取消接口
     *
     * @param reqData
     * @return
     * @throws Exception
     */
    public static ResChainCodeCancel eventRemove(ReqChainCodeCancel reqData) {
        String api = Config.config.getApi() + "/api/fiscobcos/v1/event/remove";
        BaseReqModel<ReqChainCodeCancel> req = new BaseReqModel<>(reqData);
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqChainCodeCancel, ResChainCodeCancel> httpService = new HttpService<>();
        BaseResModel<ResChainCodeCancel> res = httpService.post(req, api, Config.config.getCert(), ResChainCodeCancel.class);
        return res.getBody();
    }

}
