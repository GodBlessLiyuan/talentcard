package sbsdk.talentcard.bsnsdk.client.xuperChain.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.xuperChain.ReqUserRegister;
import sbsdk.talentcard.bsnsdk.entity.res.xuperChain.ResUserRegister;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;

import java.io.IOException;

public class UserService {
    /**
     * 用户注册
     *
     * @param register
     * @return
     * @throws IOException
     */
    public static ResUserRegister userRegister(ReqUserRegister register) throws IOException {
        String api = Config.config.getApi() + "/api/xuperchain/v1/user/register";
        BaseReqModel<ReqUserRegister> req = new BaseReqModel<>(register);
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqUserRegister, ResUserRegister> httpService = new HttpService<ReqUserRegister, ResUserRegister>();
        BaseResModel<ResUserRegister> res = httpService.post(req, api, Config.config.getCert(), ResUserRegister.class);
        ResUserRegister body = res.getBody();
        return body;
    }
}
