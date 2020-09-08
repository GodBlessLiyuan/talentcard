package sbsdk.controller;

import com.alibaba.fastjson.JSON;
import com.bsnsapi.fabric.dto.CreateDTO;
import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbsdk.config.MyConfig;
import sbsdk.talentcard.bsnsdk.client.fabric.service.NodeService;
import sbsdk.talentcard.bsnsdk.client.fabric.service.TransactionService;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqGetTransaction;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResGetTransaction;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrowNo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: 智能合约接口
 * @author: liyuan
 * @data 2020-09-03 14:59
 */
@RestController
@RequestMapping("transaction")
@Deprecated
public class TransactionController {
    @Autowired
    private MyConfig myConfig;

    private ReqKeyEscrow initDTO(CreateDTO createDTO){
        myConfig.initConfig();
        String[] args={JSON.toJSONString(createDTO.getTicket())};
//        createDTO.getReqKeyEscrow().setArgs(args);
//        return createDTO.getReqKeyEscrow();
        return null;
    }
   /***
    我感觉只有 "funcName":"createTicket", 不一样，其他都一样。
    reqkey.setFuncName("getTicketInfo");
    reqkey.setFuncName("getHistoryForTicketStatus");
    * */
    @PostMapping("reqChainCode")
    public ResultVO reqChainCode(@RequestBody CreateDTO createDTO) throws IOException {
        ResKeyEscrow resKeyEscrow=TransactionService.reqChainCode(initDTO(createDTO));
        return new ResultVO(1000,resKeyEscrow);
    }
    /***
     reqkey.setFuncName("set");
     * */
    @PostMapping("nodeTrans")
    public ResultVO nodeTrans(@RequestBody CreateDTO createDTO) throws NoSuchAlgorithmException {
        ResKeyEscrowNo resKeyEscrowNo = TransactionService.nodeTrans(initDTO(createDTO));
        return new ResultVO(1000,resKeyEscrowNo);
    }
    @PostMapping("getTransaction")
    public ResultVO getTransaction(@RequestBody ReqGetTransaction reqData){
        ResGetTransaction transaction = NodeService.getTransaction(reqData);
        return new ResultVO(1000,transaction);
    }

}
