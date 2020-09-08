package sbsdk.controller;


import com.talentcard.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sbsdk.talentcard.bsnsdk.client.fabric.service.UserService;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrowEnroll;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqUserRegister;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrowEnroll;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResUserRegister;
import sbsdk.config.MyConfig;

import java.io.IOException;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-03 20:00
 */
@RequestMapping("fabric")
@RestController
public class FabricController {
    @Autowired
    private MyConfig myConfig;
    @PostMapping("userRegister")
    public ResultVO userRegister(@RequestBody ReqUserRegister reqUserRegister) throws IOException {
        myConfig.initConfig();
        ResUserRegister resUserRegister = UserService.userRegister(reqUserRegister);
        return new ResultVO(1000,resUserRegister);
    }
    @PostMapping("userEnroll")
    public ResultVO userEnroll(@RequestBody ReqKeyEscrowEnroll r ) throws IOException {
        myConfig.initConfig();
        ResKeyEscrowEnroll resKeyEscrowEnroll = UserService.userEnroll(r);
        return new ResultVO(1000,resKeyEscrowEnroll);
    }

}
