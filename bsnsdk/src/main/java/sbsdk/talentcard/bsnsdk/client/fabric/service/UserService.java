package sbsdk.talentcard.bsnsdk.client.fabric.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrowEnroll;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqUserRegister;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrowEnroll;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResUserRegister;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;
import sbsdk.talentcard.bsnsdk.util.common.StoreUtils;
import sbsdk.talentcard.bsnsdk.util.common.UserCertInfo;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class UserService {
    /**
     * 用户注册
     *  初始化时候：Config.config 才有值
     * @param register
     * @return
     * @throws IOException
     */
    public static ResUserRegister userRegister(ReqUserRegister register) throws IOException {
        String api = Config.config.getApi() + "/api/fabric/v1/user/register";
        BaseReqModel<ReqUserRegister> req = new BaseReqModel<ReqUserRegister>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(register);
        HttpService<ReqUserRegister, ResUserRegister> httpService = new HttpService<ReqUserRegister, ResUserRegister>();
        BaseResModel<ResUserRegister> res = httpService.post(req, api, Config.config.getCert(), ResUserRegister.class);

        ResUserRegister body = res.getBody();
        return body;
    }

    /**
     * 用户非托管模式用户证书登记
     *
     * @param kes
     * @return
     * @throws IOException
     */
    public static ResKeyEscrowEnroll userEnroll(@NotNull ReqKeyEscrowEnroll kes) throws IOException {
        String api = Config.config.getApi() + "/api/fabric/v1/user/enroll";
        UserCertInfo certInfo = StoreUtils.generateCSR(kes.getName(), Config.config.getAppCode(), Config.config.getMspDir());
        System.out.println(certInfo.getCSRPem());
        kes.setCsrPem(certInfo.getCSRPem());

        BaseReqModel<ReqKeyEscrowEnroll> req = new BaseReqModel<ReqKeyEscrowEnroll>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(kes);
        HttpService<ReqKeyEscrowEnroll, ResKeyEscrowEnroll> httpService = new HttpService<ReqKeyEscrowEnroll, ResKeyEscrowEnroll>();
        BaseResModel<ResKeyEscrowEnroll> res = httpService.post(req, api, Config.config.getCert(), ResKeyEscrowEnroll.class);

        ResKeyEscrowEnroll body = res.getBody();

        //存储私钥
        Config.config.getKeyStore().storeUserPrivateKey(kes.getName(), Config.config.getAppCode(), certInfo.getKey());
        //存储登记的证书
        Config.config.getKeyStore().storeUserCert(kes.getName(), Config.config.getAppCode(), body.getCert());

        return body;
    }

}
