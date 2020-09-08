package sbsdk.controller;

import com.bsnsapi.fabric.chaincodeEntities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbsdk.config.MyConfig;
import sbsdk.convert.TransactionStatus;
import sbsdk.convert.Profile2Ticket;
import sbsdk.talentcard.bsnsdk.client.fabric.service.TransactionService;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrow;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:  4-1 人才数据智能合约方法名称
 * @author: liyuan
 * @data 2020-09-08 15:40
 */
@RestController
@RequestMapping("transactionProfile")
public class TransactionProfileController {
    @Autowired
    private MyConfig myConfig;
    @PostMapping("createProfile")
    public String createProfile(@RequestBody Profile profile, HttpServletRequest request){
        try{
            TransactionService.reqChainCode(this.profileFunName(profile,request));
            return TransactionStatus.OK.getStatus();
        }catch (Exception e){
            e.printStackTrace();
            return TransactionStatus.Error.getStatus();
        }
    }

    private ReqKeyEscrow profileFunName(Profile profile,HttpServletRequest request) {
        myConfig.initConfig();
        ReqKeyEscrow reqKeyEscrow = Profile2Ticket.convert(profile);
        String[] url = request.getRequestURI().split("/");
        // /transactionProfile/getProfile
        reqKeyEscrow.setFuncName(url[2]);
        return reqKeyEscrow;
    }

    @PostMapping("getProfile")
    public String getProfile(@RequestBody Profile profile,HttpServletRequest request){
        try{
            ResKeyEscrow resKeyEscrow = TransactionService.reqChainCode(this.profileFunName(profile, request));
            return resKeyEscrow.getCcRes().getCcData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("updateProfile")
    public String updateProfile(@RequestBody Profile profile,HttpServletRequest request){
        try{
            TransactionService.reqChainCode(this.profileFunName(profile,request));
            return TransactionStatus.OK.getStatus();
        }catch (Exception e){
            e.printStackTrace();
            return TransactionStatus.Error.getStatus();
        }
    }
}
