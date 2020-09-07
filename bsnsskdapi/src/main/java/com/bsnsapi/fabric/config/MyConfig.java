package com.bsnsapi.fabric.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import sbsdk.talentcard.bsnsdk.entity.config.Config;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-03 19:14
 */
@Component
@ConfigurationProperties(prefix = "bsnsdktalent")
@Data
public class MyConfig {
    /**
     * 节点网关地址
     */
    private String api;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 用户编号
     */
    private String appCode;
    /**
     * 应用公钥
     */
    private String puk;
    /**
     * 应用私钥
     */
    private String prk;
    /**
     * 证书存数目录
     */
    private String mspDir;
    /**
     * 证书
     */
    private String cert;
    public void initConfig(){
        Config config=new Config();
        config.setAppCode(appCode);
        config.setUserCode(userCode);
        config.setApi(api);
        config.setCert(cert);
        config.setPrk(prk);
        config.setPuk(puk);
        config.setMspDir(mspDir);
        config.initConfig(config);
    }
}
