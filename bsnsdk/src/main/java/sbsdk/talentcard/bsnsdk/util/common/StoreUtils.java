package sbsdk.talentcard.bsnsdk.util.common;

import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.util.algorithm.AlgorithmTypeContext;
import sbsdk.talentcard.bsnsdk.util.enums.AlgorithmTypeEnum;
import sbsdk.talentcard.bsnsdk.util.enums.ResultInfoEnum;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Objects;

public class StoreUtils {
    private final static Logger logger = LoggerFactory.getLogger(StoreUtils.class);

    /**
     * csr生成
     * @param name
     * @param appCode
     * @param fileName
     * @return
     */
    public static UserCertInfo generateCSR(String name,String appCode, String fileName, Config config) {
        try {
            String DN = "CN=" + Common.getCNName(name, appCode)+",OU=client";
            AlgorithmTypeEnum algorithmTypeEnum = AlgorithmTypeEnum.fromAlgorithmTypeEnum(config.getAppInfo().getAlgorithmType());
            AlgorithmTypeContext algorithmTypeContext = new AlgorithmTypeContext(algorithmTypeEnum);
            UserCertInfo certInfo=algorithmTypeContext.getAlgorithmTypeHandle().getUserCertInfo(DN);
            if(Objects.isNull(certInfo)){
                throw new GlobalException(ResultInfoEnum.ALGORITHM_TYPE_ERROR);
            }
            return certInfo;
        } catch (Exception e) {
            System.out.println("Message :"+e.getMessage());
            System.out.println("StackTrace :"+e.getStackTrace());
            e.printStackTrace();
            throw new GlobalException("获取CSR异常");
        }
    }
    public static byte[] signData(String algorithm, byte[] data, PrivateKey key) throws Exception {
        Signature signer = Signature.getInstance(algorithm);
        signer.initSign(key);
        signer.update(data);
        return (signer.sign());
    }


}
