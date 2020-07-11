package com.talentcard.web.service;

import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.ScenicDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:04
 * @description: 景区
 * @version: 1.0
 */
public interface IScenicService {
    ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap);

    ResultVO edit(ScenicDTO dto);

    ResultVO status(Long scenicId, Byte status);

    ResultVO detail(Long scenicId);

    ResultVO upload(MultipartFile file);

    ResultVO setTripTimes(List<CardPO> cardPOList);
}
