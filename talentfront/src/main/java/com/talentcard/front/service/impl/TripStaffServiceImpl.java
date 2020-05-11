package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import com.talentcard.common.mapper.StaffMapper;
import com.talentcard.common.mapper.StaffTripMapper;
import com.talentcard.common.mapper.TalentTripMapper;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.pojo.StaffTripPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITripStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 09:15
 * @description
 */
@Service
public class TripStaffServiceImpl implements ITripStaffService {
    @Autowired
    private TalentTripMapper talentTripMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private StaffTripMapper staffTripMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO register(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        StaffPO ifExistStaff = staffMapper.ifExistStaff(openId);
        if (ifExistStaff != null) {
            return new ResultVO(2501, "当前openId已经成为员工");
        }
        //staff表 insert
        StaffPO staffPO = new StaffPO();
        staffPO.setOpenId(openId);
        staffPO.setName(jsonObject.getString("name"));
        staffPO.setPhone(jsonObject.getString("phone"));
        staffPO.setCreateTime(new Date());
        staffPO.setDr((byte) 1);
        staffMapper.add(staffPO);
        Long staffId = staffPO.getStaffId();

        //staffTrip表 insert
        StaffTripPO staffTripPO = new StaffTripPO();
        staffTripPO.setScenicId(jsonObject.getLong("scenicId"));
        staffTripPO.setStaffId(staffId);
        staffTripPO.setCreateTime(new Date());
        staffTripPO.setDr((byte) 1);
        staffTripPO.setStatus((byte) 1);
        staffTripMapper.insertSelective(staffTripPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO ifEnableRegister(String openId, Long scenicId) {
        Byte status;
        StaffPO staffPO = staffMapper.ifExistStaff(openId);
        Integer staffNum = staffTripMapper.findStaffNum(scenicId);
        if (staffPO != null) {
            //存在当前员工，已经绑定
            status = 1;
        } else {
            //不存在当前员工，未绑定
            status = 2;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        hashMap.put("staffNum", staffNum);
        return new ResultVO(1000, hashMap);
    }
}
