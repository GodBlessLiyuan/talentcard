package com.talentcard.web.client;

import com.bsnsapi.fabric.api.TransactionApplyApi;
import org.springframework.cloud.openfeign.FeignClient;
/**
 *   bsnssdk  支持大小写
 * */
@FeignClient("bsnssdk")
public interface TransactionApplyClient extends TransactionApplyApi {
}
