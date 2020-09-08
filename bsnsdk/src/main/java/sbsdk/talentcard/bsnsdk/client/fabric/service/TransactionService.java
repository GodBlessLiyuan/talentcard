package sbsdk.talentcard.bsnsdk.client.fabric.service;


import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrowNo;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrowNo;
import sbsdk.talentcard.bsnsdk.entity.transactionHeader.TransactionRequest;
import sbsdk.talentcard.bsnsdk.entity.transactionHeader.TransactionUser;
import sbsdk.talentcard.bsnsdk.util.common.Common;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;
import sbsdk.talentcard.bsnsdk.util.common.Nonce;
import sbsdk.talentcard.bsnsdk.util.enums.ResultInfoEnum;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static sbsdk.talentcard.bsnsdk.util.common.TransData.getTransdata;

@Service
public class TransactionService {

    /**
     * 密钥托管模式交易处理
     *
     * @param kes
     * @return
     * @throws IOException
     */
    public static ResKeyEscrow reqChainCode(@NotNull ReqKeyEscrow kes) throws IOException {
        String api = Config.config.getApi() + "/api/fabric/v1/node/reqChainCode";
        BaseReqModel<ReqKeyEscrow> req = new BaseReqModel<ReqKeyEscrow>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        kes.setNonce(Nonce.getNonceString());
        req.setBody(kes);

        HttpService<ReqKeyEscrow, ResKeyEscrow> httpService = new HttpService<ReqKeyEscrow, ResKeyEscrow>();
        /****
         * 包含信息头 header、body、签名值mac；body是类：ResKeyEscrow
         * */
        BaseResModel<ResKeyEscrow> res = httpService.post(req, api, Config.config.getCert(), ResKeyEscrow.class);
        return res.getBody();
    }

    /**
     * 密钥非托管模式交易
     *
     * @param reqkey
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static ResKeyEscrowNo nodeTrans(@NotNull ReqKeyEscrow reqkey) throws NoSuchAlgorithmException {
        String api = Config.config.getApi() + "/api/fabric/v1/node/trans";

        TransactionUser user = null;
        try {
            user = Config.config.getKeyStore().loadUser(reqkey.getUserName(), Config.config.getAppCode());
            user.setMspId(Config.config.getAppInfo().getMspId());
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.USER_CERTIFICATE_ERROR.getMsg());
        }

        TransactionRequest request = new TransactionRequest();
        request.setChanelId(Config.config.getAppInfo().getChannelId());
        request.setArgs(Common.StringBytesConvert(reqkey.getArgs()));
        request.setTransientMap(reqkey.getTransientData());
        request.setChaincodeId(reqkey.getChainCode());
        request.setFcn(reqkey.getFuncName());

        String transData = null;
        try {
            transData = getTransdata(request, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.TRANSACTION_CONVERSION_ERROR.getMsg());
        }

        ReqKeyEscrowNo keyNo = new ReqKeyEscrowNo();
        keyNo.setTransData(transData);
        BaseReqModel<ReqKeyEscrowNo> req = new BaseReqModel<ReqKeyEscrowNo>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(keyNo);
        HttpService<ReqKeyEscrowNo, ResKeyEscrowNo> httpService = new HttpService<ReqKeyEscrowNo, ResKeyEscrowNo>();
        BaseResModel<ResKeyEscrowNo> res = httpService.post(req, api, Config.config.getCert(), ResKeyEscrowNo.class);

        return res.getBody();

    }


}
