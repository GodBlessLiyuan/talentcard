package sbsdk.talentcard.bsnsdk.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.util.Log;
import sbsdk.talentcard.bsnsdk.util.algorithm.*;
import sbsdk.talentcard.bsnsdk.util.algorithm.AlgorithmTypeContext;
import sbsdk.talentcard.bsnsdk.util.common.Common;
import sbsdk.talentcard.bsnsdk.util.enums.AlgorithmTypeEnum;
import lombok.Data;

@Data
public class BaseResModel<T extends Object & IBody> implements IBaseResModel {

    @JSONField(name = "header")
    ResHeader header;

    @JSONField(name = "mac")
    String mac;

    @JSONField(name = "body")
    T body;

    public BaseResModel() {

    }

    @Override
    public boolean verify() throws Exception {
        String signValue = (this.header == null ? "" : this.header.getHeaderString()) + (this.body == null ? "" : this.body.getEncryptionValue());
        Log.d(signValue);
        AlgorithmTypeEnum algorithmTypeEnum = AlgorithmTypeEnum.fromAlgorithmTypeEnum(Config.config.getAppInfo().getAlgorithmType());
        AlgorithmTypeContext algorithmTypeContext = new AlgorithmTypeContext(algorithmTypeEnum);
        boolean verify = algorithmTypeContext.getAlgorithmTypeHandle().verify(Common.readFile(Common.getClassPathResource(Config.config.getPuk())),  this.mac,signValue);
         return verify;
    }

}
