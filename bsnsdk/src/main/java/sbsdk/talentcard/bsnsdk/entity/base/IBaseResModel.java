package sbsdk.talentcard.bsnsdk.entity.base;

import sbsdk.talentcard.bsnsdk.entity.config.Config;

public interface IBaseResModel {
    boolean verify(Config config) throws Exception;
}
