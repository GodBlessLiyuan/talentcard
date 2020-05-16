package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.dto.MessageDTO;
import com.talentcard.front.service.IStaffService;
import com.talentcard.front.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;
    @Autowired
    private TalentFarmhouseMapper talentFarmhouseMapper;
    @Autowired
    private FarmhouseGroupAuthorityMapper farmhouseGroupAuthorityMapper;
    @Autowired
    private FarmhouseMapper farmhouseMapper;

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
        //判断数量是否满足10
        Integer staffNum = staffMapper.findStaffNum(activityFirstContentId, activitySecondContentId);
        if (staffNum >= 10) {
            return new ResultVO(2505, "超出数量限制");
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
    @Transactional(rollbackFor = Exception.class)
    public ResultVO tripVertify(HttpServletRequest httpServletRequest, String talentOpenId, String staffOpenId, Long activitySecondContentId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        //判断人才旅游表里是否有状态为1的记录
        TalentTripPO talentTripPO = talentTripMapper.findOneNotExpired(talentOpenId, activitySecondContentId, currentTime);
        if (talentTripPO == null) {
            return new ResultVO(1001, "该人才没资格");
        }
        if (!talentTripPO.getScenicId().equals(activitySecondContentId)) {
            return new ResultVO(1001, "该人才领的是其他的景区的！");
        }
        //得到staffPO
        StaffPO staffPO = staffMapper.findOneByOpenId(staffOpenId);
        if (staffPO == null) {
            return new ResultVO(2503, "没有此员工");
        }
        if (!staffPO.getActivitySecondContentId().equals(activitySecondContentId)) {
            return new ResultVO(1001, "该人才领的是其他的景区的，和员工不一致！");
        }

        //找到staffId，更新人才旅游表
        talentTripPO.setStatus((byte) 2);
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
        //得到ip
        String ipAddress = HttpServletRequestUtil.getIpAddr(httpServletRequest);
        talentActivityHistoryPO.setIpAddress(ipAddress);
        talentActivityHistoryMapper.insertSelective(talentActivityHistoryPO);

        //得到当前检验人数
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        String startTime = year + "-" + month + "-" + date + " 00:00:00";
        String endTime = year + "-" + month + "-" + date + " 23:59:59";
        Long vertifyNum = talentActivityHistoryMapper.getVertifyNum(staffId, (long) 1, activitySecondContentId, startTime, endTime);
        HashMap<String, Object> result = new HashMap<>();
        result.put("vertifyNum", vertifyNum);
        sendMessage(talentOpenId, staffOpenId, (long) 1, activitySecondContentId);
        return new ResultVO(1000, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO farmhouseVertify(HttpServletRequest httpServletRequest, String talentOpenId, String staffOpenId, Long activitySecondContentId) {
        TalentPO talentPO = talentMapper.selectByOpenId(talentOpenId);
        if (talentPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        //得到staffPO
        StaffPO staffPO = staffMapper.findOneByOpenId(staffOpenId);
        if (staffPO == null) {
            return new ResultVO(2503, "没有此员工");
        }
        if (!staffPO.getActivitySecondContentId().equals(activitySecondContentId)) {
            return new ResultVO(1001, "该人才领的是其他的景区的，和员工不一致！");
        }
        //从uci表里取得人才类别
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (userCurrentInfoPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        Long cardId = talentPO.getCardId();
        String category = userCurrentInfoPO.getTalentCategory();
        ArrayList categoryList = null;
        //拆分人才类别
        if (category != null && !category.equals("")) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        List<Long> farmhouseIdList;
        /**
         * 农家乐idList，去中间表查询
         */
        String code = getMiddleTableString(cardId, category, education, title, quality);
        farmhouseIdList = farmhouseGroupAuthorityMapper.findByCode(code);
        /**
         *  中间表没找到景区idList，去大表查询
         */
        if (farmhouseIdList.size() == 0) {
            farmhouseIdList = farmhouseEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
            if (farmhouseIdList.size() == 0) {
                return new ResultVO(1001, "查无景区!不具备此农家乐权益!");
            }
            //去重
            farmhouseIdList = farmhouseIdList.stream().distinct().collect(Collectors.toList());
            //新增中间表
            FarmhouseGroupAuthorityPO farmhouseGroupAuthorityPO = new FarmhouseGroupAuthorityPO();
            farmhouseGroupAuthorityPO.setAuthorityCode(code);
            for (Long farmhouseId : farmhouseIdList) {
                farmhouseGroupAuthorityPO.setFarmhouseId(farmhouseId);
                farmhouseGroupAuthorityMapper.insertSelective(farmhouseGroupAuthorityPO);
            }
        } else {
            //去重
            farmhouseIdList = farmhouseIdList.stream().distinct().collect(Collectors.toList());
        }
        //flag用来判断talent是否拥有此农家乐的权限
        Integer flag = 0;
        for (Long farmhouseId : farmhouseIdList) {
            if (farmhouseId.equals(activitySecondContentId)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            return new ResultVO(1001, "核销失败，不具备此农家乐权益");
        }

        //更新人才农家乐表
        TalentFarmhousePO talentFarmhousePO = new TalentFarmhousePO();
        talentFarmhousePO.setOpenId(talentOpenId);
        talentFarmhousePO.setStatus((byte) 2);
        Long staffId = staffPO.getStaffId();
        talentFarmhousePO.setStaffId(staffId);
        talentFarmhousePO.setUpdateTime(new Date());
        talentFarmhousePO.setFarmhouseId(activitySecondContentId);
        talentFarmhousePO.setDr((byte) 1);
        //得到农家乐信息
        FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(activitySecondContentId);
        if (farmhousePO == null) {
            return new ResultVO(2502, "没有此活动");
        }
        talentFarmhousePO.setDiscount(farmhousePO.getDiscount());
        talentFarmhouseMapper.insertSelective(talentFarmhousePO);

        //更新历史表
        TalentActivityHistoryPO talentActivityHistoryPO = new TalentActivityHistoryPO();
        talentActivityHistoryPO.setOpenId(talentOpenId);
        talentActivityHistoryPO.setStaffId(staffId);
        talentActivityHistoryPO.setActivityFirstContentId((long) 2);
        talentActivityHistoryPO.setActivitySecondContentId(activitySecondContentId);
        talentActivityHistoryPO.setActivitySecondContentName(farmhousePO.getName());
        talentActivityHistoryPO.setCreateTime(new Date());
        talentActivityHistoryPO.setDr((byte) 1);
        //得到ip
        String ipAddress = HttpServletRequestUtil.getIpAddr(httpServletRequest);
        talentActivityHistoryPO.setIpAddress(ipAddress);
        talentActivityHistoryMapper.insertSelective(talentActivityHistoryPO);
        //得到当前检验人数
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        String startTime = year + "-" + month + "-" + date + " 00:00:00";
        String endTime = year + "-" + month + "-" + date + " 23:59:59";
        Long vertifyNum = talentActivityHistoryMapper.getVertifyNum(staffId, (long) 2, activitySecondContentId, startTime, endTime);
        HashMap<String, Object> result = new HashMap<>();
        result.put("vertifyNum", vertifyNum);
        sendMessage(talentOpenId, staffOpenId, (long) 2, activitySecondContentId);
        return new ResultVO(1000, result);
    }

    /**
     * 根据五个条件获得中间表唯一字符串
     *
     * @param cardId
     * @param category
     * @param education
     * @param title
     * @param quality
     * @return
     */
    public static String getMiddleTableString(Long cardId, String category,
                                              Integer education, Integer title, Integer quality) {
        String middleTableString;
        if (cardId == null) {
            cardId = (long) 0;
        }
        if (category == null || category.equals("")) {
            category = "0";
        }
        if (education == null) {
            education = 0;
        }
        if (title == null) {
            title = 0;
        }
        if (quality == null) {
            quality = 0;
        }
        middleTableString = "" + cardId + "-" + category + "-" + education + "-" + title + "-" + quality;
        return middleTableString;
    }

    @Override
    public ResultVO sendMessage(String talentOpenId, String staffOpenId,
                                Long activityFirstContentId, Long activitySecondContentId) {
        String keyword1 = "";
        String keyword2 = "";
        TalentPO talentPO = talentMapper.selectByOpenId(talentOpenId);
        if (activityFirstContentId == 1) {
            ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(activitySecondContentId);
            keyword1 = scenicPO.getName() + "免门票服务";
            keyword2 = scenicPO.getName();
        } else if (activityFirstContentId == 2) {
            FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(activitySecondContentId);
            keyword1 = farmhousePO.getName() + farmhousePO.getDiscount() + "折优惠服务";
            keyword2 = farmhousePO.getName();
        }
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentOpenId);
        //开头
        messageDTO.setFirst("您好，您的人才服务已使用成功");
        messageDTO.setKeyword1(keyword1);
        messageDTO.setKeyword2(keyword2);
        messageDTO.setKeyword3("个人");
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword3(currentTime);
        //模版编号
        messageDTO.setTemplateId(2);
        //结束
        messageDTO.setRemark("感谢使用！");
        messageDTO.setUrl(FrontParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        return null;
    }

}
