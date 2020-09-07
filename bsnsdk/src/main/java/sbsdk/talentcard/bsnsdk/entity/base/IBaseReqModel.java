package sbsdk.talentcard.bsnsdk.entity.base;

public interface IBaseReqModel {
    void sign() throws Exception;

    void setReqHeader(String userCode, String appCode);
}
