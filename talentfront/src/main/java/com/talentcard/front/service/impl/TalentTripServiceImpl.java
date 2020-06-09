package com.talentcard.front.service.impl;

import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.TalentTripPO;
import com.talentcard.common.pojo.TripGroupAuthorityPO;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.vo.ScenicVO;
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
    @Autowired
    private TalentActivityCollectMapper talentActivityCollectMapper;
    @Autowired
    private ITalentService iTalentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO findSecondContent(String openId, String name, Byte starLevel, Byte area, Byte order) {

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
        String code = vo.toString();

        List<Long> scenicIdList = tripGroupAuthorityMapper.findByCode(code);
        /**
         *  中间表没找到景区idList，去大表查询
         */
        if (scenicIdList.size() == 0) {
            scenicIdList = scenicEnjoyMapper.findSecondContent(vo.getCardId(), vo.getCategoryList(), vo.getEducation(), vo.getTitle(), vo.getQuality(), vo.getTalentHonour());
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
        Long benefitNum = ActivityResidueNumUtil.getResidueNum();
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
            Byte unit = scenicBO.getUnit();
            List<String> timeList = getTime(unit);
            String startTime = timeList.get(0);
            String endTime = timeList.get(1);
            //指定时间内已领取但没用福利次数
            Integer getBenefitTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 1);
            //指定时间内福利核销次数
            Integer vertifyTimes = talentTripMapper.talentGetTimes(openId, activitySecondContentId, startTime, endTime, (byte) 2);
            Integer getTimes = scenicVO.getTimes() - getBenefitTimes - vertifyTimes;
            scenicVO.setGetTimes(getTimes);
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
        //指定时间内已领取但没用福利次数
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
        talentTripPO.setUsagePeriod(timeList.get(3));
        talentTripMapper.insertSelective(talentTripPO);
        ActivityResidueNumUtil.minusOneResidueNum();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        redisMapUtil.hdel("talentfarmhouse", "benefitNum" + date.format(formatter));

        return new ResultVO(1000, "领取成功");
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
        return scenicVOList;
    }
}
