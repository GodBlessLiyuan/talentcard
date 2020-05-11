package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.StaffTripMapper;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 11:44
 * @description
 */
@Service
public class StaffServiceImpl implements IStaffService {
    @Autowired
    StaffTripMapper staffTripMapper;
    @Override
    public ResultVO findStaff(String openId) {
        //旅游
        return null;
    }
}
