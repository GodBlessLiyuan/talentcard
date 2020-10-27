package sbsdk.talentcard.bsnsdk.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.util.algorithm.*;
import sbsdk.talentcard.bsnsdk.util.algorithm.AlgorithmTypeContext;
import sbsdk.talentcard.bsnsdk.util.common.Common;
import sbsdk.talentcard.bsnsdk.util.enums.AlgorithmTypeEnum;
import sbsdk.talentcard.bsnsdk.util.enums.ResultInfoEnum;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import lombok.Data;

@Data
public class BaseReqModel<T extends Object & IBody> implements IBaseReqModel {
    @JSONField(name = "header")
    ReqHeader header;

    @JSONField(name = "mac")
    String mac;

    @JSONField(name = "body")
    T body;

    public BaseReqModel() {
    }

    public BaseReqModel(T body) {
        this.body = body;
    }

    @Override
    public void sign(Config config) throws Exception {
        if (this.header == null) {
            throw new GlobalException(ResultInfoEnum.REQUEST_HEADER_ERROR);
        }

        String signValue = this.header.getHeaderString();

        if (this.body != null) {
            signValue += this.body.getEncryptionValue();
        }
        AlgorithmTypeEnum algorithmTypeEnum = AlgorithmTypeEnum.fromAlgorithmTypeEnum(config.getAppInfo().getAlgorithmType());
        AlgorithmTypeContext algorithmTypeContext = new AlgorithmTypeContext(algorithmTypeEnum);
        String sign = algorithmTypeContext.getAlgorithmTypeHandle().sign(Common.readFile(Common.getClassPathResource(config.getPrk())), signValue);
        this.mac = sign;
    }


    @Override
    public void setReqHeader(String userCode,String appCode) {
        this.header = new ReqHeader();
        this.header.setAppCode(appCode);
        this.header.setUserCode(userCode);
    }
}
