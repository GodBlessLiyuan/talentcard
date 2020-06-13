package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.bo.FarmhouseBO;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.FarmhouseEnjoyMapper;
import com.talentcard.common.mapper.FarmhouseGroupAuthorityMapper;
import com.talentcard.common.mapper.FarmhouseMapper;
import com.talentcard.common.mapper.TalentActivityCollectMapper;
import com.talentcard.common.pojo.FarmhouseGroupAuthorityPO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.service.ITalentFarmhouseService;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.vo.FarmhouseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:24
 * @description
 */
@Service
@Slf4j
public class TalentFarmhouseServiceImpl implements ITalentFarmhouseService {
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;
    @Autowired
    private FarmhouseMapper farmhouseMapper;
    @Autowired
    private FarmhouseGroupAuthorityMapper farmhouseGroupAuthorityMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private TalentActivityCollectMapper talentActivityCollectMapper;
    @Autowired
    private ITalentService iTalentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO findSecondContent(String openId, String name, Byte area, Byte order) {

        if(StringUtils.isEmpty(openId) || StringUtils.equalsIgnoreCase(openId, "null")){
            openId = TalentConstant.DEFAULT_TALENT_OPENID;
        }
        /**
         * 获取用户类型
         */
        TalentTypeVO talentTypeVO = iTalentService.getTalentInfo(openId);

        if (talentTypeVO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }

        //先从redis中找
        String code = talentTypeVO.toString();
        List<Long> list = null;

        String s_list = redisMapUtil.hget("talentfarmhouse", code);
        if (!StringUtils.isEmpty(s_list)) {
            list = StringToObjUtil.strToObj(s_list, List.class);
        }
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String lon = redisMapUtil.hget("talentfarmhouse", "benefitNum" + date.format(formatter));

        Map<String, Object> resultMap = new HashMap<>(2);

        List<Long> farmhouseIdList = null;
        /**
         * 农家乐idList，去中间表查询
         */
        if (list == null || StringUtils.isEmpty(lon)) {

            farmhouseIdList = farmhouseGroupAuthorityMapper.findByCode(code);
            /**
             *  中间表没找到景区idList，去大表查询
             */
            if (farmhouseIdList == null || farmhouseIdList.size() == 0) {

                farmhouseIdList = farmhouseEnjoyMapper.findSecondContent(talentTypeVO.getCardId(),
                        talentTypeVO.getCategoryList(), talentTypeVO.getEducation(), talentTypeVO.getTitle(),
                        talentTypeVO.getQuality(), talentTypeVO.getTalentHonour());

                if (farmhouseIdList.size() == 0) {
                    //查无景区
                    return new ResultVO(1000, null);
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

            Long benefitNum = ActivityResidueNumUtil.getResidueNum();
            resultMap.put("benefitNum", benefitNum);

            redisMapUtil.hset("talentfarmhouse", "benefitNum" + date.format(formatter), String.valueOf(benefitNum));
            redisMapUtil.hset("talentfarmhouse", code, JSON.toJSONString(farmhouseIdList));
        } else {
            farmhouseIdList = list;
            resultMap.put("benefitNum", Long.parseLong(lon));
        }

        if(farmhouseIdList != null && farmhouseIdList.size() > 0){
            //景区表，查询符合条件的景区
            List<FarmhousePO> farmhousePOList = farmhouseMapper.findEnjoyFarmhouse(farmhouseIdList, name, area, order);

            if (farmhousePOList != null && farmhousePOList.size() > 0) {
                List<FarmhouseVO> farmhouseVOList = FarmhouseVO.convert(farmhousePOList);
                resultMap.put("farmhouseVOList", farmhouseVOList);
            } else {
                resultMap.put("farmhouseVOList", new ArrayList<>(0));
            }
        } else {
            resultMap.put("farmhouseVOList", new ArrayList<>(0));
        }

        return new ResultVO(1000, resultMap);
    }

    @Override
    public ResultVO findOne(Long activitySecondContentId, String openId) {
        FarmhouseBO farmhouseBO = farmhouseMapper.findOne(activitySecondContentId);
        if (farmhouseBO == null) {
            return new ResultVO(2504, "查无农家乐");
        }
        FarmhouseVO farmhouseVO = FarmhouseVO.convert(farmhouseBO);
        /**
         * openId不为null，说明要给是否收藏，次数等信息
         */
        //算次数
        if (openId != null) {
            //我的收藏
            List<Long> activitySecondContentIdList = talentActivityCollectMapper.findSecondContentIdByCollect(openId, (long) 2);
            farmhouseVO = FarmhouseVO.setIfCollect(farmhouseVO, activitySecondContentIdList);
        }
        return new ResultVO(1000, farmhouseVO);
    }

}
