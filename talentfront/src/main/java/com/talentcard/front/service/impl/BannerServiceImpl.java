package com.talentcard.front.service.impl;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.BannerMapper;
import com.talentcard.common.pojo.BannerPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-02 10:13
 * @description
 */
@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO query() {
        List<BannerPO> bannerPOList = bannerMapper.bannerQuery();
        String picture = "";
        for (BannerPO bannerPO : bannerPOList) {
            picture = bannerPO.getPicture();
            if (picture != null && !picture.equals("")) {
                picture = filePathConfig.getPublicBasePath() + picture;
                bannerPO.setPicture(picture);
            }
        }
        return new ResultVO(1000, bannerPOList);
    }
}
