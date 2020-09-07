package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.dto.CreateDTO;
import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-03 16:14

 其他的引用形式：
 @FeignClient("item-service")
 public interface BrandClient extends BrandApi{}
 */
public interface TransactionApi {
    @PostMapping("transaction/reqChainCode")
    ResultVO reqChainCode(@RequestBody CreateDTO createDTO) throws IOException;
}
