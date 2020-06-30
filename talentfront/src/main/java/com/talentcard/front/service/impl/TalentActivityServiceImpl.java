package com.talentcard.front.service.impl;

import com.talentcard.common.bo.ActivityCardTicketBO;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentActivityHistoryPO;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.service.ITalentActivityService;
import com.talentcard.front.service.ITalentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 20:04
 * @description
 */
@Service
public class TalentActivityServiceImpl implements ITalentActivityService {
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private UserCardMapper userCardMapper;
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;
    @Autowired
    private ITalentService iTalentService;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private TalentTripMapper talentTripMapper;

    @Override
    public ResultVO findFirstContent(String openId) {

        if(StringUtils.isEmpty(openId)){
            openId = TalentConstant.DEFAULT_TALENT_OPENID;
        }

        TalentTypeVO vo = iTalentService.getTalentInfo(openId);

        if (vo == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }


        String talentType = "show_"+vo.toString();

        Map<String, Object> resultHashMap = new HashMap<>(2);
        /**
         * 每一个活动挨个枚举
         */

        String s_trip = redisMapUtil.hget("talentTrip", talentType);
        if (StringUtils.isEmpty(s_trip)) {
            List<Long> scenicIdList;
            /**
             * 旅游
             */
            scenicIdList = scenicEnjoyMapper.findSecondContent(vo.getCardId(), vo.getCategoryList(), vo.getEducationList(), vo.getTitleList(), vo.getQualityList(), vo.getHonourList());
            if (scenicIdList.size() > 0) {
                resultHashMap.put("trip", 1);
                s_trip = String.valueOf(1);
            } else {
                resultHashMap.put("trip", 2);
                s_trip = String.valueOf(2);
            }

            redisMapUtil.hset("talentTrip", talentType, s_trip);
        } else {
            try {
                resultHashMap.put("trip", Integer.valueOf(s_trip));
            } catch (Exception e) {
                e.printStackTrace();
                resultHashMap.put("trip", Integer.valueOf(2));
            }
        }

        String s_farmhouse = redisMapUtil.hget("talentfarmhouse", talentType);
        if (StringUtils.isEmpty(s_farmhouse)) {
            List<Long> farmhouseList;

            /**
             * 农家乐
             */
            farmhouseList = farmhouseEnjoyMapper.findSecondContent(vo.getCardId(), vo.getCategoryList(), vo.getEducationList(), vo.getTitleList(), vo.getQualityList(), vo.getHonourList());
            if (farmhouseList.size() > 0) {
                resultHashMap.put("farmhouse", 1);
                s_farmhouse = String.valueOf(1);
            } else {
                resultHashMap.put("farmhouse", 2);
                s_farmhouse = String.valueOf(2);
            }

            redisMapUtil.hset("talentfarmhouse", talentType, s_farmhouse);
        } else {
            try {
                resultHashMap.put("farmhouse", Integer.valueOf(s_farmhouse));
            } catch (Exception e) {
                e.printStackTrace();
                resultHashMap.put("farmhouse", 2);
            }
        }

        return new ResultVO(1000, resultHashMap);
    }

    @Override
    public ResultVO findHistory(String openId) {

        if(TalentConstant.isDefaultTalent(openId)){
            return new ResultVO(1000,new ArrayList<>(0));
        }

        List<TalentActivityHistoryPO> resultList = talentActivityHistoryMapper.findByOpenId(openId);
        return new ResultVO(1000, resultList);
    }

    @Override
    public String getOpenId(String cardNum) {
        return userCardMapper.findOpenIdByCardNum(cardNum);
    }

    @Override
    public ResultVO findActivityTicket(String openId, Byte type) {

        if(TalentConstant.isDefaultTalent(openId)){
            return new ResultVO(1000,new ArrayList<>(0));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        List<ActivityCardTicketBO> talentTripPOList = talentTripMapper.findActivityCardTicket(openId, currentTime, type);
        return new ResultVO(1000, talentTripPOList);
    }
}
