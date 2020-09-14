package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.chaincodeEntities.Profile;
import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
 智能合约的Profile类
 * */
public interface TransactionProfileApi {
    @PostMapping("transactionProfile/createProfile")
    ResultVO createProfile(@RequestBody Profile profile);

    @PostMapping("transactionProfile/getProfile")
    ResultVO getProfile(@RequestBody Profile profile);

    @PostMapping("transactionProfile/updateProfile")
    ResultVO updateProfile(@RequestBody Profile profile);
}
