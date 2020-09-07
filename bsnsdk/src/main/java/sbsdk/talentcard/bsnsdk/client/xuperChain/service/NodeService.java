package sbsdk.talentcard.bsnsdk.client.xuperChain.service;

import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.xuperChain.ReqGetBlockInformation;
import sbsdk.talentcard.bsnsdk.entity.req.xuperChain.ReqGetTransaction;
import sbsdk.talentcard.bsnsdk.entity.req.xuperChain.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.xuperChain.ResGetBlockInformation;
import sbsdk.talentcard.bsnsdk.entity.res.xuperChain.ResGetTransaction;
import sbsdk.talentcard.bsnsdk.entity.res.xuperChain.ResKeyEscrow;
import sbsdk.talentcard.bsnsdk.util.common.HttpService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class NodeService {

    /**
     * 密钥托管模式下调用智能合约接口
     *
     * @param kes
     * @return
     * @throws IOException
     */
    public static ResKeyEscrow reqChainCode(@NotNull ReqKeyEscrow kes) throws IOException {
        String api = Config.config.getApi() + "/api/xuperchain/v1/node/reqChainCode";
        BaseReqModel<ReqKeyEscrow> req = new BaseReqModel<ReqKeyEscrow>(kes);
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqKeyEscrow, ResKeyEscrow> httpService = new HttpService<ReqKeyEscrow, ResKeyEscrow>();
        BaseResModel<ResKeyEscrow> res = httpService.post(req, api, Config.config.getCert(), ResKeyEscrow.class);
        return res.getBody();

    }

    /**
     * 获取交易信息
     *
     * @param reqData
     */
    public static ResGetTransaction getTxInfoByTxHash(ReqGetTransaction reqData) {
        String api = Config.config.getApi() + "/api/xuperchain/v1/node/getTxInfoByTxHash";
        BaseReqModel<ReqGetTransaction> req = new BaseReqModel<ReqGetTransaction>(reqData);
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqGetTransaction, ResGetTransaction> httpService = new HttpService<ReqGetTransaction, ResGetTransaction>();
        BaseResModel<ResGetTransaction> res = httpService.post(req, api, Config.config.getCert(), ResGetTransaction.class);

        return res.getBody();
    }

    /**
     * 获取块信息
     *
     * @param reqData
     */
    public static ResGetBlockInformation getBlockInfo(ReqGetBlockInformation reqData) {
        String api = Config.config.getApi() + "/api/xuperchain/v1/node/getBlockInfo";
        BaseReqModel<ReqGetBlockInformation> req = new BaseReqModel<ReqGetBlockInformation>(reqData);
        req.setReqHeader(Config.config.getUserCode(),Config.config.getAppCode());
        HttpService<ReqGetBlockInformation, ResGetBlockInformation> httpService = new HttpService<ReqGetBlockInformation, ResGetBlockInformation>();
        BaseResModel<ResGetBlockInformation> res = httpService.post(req, api, Config.config.getCert(), ResGetBlockInformation.class);
        return res.getBody();
    }

}
