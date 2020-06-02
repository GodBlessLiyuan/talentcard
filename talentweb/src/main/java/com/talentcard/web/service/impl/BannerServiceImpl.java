package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.BannerMapper;
import com.talentcard.common.pojo.BannerPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.BannerDTO;
import com.talentcard.web.service.IBannerService;
import com.talentcard.web.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Page<BannerPO> page = PageHelper.startPage(pageNum, pageSize);
        List<BannerPO> pos = bannerMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), BannerVO.convert(pos)));
    }

    @Override
    public ResultVO insert(BannerDTO dto) {
        BannerPO po = BannerDTO.buildPO(new BannerPO(), dto);
        bannerMapper.insert(po);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO status(Long bid, Byte status) {
        bannerMapper.status(bid, status);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(Long bid) {
        bannerMapper.deleteByPrimaryKey(bid);
        return new ResultVO(1000);
    }
}
