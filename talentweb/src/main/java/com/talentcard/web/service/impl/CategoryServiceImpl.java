package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TalentCertificationBO;
import com.talentcard.common.mapper.CategoryMapper;
import com.talentcard.common.pojo.CategoryPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.ICategoryService;
import com.talentcard.web.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private ILogService logService;

    @Override
    public ResultVO add(String name, String description, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        Integer ifExistName = categoryMapper.ifExistName(name);
        if (ifExistName != 0) {
            return new ResultVO(2730, "人才类别或者人才荣誉重复了！");
        }
        CategoryPO categoryPO = new CategoryPO();
        categoryPO.setName(name);
        categoryPO.setDescription(description);
        categoryPO.setCreateTime(new Date());
        categoryPO.setUpdateTime(new Date());
        categoryPO.setDr((byte) 1);
        categoryPO.setStatus((byte) 2);
        categoryMapper.insertSelective(categoryPO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentCategory,
                "新增人才类别\"%s\"", name);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO edit(Long categoryId, String description, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        CategoryPO categoryPO = categoryMapper.selectByPrimaryKey(categoryId);
        if (categoryPO == null) {
            return new ResultVO(2710, "查无此人才类别！");
        }
        categoryPO.setDescription(description);
        categoryPO.setUpdateTime(new Date());
        categoryMapper.updateByPrimaryKeySelective(categoryPO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentCategory,
                "编辑人才类别\"%s\"", categoryPO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upDown(Long categoryId, Byte status, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        CategoryPO categoryPO = categoryMapper.selectByPrimaryKey(categoryId);
        if (categoryPO == null) {
            return new ResultVO(2710, "查无此人才类别！");
        }
        categoryPO.setStatus(status);
        categoryPO.setUpdateTime(new Date());
        categoryMapper.updateByPrimaryKeySelective(categoryPO);
        if (status == 1) {
            logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentCategory,
                    "上架人才类别\"%s\"", categoryPO.getName());
        } else {
            logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentCategory,
                    "下架人才类别\"%s\"", categoryPO.getName());
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO query(int pageNum, int pageSize, String name, Byte status, Byte type) {
        Page<CategoryPO> page = PageHelper.startPage(pageNum, pageSize);
        List<CategoryPO> categoryPOList = categoryMapper.query(name, status, type);
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
