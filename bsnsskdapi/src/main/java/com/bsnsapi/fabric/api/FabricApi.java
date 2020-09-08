package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.vo.ResultVO;
import com.bsnsapi.fabric.chaincodeEntities.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.io.IOException;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-03 20:10
 */
public interface FabricApi {
    @PostMapping("fabric/userRegister")
    ResultVO userRegister(@RequestBody String reqUserRegister) throws IOException;

}
