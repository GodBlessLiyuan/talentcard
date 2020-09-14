package com.talentcard.web.client;

import com.bsnsapi.fabric.api.TransactionProfileApi;
import org.springframework.cloud.openfeign.FeignClient;
/***
 智能合约的Profile类
 * */
@FeignClient("bsnssdk")
public interface TransactionProfileClient extends TransactionProfileApi {
}
