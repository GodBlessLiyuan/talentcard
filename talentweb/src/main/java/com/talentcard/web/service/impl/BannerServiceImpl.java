package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.BannerMapper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.BannerDTO;
import com.talentcard.web.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 19:55
 * @description: banner配置
 * @version: 1.0
 */
@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        return null;
    }

    @Override
    public ResultVO insert(BannerDTO dto) {
        return null;
    }

    @Override
    public ResultVO status(Long bid, Byte status) {
        return null;
    }

    @Override
    public ResultVO delete(Long bid) {
        return null;
    }
}
