package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.HonourMapper;
import com.talentcard.common.pojo.HonourPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IHonourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:58
 * @description
 */
@Service
public class HonourServiceImpl implements IHonourService {
    @Autowired
    HonourMapper honourMapper;

    @Override
    public ResultVO add(String name, String description) {
        HonourPO honourPO = new HonourPO();
        honourPO.setName(name);
        honourPO.setDescription(description);
        honourPO.setCreateTime(new Date());
        honourPO.setUpdateTime(new Date());
        honourPO.setDr((byte) 1);
        honourPO.setStatus((byte) 2);
        honourMapper.insertSelective(honourPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO edit(Long honourId, String description) {
        HonourPO honourPO = honourMapper.selectByPrimaryKey(honourId);
        if (honourPO == null) {
            return new ResultVO(2720, "查无此人才荣誉！");
        }
        honourPO.setDescription(description);
        honourMapper.updateByPrimaryKeySelective(honourPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upDown(Long honourId, Byte status) {
        HonourPO honourPO = honourMapper.selectByPrimaryKey(honourId);
        if (honourPO == null) {
            return new ResultVO(2720, "查无此人才荣誉！");
        }
        honourPO.setStatus(status);
        honourMapper.updateByPrimaryKeySelective(honourPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO query(int pageNum, int pageSize, String name, Byte status) {
        Page<HonourPO> page = PageHelper.startPage(pageNum, pageSize);
        List<HonourPO> honourPOList = honourMapper.query(name, status);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), honourPOList));
    }

    @Override
    public ResultVO findOne(Long honourId) {
        HonourPO honourPO = honourMapper.selectByPrimaryKey(honourId);
        if (honourPO == null) {
            return new ResultVO(2720, "查无此人才荣誉！");
        }
        return new ResultVO(1000, honourPO);
    }
}
