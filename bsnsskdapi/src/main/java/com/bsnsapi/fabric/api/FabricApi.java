package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqUserRegister;

import java.io.IOException;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-03 20:10
 */
public interface FabricApi {
    @PostMapping("fabric/userRegister")
    ResultVO userRegister(@RequestBody ReqUserRegister reqUserRegister) throws IOException;
}
