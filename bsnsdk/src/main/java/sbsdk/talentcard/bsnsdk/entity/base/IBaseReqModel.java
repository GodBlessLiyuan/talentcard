package sbsdk.talentcard.bsnsdk.entity.base;

import sbsdk.talentcard.bsnsdk.entity.config.Config;

public interface IBaseReqModel {
    void sign(Config config) throws Exception;

    void setReqHeader(String userCode, String appCode);
}
