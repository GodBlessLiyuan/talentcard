package sbsdk.talentcard.bsnsdk.client.fabric.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqUserInfo;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResUserInfo;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;

import java.io.IOException;

public class AppService {

    /**
     * 获取应用信息
     *
     * @return
     * @throws IOException
     */
    public static ResUserInfo getAppInfo(Config config) throws IOException {
        String api = config.getApi() + "/api/app/getAppInfo";
        BaseReqModel<ReqUserInfo> req = new BaseReqModel<ReqUserInfo>();
        req.setReqHeader(config.getUserCode(), config.getAppCode());

        HttpService<ReqUserInfo, ResUserInfo> httpService = new HttpService<ReqUserInfo, ResUserInfo>();
        BaseResModel<ResUserInfo> res = httpService.noSignPost(req, api, config.getCert(), ResUserInfo.class);

        return res.getBody();
    }

}
