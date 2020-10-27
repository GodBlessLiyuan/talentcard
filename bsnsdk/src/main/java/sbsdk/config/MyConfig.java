package sbsdk.config;

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
     * 节点网关地址1
     */
    private String api;
    /**
     * 节点网关地址2
     */
    private String api2;
    /**
     * 节点网关地址3
     */
    private String api3;
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
    private String puk2;
    private String puk3;
    /**
     * 应用私钥
     */
    private String prk;
    private String prk2;
    private String prk3;
    /**
     * 证书存数目录
     */
    private String mspDir;
    /**
     * 证书
     */
    private String cert;
    private String cert2;
    private String cert3;

    Config config;

    Config configs[] = new Config[3];

    public Config initConfig(int index) {
        if (configs[index] == null) {
            config = new Config();
            config.setAppCode(appCode);
            config.setUserCode(userCode);
            if (index == 0) {
                config.setApi(api);
                config.setCert(cert);
                config.setPrk(prk);
                config.setPuk(puk);

            } else if (index == 1) {
                config.setApi(api2);
                config.setCert(cert2);
                config.setPrk(prk2);
                config.setPuk(puk2);
            } else if (index == 2) {
                config.setApi(api3);
                config.setCert(cert3);
                config.setPrk(prk3);
                config.setPuk(puk3);
            }

            config.setMspDir(mspDir);
            config.initConfig(config);
            configs[index] = config;
        }

        return configs[index];
    }

}
