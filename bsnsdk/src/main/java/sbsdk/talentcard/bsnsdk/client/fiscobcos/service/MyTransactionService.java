package sbsdk.talentcard.bsnsdk.client.fiscobcos.service;


import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fiscobcos.ReqGetTxCountByBlockNumber;
import sbsdk.talentcard.bsnsdk.entity.req.fiscobcos.ReqGetTxReceiptByTxHash;
import sbsdk.talentcard.bsnsdk.entity.req.fiscobcos.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResGetTxCount;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResGetTxCountByBlockNumber;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResGetTxReceiptByTxHash;
import sbsdk.talentcard.bsnsdk.entity.res.fiscobcos.ResKeyEscrow;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class MyTransactionService {

    /**
     * 密钥托管模式下调用智能合约接口
     * @param kes
     * @return
     * @throws IOException
     */
    public static ResKeyEscrow reqChainCode(@NotNull ReqKeyEscrow kes) throws IOException{
    	 String api =  Config.config.getApi() + "/api/fiscobcos/v1/node/reqChainCode";
         BaseReqModel<ReqKeyEscrow> req = new  BaseReqModel<ReqKeyEscrow>();
//         kes.setNonce(Nonce.getNonceString());
         System.out.println(kes.getEncryptionValue());
         req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
         req.setBody(kes);
         HttpService<ReqKeyEscrow,ResKeyEscrow> httpService =new HttpService<ReqKeyEscrow,ResKeyEscrow>();
         BaseResModel<ResKeyEscrow> res = httpService.post(req,api, Config.config.getCert(),ResKeyEscrow.class);
         return res.getBody();

    }


    /**
     * 获取应用内的交易总数
     * @return
     * @throws IOException
     */
    public static ResGetTxCount getTxCount() throws IOException{
        String api =  Config.config.getApi() + "/api/fiscobcos/v1/node/getTxCount";
        BaseReqModel<ReqKeyEscrow> req = new  BaseReqModel<>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService httpService =new HttpService();
        BaseResModel<ResGetTxCount> res = httpService.post(req,api, Config.config.getCert(),ResGetTxCount.class);
        return res.getBody();

    }

    /**
     * 获取块内的交易总数接口
     * @return
     * @throws IOException
     */
    public static ResGetTxCountByBlockNumber getTxCountByBlockNumber(@NotNull ReqGetTxCountByBlockNumber reqGetTxCountByBlockNumber) throws IOException{
        String api =  Config.config.getApi() + "/api/fiscobcos/v1/node/getTxCountByBlockNumber";
        BaseReqModel<ReqGetTxCountByBlockNumber> req = new  BaseReqModel<>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(reqGetTxCountByBlockNumber);
        HttpService<ReqGetTxCountByBlockNumber, ResGetTxCountByBlockNumber> httpService =new HttpService();
        BaseResModel<ResGetTxCountByBlockNumber> res = httpService.post(req,api, Config.config.getCert(),ResGetTxCountByBlockNumber.class);
        return res.getBody();
    }

    /**
     * 获取交易回执接口
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    public static ResGetTxReceiptByTxHash getTxReceiptByTxHash(ReqGetTxReceiptByTxHash reqGetTxReceiptByTxHash) throws  NoSuchAlgorithmException{
        String api =  Config.config.getApi() + "/api/fiscobcos/v1/node/getTxReceiptByTxHash";
        BaseReqModel<ReqGetTxReceiptByTxHash> req = new  BaseReqModel<>();
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        req.setBody(reqGetTxReceiptByTxHash);
        HttpService<ReqGetTxReceiptByTxHash, ResGetTxReceiptByTxHash> httpService =new HttpService();
        BaseResModel<ResGetTxReceiptByTxHash> res = httpService.post(req,api, Config.config.getCert(),ResGetTxReceiptByTxHash.class);
        return res.getBody();
    }
}
