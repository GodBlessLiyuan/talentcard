package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.StaffMapper;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

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
    @Autowired
    private ScenicMapper scenicMapper;

    @Override
    public ResultVO ifEnableRegister(String openId, Long activityFirstContent, Long activitySecondContent) {
        Byte status;
        StaffPO staffPO = staffMapper.findOneByOpenId(openId);
        Integer staffNum = staffMapper.findStaffNum(activityFirstContent, activitySecondContent);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO register(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        Long activitySecondContent = jsonObject.getLong("activitySecondContent");
        StaffPO ifExistStaff = staffMapper.findOneByOpenId(openId);
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
        staffPO.setActivityFirstContent((long) 1);
        staffPO.setActivitySecondContent(activitySecondContent);
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(activitySecondContent);
        staffPO.setActivitySecondContentName(scenicPO.getName());
        staffMapper.insertSelective(staffPO);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO findStaffBusinessService(String openId) {
        StaffPO staffPO = staffMapper.findOneByOpenId(openId);
        if (staffPO == null) {
            return new ResultVO(1100, null);
        }
//        StaffBO staffBO = staffMapper.findOne(openId);

//        StaffVO staffVO = StaffVO.convert(staffTripBO);
        return new ResultVO(1000, null);
    }
}
