package sbsdk.talentcard.bsnsdk.entity.config;

import sbsdk.talentcard.bsnsdk.client.fabric.service.AppService;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResUserInfo;
import sbsdk.talentcard.bsnsdk.util.enums.ResultInfoEnum;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import sbsdk.talentcard.bsnsdk.util.keystore.IKeyStore;
import sbsdk.talentcard.bsnsdk.util.keystore.KeyStore;
import lombok.Data;

import java.io.IOException;

@Data
public class Config {
    /**
     * 节点网关地址
     */
    String api;
    /**
     * 用户编号
     */
    String userCode;
    /**
     * 用户编号
     */
    String appCode;
    /**
     * 应用公钥
     */
    String puk;
    /**
     * 应用私钥
     */
    String prk;
    /**
     * 证书存数目录
     */
    String mspDir;
    /**
     * 证书
     */
    String cert;

    //APP信息
    ResUserInfo appInfo;

    //子用户证书存储处理
    IKeyStore keyStore;



    public Config config;

    public void initConfig(Config cg) {
        if (config == null) {
            config = cg;
            keyStore = new KeyStore(config.getMspDir());
            ResUserInfo res = null;
            try {
                res = AppService.getAppInfo();//httpService.noSignPost
            } catch (IOException e) {
                e.printStackTrace();
                throw new GlobalException(ResultInfoEnum.GET_APP_INFO_ERROR);
            }
            appInfo = res;
        }
    }
}
