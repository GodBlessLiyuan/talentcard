package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TalentCertificationBO;
import com.talentcard.common.mapper.CategoryMapper;
import com.talentcard.common.pojo.CategoryPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICategoryService;
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
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ResultVO add(String name, String description) {
        CategoryPO categoryPO = new CategoryPO();
        categoryPO.setName(name);
        categoryPO.setDescription(description);
        categoryPO.setCreateTime(new Date());
        categoryPO.setUpdateTime(new Date());
        categoryPO.setDr((byte) 1);
        categoryPO.setStatus((byte) 2);
        categoryMapper.insertSelective(categoryPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO edit(Long categoryId, String description) {
        CategoryPO categoryPO = categoryMapper.selectByPrimaryKey(categoryId);
        if (categoryPO == null) {
            return new ResultVO(2710, "查无此人才类别！");
        }
        categoryPO.setDescription(description);
        categoryMapper.updateByPrimaryKeySelective(categoryPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upDown(Long categoryId, Byte status) {
        CategoryPO categoryPO = categoryMapper.selectByPrimaryKey(categoryId);
        if (categoryPO == null) {
            return new ResultVO(2710, "查无此人才类别！");
        }
        categoryPO.setStatus(status);
        categoryMapper.updateByPrimaryKeySelective(categoryPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO query(int pageNum, int pageSize, String name, Byte status) {
        Page<CategoryPO> page = PageHelper.startPage(pageNum, pageSize);
        List<CategoryPO> categoryPOList = categoryMapper.query(name, status);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), categoryPOList));
    }

    @Override
    public ResultVO findOne(Long categoryId) {
        CategoryPO categoryPO = categoryMapper.selectByPrimaryKey(categoryId);
        if (categoryPO == null) {
            return new ResultVO(2710, "查无此人才类别！");
        }
        return new ResultVO(1000, categoryPO);
    }
}
