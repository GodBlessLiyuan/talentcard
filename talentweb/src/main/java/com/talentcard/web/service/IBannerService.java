package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.BannerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 19:55
 * @description: banner配置
 * @version: 1.0
 */
public interface IBannerService {
    ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap);

    ResultVO insert(BannerDTO dto);

    ResultVO status(Long bid, Byte status);

    ResultVO delete(Long bid);

    ResultVO upload(MultipartFile file);
}
