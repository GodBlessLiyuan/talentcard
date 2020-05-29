package com.talentcard.front.service.impl;

import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.utils.TalentActivityUtil;
import com.talentcard.front.vo.ScenicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private TripGroupAuthorityMapper tripGroupAuthorityMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO findSecondContent(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
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
        Long talentHonour = userCurrentInfoPO.getHonourId();
        List<Long> scenicIdList;
        /**景区idList，去中间表查询
         *
         */
        String code = getMiddleTableString(cardId, category, education, title, quality, talentHonour);
        scenicIdList = tripGroupAuthorityMapper.findByCode(code);
        /**
         *  中间表没找到景区idList，去大表查询
         */
        if (scenicIdList.size() == 0) {
            scenicIdList = scenicEnjoyMapper.findSecondContent(cardId, categoryList,
                    education, title, quality, talentHonour);
            if (scenicIdList.size() == 0) {
                return new ResultVO(2504, "查无景区！");
            }
            //去重
            scenicIdList = scenicIdList.stream().distinct().collect(Collectors.toList());

            for (Long scenicId : scenicIdList) {
                //新增中间表
                TripGroupAuthorityPO tripGroupAuthorityPO = new TripGroupAuthorityPO();
                tripGroupAuthorityPO.setAuthorityCode(code);
                tripGroupAuthorityPO.setScenicId(scenicId);
                tripGroupAuthorityMapper.insertSelective(tripGroupAuthorityPO);
            }
        } else {
            //去重
            scenicIdList = scenicIdList.stream().distinct().collect(Collectors.toList());
        }
        //景区表，查询符合条件的景区
        List<ScenicPO> scenicPOList = scenicMapper.findEnjoyScenic(scenicIdList);
        List<ScenicVO> scenicVOList = ScenicVO.convert(scenicPOList);
        //拼结果
        HashMap<String, Object> hashMap = new HashMap<>(2);
        Long benefitNum = ActivityResidueNumUtil.getResidueNum();
        hashMap.put("scenicList", scenicVOList);
        hashMap.put("benefitNum", benefitNum);
        return new ResultVO(1000, hashMap);
    }

    @Override
    public ResultVO findOne(Long activitySecondContentId) {
        ScenicBO scenicBO = scenicMapper.findOne(activitySecondContentId);
        if (scenicBO == null) {
            return new ResultVO(2504, "查无景区");
        }
        ScenicVO scenicVO = ScenicVO.convert(scenicBO);
        return new ResultVO(1000, scenicVO);
    }

    @Override
    public ResultVO getResidueTimes(String openId, Long activitySecondContentId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查无此人");
        }
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(activitySecondContentId);
        if (scenicPO == null) {
            return new ResultVO(2504, "查无景区");
        }
        Byte unit = scenicPO.getUnit();
        Integer times = scenicPO.getTimes();
        List<String> timeList = getTime(unit);
        String startTime = timeList.get(0);
        String endTime = timeList.get(1);
        //指定时间内已领取福利次数
        Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
        //指定时间内福利核销次数
        Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
        Integer getTimes = getBenefitTimes + vertifyTimes;
        Integer residueTimes = 0;
        if (getTimes <= times) {
            residueTimes = times - getTimes;
        }
        HashMap<String, Object> result = new HashMap<>(1);
        result.put("residueTimes", residueTimes);
        return new ResultVO(1000, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO getBenefit(String openId, Long activitySecondContentId) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        TalentTripPO ifExistOne = talentTripMapper.findOneNotExpired(openId, activitySecondContentId, currentTime);
        //用户是否已领取还未过期的福利
        if (ifExistOne != null) {
            return new ResultVO(1002, "当前人才已经有没用完的券");
        }
        //平台次数是否为0
        if (ActivityResidueNumUtil.getResidueNum() <= 0) {
            return new ResultVO(1001, "当前福利已被领取完");
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
        //指定时间内已领取福利次数
        Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
        //指定时间内福利核销次数
        Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
        Integer getTimes = getBenefitTimes + vertifyTimes;
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
        talentTripPO.setDr((byte) 1);
        talentTripMapper.insertSelective(talentTripPO);
        ActivityResidueNumUtil.minusOneResidueNum();

        redisMapUtil.hdel("talentfarmhouse","benefitNum");

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
            startTime = year + "-" + month + "-01 00:00:00";
            endTime = year + "-" + month + "-" + lastDay + " 23:59:59";
        }
        effectiveTime = year + "-" + month + "-" + lastDay + " 23:59:59";
        timeList.add(startTime);
        timeList.add(endTime);
        timeList.add(effectiveTime);
        return timeList;
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
    public static String getMiddleTableString(Long cardId, String category, Integer education,
                                              Integer title, Integer quality, Long talentHonour) {
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
            title = 0;
        }
        if (quality == null) {
            quality = 0;
        }
        if (talentHonour == null) {
            talentHonour = (long) 0;
        }
        middleTableString = "" + cardId + "-" + category + "-"
                + education + "-" + title + "-" + quality + "-" + talentHonour;
        return middleTableString;
    }
}
