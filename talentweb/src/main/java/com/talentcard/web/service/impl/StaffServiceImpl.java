package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.StaffMapper;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 11:44
 * @description
 */
@Service
public class StaffServiceImpl implements IStaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<StaffPO> page = PageHelper.startPage(pageNum, pageSize);
        List<StaffPO> staffPOList = staffMapper.findStaffByFactor(hashMap);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), staffPOList));
    }

    @Override
    public ResultVO delete(Long staffId) {
        StaffPO staffPO = staffMapper.selectByPrimaryKey(staffId);
        if (staffPO == null) {
            return new ResultVO(2503, "没有此员工");
        }
        staffPO.setDr((byte) 2);
        staffMapper.updateByPrimaryKeySelective(staffPO);
        return new ResultVO(1000);
    }
}
