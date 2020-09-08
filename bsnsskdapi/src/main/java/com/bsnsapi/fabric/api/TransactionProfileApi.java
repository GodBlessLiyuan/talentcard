package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.chaincodeEntities.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
 智能合约的Profile类
 * */
public interface TransactionProfileApi {
    @PostMapping("transactionProfile/createProfile")
    String createProfile(@RequestBody Profile profile, HttpServletRequest request);

    @PostMapping("transactionProfile/getProfile")
    String getProfile(@RequestBody Profile profile, HttpServletRequest request);

    @PostMapping("transactionProfile/updateProfile")
    String updateProfile(@RequestBody Profile profile, HttpServletRequest request);
}
