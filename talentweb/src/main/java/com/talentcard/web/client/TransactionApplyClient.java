package com.talentcard.web.client;

import com.bsnsapi.fabric.api.TransactionApplyApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("bsnssdk")
public interface TransactionApplyClient extends TransactionApplyApi {
}
