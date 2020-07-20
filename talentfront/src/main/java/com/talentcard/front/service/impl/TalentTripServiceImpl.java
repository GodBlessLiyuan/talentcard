package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.vo.ScenicVO;
import com.talentcard.front.vo.TripAvailableVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private TalentTripMapper talentTripMapper;
    @Autowired
    private TripGroupAuthorityMapper tripGroupAuthorityMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private TalentActivityCollectMapper talentActivityCollectMapper;
    @Autowired
    private ITalentService iTalentService;
    @Autowired
    private CardMapper cardMapper;

    @Override
    public ResultVO findSecondContent(String openId, String name, Byte starLevel, Byte area, Byte order) {

        if (StringUtils.isEmpty(openId)) {
            openId = TalentConstant.DEFAULT_TALENT_OPENID;
        }

        /**
         * 获取用户类型
         */
        TalentTypeVO vo = iTalentService.getTalentInfo(openId);
        if (vo == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }

        /**
         * 景区idList，去中间表查询
         */
        String code = "list_" + vo.toString();

        String s_scenicIdList = redisMapUtil.hget("talentTrip", code);

        List<Long> scenicIdList = null;
        if (!StringUtils.isEmpty(s_scenicIdList)) {
            scenicIdList = StringToObjUtil.strToObj(s_scenicIdList, List.class);
        }
        if (scenicIdList == null) {
            scenicIdList = tripGroupAuthorityMapper.findByCode(code);
            /**
             *  中间表没找到景区idList，去大表查询
             */
            if (scenicIdList == null || scenicIdList.size() == 0) {
                scenicIdList = scenicEnjoyMapper.findSecondContent(vo.getCardId(), vo.getCategoryList(), vo.getEducationList(), vo.getTitleList(), vo.getQualityList(), vo.getHonourList());
                if (scenicIdList.size() == 0) {
                    //查无景区
                    return new ResultVO(1000, null);
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


            redisMapUtil.hset("talentTrip", code, JSON.toJSONString(scenicIdList));
        }

        //景区表，查询符合条件的景区
        List<ScenicPO> scenicPOList = scenicMapper.findEnjoyScenic(scenicIdList, name, starLevel, area, order);
        List<ScenicVO> scenicVOList = ScenicVO.convert(scenicPOList);
//        //我的收藏
//        List<Long> activitySecondContentIdList = talentActivityCollectMapper
//                .findSecondContentIdByCollect(openId, (long) 1);
//        scenicVOList = ScenicVO.assignIfCollect(scenicVOList, activitySecondContentIdList);
        //剩余次数
        scenicVOList = setGetTimes(openId, scenicVOList);
        //拼结果
        HashMap<String, Object> hashMap = new HashMap<>(2);
        //Long benefitNum = ActivityResidueNumUtil.getResidueNum();
        Integer benefitNum = getTalentTripAllNum(openId).getAvailable();
        hashMap.put("scenicList", scenicVOList);
        hashMap.put("benefitNum", benefitNum);
        return new ResultVO(1000, hashMap);
    }

    @Override
    public ResultVO findOne(Long activitySecondContentId, String openId) {
        ScenicBO scenicBO = scenicMapper.findOne(activitySecondContentId);
        if (scenicBO == null) {
            return new ResultVO(2504, "查无景区");
        }
        ScenicVO scenicVO = ScenicVO.convert(scenicBO);
        /**
         * openId不为null，说明要给是否收藏，次数等信息
         */
        //算次数
        if (openId != null) {
          /*  Byte unit = scenicBO.getUnit();
            List<String> timeList = getTime(unit);
            String startTime = timeList.get(0);
            String endTime = timeList.get(1);
            //指定时间内已领取但没用福利次数
            Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
            //指定时间内福利核销次数
            Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
            Integer getTimes = scenicVO.getTimes() - getBenefitTimes - vertifyTimes;*/

            TripAvailableVO tripAvailableVO = getTalentTripAllNum(openId);
            scenicVO.setGetTimes(tripAvailableVO.getAvailable());
            scenicVO.setTimes(tripAvailableVO.getTotal());
            //我的收藏
            List<Long> activitySecondContentIdList = talentActivityCollectMapper.findSecondContentIdByCollect(openId, (long) 1);
            scenicVO = ScenicVO.assignIfCollect(scenicVO, activitySecondContentIdList);
        }
        //使用期限
        scenicVO = ScenicVO.assignUsagePeriod(scenicVO);
        return new ResultVO(1000, scenicVO);
    }

    @Override
    public ResultVO getResidueTimes(String openId, Long activitySecondContentId) {
       /* TalentPO talentPO = talentMapper.selectByOpenId(openId);
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
        //指定时间内已领取但没用福利次数
        Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
        //指定时间内福利核销次数
        Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
        Integer getTimes = getBenefitTimes + vertifyTimes;
        Integer residueTimes = 0;
        if (getTimes <= times) {
            residueTimes = times - getTimes;
        }*/

        Integer residueTimes = getTalentTripAllNum(openId).getAvailable();
        HashMap<String, Object> result = new HashMap<>(1);
        result.put("residueTimes", residueTimes);
        return new ResultVO(1000, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO getBenefit(String openId, Long activitySecondContentId) throws ParseException {

        if (StringUtils.isEmpty(openId) || StringUtils.equalsIgnoreCase(openId, TalentConstant.DEFAULT_TALENT_OPENID)) {
            return new ResultVO(1001, "当前福利已被领取完");
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        TalentTripPO ifExistOne = talentTripMapper.findOneNotExpired(openId, activitySecondContentId, currentTime);
        /**
         * 判断用户身份
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO(2520, "当前人才无权限！");
        }
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
        if (scenicPO.getStatus() == 2) {
            return new ResultVO(2511, "该景区已经下架");
        }
        Byte unit = scenicPO.getUnit();
        Integer times = scenicPO.getTimes();
        List<String> timeList = getTime(unit);

        /*
        //指定时间内已领取福利次数
        Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
        //指定时间内福利核销次数
        Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
        Integer getTimes = getBenefitTimes + vertifyTimes;
        if (getTimes >= times) {
            return new ResultVO(1003, "当前用户已经把当月/年次数用尽");
        }*/
        Calendar c = Calendar.getInstance();
        String endTime = DateUtil.date2Str(c.getTime(),DateUtil.YMD_HMS);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        String startTime = DateUtil.date2Str(c.getTime(),DateUtil.YMD_HMS);

        //限制当天只能核销一次
        Integer dailyTotal = talentTripMapper.talentGetTimes(openId,activitySecondContentId, startTime, endTime,(byte)2 );
        if(dailyTotal > 0){
            return new ResultVO(1001, "当前福利已被领取完");
        }

        Integer available = getTalentTripAllNum(openId).getAvailable();
        if (available <= 0) {
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
        talentTripPO.setUsagePeriod(timeList.get(3));
        talentTripMapper.insertSelective(talentTripPO);
        ActivityResidueNumUtil.minusOneResidueNum();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        redisMapUtil.hdel("talentfarmhouse", "benefitNum" + date.format(formatter));

        redisMapUtil.del(openId);

        return new ResultVO(1000, "领取成功");
    }

    /**
     * 获取人才旅游的一年中的总次数
     *
     * @param openId
     * @return
     */
    private TripAvailableVO getTalentTripAllNum(String openId) {

        String s_available = redisMapUtil.hget(openId, TalentConstant.TALENT_AVAILABLE);
        if (!StringUtils.isEmpty(s_available)) {
            try {
                TripAvailableVO availableVO = StringToObjUtil.strToObj(s_available, TripAvailableVO.class);
                if (availableVO != null) {
                    return availableVO;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TalentTypeVO talentTypeVO = iTalentService.getTalentInfo(openId);
        if (talentTypeVO == null) {
            return new TripAvailableVO();
        }

        CardPO cardPO = this.cardMapper.selectByPrimaryKey(talentTypeVO.getCardId());

        //算次数
        if (openId != null) {
            /**
             * 当月的申请次数
             */
            List<String> timeList = getTime((byte) 3);
            String startTime = timeList.get(0);
            String endTime = timeList.get(1);
            //指定时间内已领取但没用福利次数
            Integer getBenefitTimes = talentTripMapper.talentGetTimesByAll(openId, startTime, endTime, (byte) 1);
            //指定时间内福利核销次数
            /**
             * 全年的核销次数
             */
            timeList = getTime((byte) 1);
            startTime = timeList.get(0);
            endTime = timeList.get(1);

            Integer vertifyTimes = talentTripMapper.talentGetTimesByAll(openId, startTime, endTime, (byte) 2);
            Integer getTimes = cardPO.getTripTimes() - getBenefitTimes - vertifyTimes;

            if (getTimes < 0) {
                getTimes = 0;
            }
            TripAvailableVO tripAvailableVO = new TripAvailableVO();
            tripAvailableVO.setAvailable(getTimes);
            tripAvailableVO.setTotal(cardPO.getTripTimes());

            redisMapUtil.hset(openId, TalentConstant.TALENT_AVAILABLE, JSON.toJSONString(tripAvailableVO));

            return tripAvailableVO;
        }
        return new TripAvailableVO();
    }

    /**
     * 获得可用次数的起始时间和结束时间，以及有效时间
     *
     * @param unit
     * @return
     */
    public static List<String> getTime(Byte unit) {
        String startTime;
        String endTime;
        String effectiveTime;
        String effectiveTimeStart;
        String usage;
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
        usage = year + "-" + month + "-" + "01" + "~" + year + "-" + month + "-" + lastDay;
        timeList.add(startTime);
        timeList.add(endTime);
        timeList.add(effectiveTime);
        timeList.add(usage);
        return timeList;
    }


    /**
     * 设置剩余次数
     *
     * @param openId
     * @param scenicVOList
     * @return
     */
    public List<ScenicVO> setGetTimes(String openId, List<ScenicVO> scenicVOList) {
        String key = "trip_gettime_" + JSONObject.toJSONString(scenicVOList);
        String s_scenicVO = this.redisMapUtil.hget(openId, key);
        if (!StringUtils.isEmpty(s_scenicVO)) {
            List<ScenicVO> list = StringToObjUtil.strToObj(s_scenicVO, List.class);
            if (list != null) {
                return list;
            }
        }

        Long activitySecondContentId;
        Byte unit;
        List<String> timeList;
        String startTime;
        String endTime;
        for (ScenicVO scenicVO : scenicVOList) {
            unit = scenicVO.getUnit();
            timeList = TalentTripServiceImpl.getTime(unit);
            startTime = timeList.get(0);
            endTime = timeList.get(1);
            activitySecondContentId = scenicVO.getScenicId();
            //指定时间内已领取但没用福利次数
            Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
            //指定时间内福利核销次数
            Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
            Integer getTimes = scenicVO.getTimes() - getBenefitTimes - vertifyTimes;
            scenicVO.setGetTimes(getTimes);
        }

        this.redisMapUtil.hset(openId, key, JSON.toJSONString(scenicVOList));
        return scenicVOList;
    }
}
