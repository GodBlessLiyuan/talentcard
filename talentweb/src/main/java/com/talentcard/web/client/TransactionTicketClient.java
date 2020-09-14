package com.talentcard.web.client;

import com.bsnsapi.fabric.api.TransactionTicketApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-08 17:57
 */
@FeignClient("bsnssdk")
public interface TransactionTicketClient extends TransactionTicketApi {
}
