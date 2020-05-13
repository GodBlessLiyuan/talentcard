package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.StaffMapper;
import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TalentTripMapper;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.pojo.TalentActivityHistoryPO;
import com.talentcard.common.pojo.TalentTripPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IStaffService;
import com.talentcard.front.utils.StaffActivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
    private TalentTripMapper talentTripMapper;
    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;

    @Override
    public ResultVO ifEnableRegister(String openId, Long activityFirstContentId, Long activitySecondContentId) {
        Byte status;
        StaffPO staffPO = staffMapper.findOneByOpenId(openId);
        Integer staffNum = staffMapper.findStaffNum(activityFirstContentId, activitySecondContentId);
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
        Long activityFirstContentId = jsonObject.getLong("activityFirstContentId");
        Long activitySecondContentId = jsonObject.getLong("activitySecondContentId");
        StaffPO ifExistStaff = staffMapper.findOneByOpenId(openId);
        if (ifExistStaff != null) {
            return new ResultVO(2501, "当前openId已经成为员工");
        }
        //staff表 insert
        StaffPO staffPO = new StaffPO();
        staffPO.setOpenId(openId);
        staffPO.setName(jsonObject.getString("name"));
        staffPO.setPhone(jsonObject.getString("phone"));
        staffPO.setSex(jsonObject.getByte("sex"));
        staffPO.setIdCard(jsonObject.getString("idCard"));
        staffPO.setCreateTime(new Date());
        staffPO.setDr((byte) 1);
        staffPO.setActivityFirstContentId(activityFirstContentId);
        staffPO.setActivitySecondContentId(activitySecondContentId);
        /**
         * 设置二级目录名字，用工具类
         * 在这里扩展
         */
        String activitySecondContentName = StaffActivityUtil.getActivitySecondContentName(activityFirstContentId,
                activitySecondContentId);
        if (activitySecondContentName == "") {
            return new ResultVO(2502, "没有此活动");
        }
        staffPO.setActivitySecondContentName(activitySecondContentName);
        staffMapper.insertSelective(staffPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(String openId) {
        StaffPO staffPO = staffMapper.findOneByOpenId(openId);
        if (staffPO == null) {
            return new ResultVO(1001, staffPO);
        }
        return new ResultVO(1000, staffPO);
    }

    public StaffServiceImpl() {
        super();
    }

    @Override
    public ResultVO vertify(String talentOpenId, String staffOpenId,
                            Long activityFirstContentId, Long activitySecondContentId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO tripVertify(String talentOpenId, String staffOpenId, Long activitySecondContentId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        //判断人才旅游表里是否有状态为1的记录
        TalentTripPO talentTripPO = talentTripMapper.findOneNotExpired(talentOpenId, currentTime);
        if (talentTripPO == null) {
            return new ResultVO(1001, "该人才没资格");
        }
        if (!talentTripPO.getScenicId().equals(activitySecondContentId)) {
            return new ResultVO(1001, "该人才领的是其他的景区的！");
        }
        //找到staffId，更新人才旅游表
        talentTripPO.setStatus((byte) 2);
        StaffPO staffPO = staffMapper.findOneByOpenId(staffOpenId);
        if (staffPO == null) {
            return new ResultVO(2503, "没有此员工");
        }
        Long staffId = staffPO.getStaffId();
        talentTripPO.setStaffId(staffId);
        talentTripPO.setUpdateTime(new Date());
        talentTripMapper.updateByPrimaryKeySelective(talentTripPO);
        //更新历史表
        TalentActivityHistoryPO talentActivityHistoryPO = new TalentActivityHistoryPO();
        talentActivityHistoryPO.setOpenId(talentOpenId);
        talentActivityHistoryPO.setStaffId(staffId);
        talentActivityHistoryPO.setActivityFirstContentId((long) 1);
        talentActivityHistoryPO.setActivitySecondContentId(activitySecondContentId);
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(activitySecondContentId);
        if (scenicPO == null) {
            return new ResultVO(2504);
        }
        talentActivityHistoryPO.setActivitySecondContentName(scenicPO.getName());
        talentActivityHistoryPO.setCreateTime(new Date());
        talentActivityHistoryPO.setDr((byte) 1);
        talentActivityHistoryMapper.insertSelective(talentActivityHistoryPO);
        return new ResultVO(1000);
    }
}
