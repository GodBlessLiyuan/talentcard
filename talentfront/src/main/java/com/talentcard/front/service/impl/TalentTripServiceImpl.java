package com.talentcard.front.service.impl;

import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.TalentTripPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.utils.TalentActivityUtil;
import com.talentcard.front.vo.ScenicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:24
 * @description
 */
@Service
public class TalentTripServiceImpl implements ITalentTripService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private TalentTripMapper talentTripMapper;

    @Override
    public ResultVO findSecondContent(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (talentPO == null || userCurrentInfoPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        Long cardId = talentPO.getCardId();
        ArrayList categoryList = null;
        //拆分人才类别
        if (!(userCurrentInfoPO.getTalentCategory().equals("") && userCurrentInfoPO != null)) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        List<Long> scenicIdList = null;
        //景区idList，去大表查询
        scenicIdList = scenicEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
        if (scenicIdList == null) {
            return new ResultVO(2504, "查无景区！");
        }
        scenicIdList = scenicIdList.stream().distinct().collect(Collectors.toList());
        List<ScenicPO> scenicPOList = scenicMapper.findEnjoyScenic(scenicIdList);
        List<ScenicVO> scenicVOList = ScenicVO.convert(scenicPOList);
        HashMap<String, Object> hashMap = new HashMap<>();
        Long benefitNum = ActivityResidueNumUtil.getResidueNum();
        hashMap.put("scenicList", scenicVOList);
        hashMap.put("benefitNum", benefitNum);
        return new ResultVO(1000, hashMap);
    }

    @Override
    public ResultVO findOne(Long scenicId) {
        ScenicBO scenicBO = scenicMapper.findOne(scenicId);
        ScenicVO scenicVO = ScenicVO.convert(scenicBO);
        return new ResultVO(1000, scenicVO);
    }

    @Override
    public ResultVO getBenefit(String openId, Long activitySecondContentId) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        TalentTripPO ifExistOne = talentTripMapper.findOneNotExpired(openId, currentTime);
        //平台次数是否为0
        if (ActivityResidueNumUtil.getResidueNum() <= 0) {
            return new ResultVO(1001, "当前福利已被领取完");
        }
        //用户是否已领取还未过期的福利
        if (ifExistOne != null) {
            return new ResultVO(1002, "当前人才已经有没用完的券");
        }
        //用户可用次数是否已超过限额
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(activitySecondContentId);
        if (scenicPO == null) {
            return new ResultVO(2504, "查无景区");
        }
        Byte unit = scenicPO.getUnit();
        Integer times = scenicPO.getTimes();
        List<String> timeList = getTime(unit);
        String startTime = timeList.get(0);
        String endTime = timeList.get(1);
        Integer getTimes = talentTripMapper.TalentGetTimes(openId, startTime, endTime);
        if (getTimes >= times) {
            return new ResultVO(1003, "当前用户已经把当月/年次数用尽");
        }
        TalentTripPO talentTripPO = new TalentTripPO();
        talentTripPO.setOpenId(openId);
        talentTripPO.setScenicId(activitySecondContentId);
        talentTripPO.setCreateTime(new Date());
        //设置有效时间
        Date effectiveTime = simpleDateFormat.parse(timeList.get(2));
        talentTripPO.setEffectiveTime(effectiveTime);
        talentTripPO.setStatus((byte) 1);
        talentTripMapper.insertSelective(talentTripPO);
        return new ResultVO(1000, "领取成功");
    }

    /**
     * 获得可用次数的起始时间和结束时间，以及有效时间
     *
     * @param unit
     * @return
     */
    private static List<String> getTime(Byte unit) {
        String startTime;
        String endTime;
        String effectiveTime;
        List<String> timeList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int lastDay = 0;
        if (month == 2) {
            lastDay = calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        //年
        if (unit == 1) {
            startTime = year + "-01-01 00:00:00";
            endTime = year + "-12-31 23:59:59";
            //月
        } else {
            startTime = year + "-" + month + "-" + lastDay + " 00:00:00";
            endTime = year + "-" + month + "-" + lastDay + " 23:59:59";
        }
        effectiveTime = year + "-" + month + "-" + lastDay + " 23:59:59";
        timeList.add(startTime);
        timeList.add(endTime);
        timeList.add(effectiveTime);
        return timeList;
    }

}
