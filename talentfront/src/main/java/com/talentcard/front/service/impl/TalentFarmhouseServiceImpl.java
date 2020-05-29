package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.bo.FarmhouseBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.FarmhouseGroupAuthorityPO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.service.ITalentFarmhouseService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.utils.TalentActivityUtil;
import com.talentcard.front.vo.FarmhouseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;
    @Autowired
    private FarmhouseMapper farmhouseMapper;
    @Autowired
    private FarmhouseGroupAuthorityMapper farmhouseGroupAuthorityMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO findSecondContent(String openId) {
        //先从redis中获取数据
        String mapStr = this.redisMapUtil.hget(openId, "findTalentType");
        log.info("com.talentcard.front.service.impl.TalentFarmhouseServiceImpl: TalentType Data In Redis [{}]:",mapStr,
                " : With Redis Key [{}]:", openId, "findTalentType");
        TalentTypeVO talentTypeVO = null;
        if(!StringUtils.isEmpty(mapStr)){
            talentTypeVO = StringToObjUtil.strToObj(mapStr, TalentTypeVO.class);
        }else {
            TalentPO talentPO = talentMapper.selectByOpenId(openId);
            if (talentPO == null) {
                return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
            }
            UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
            if (userCurrentInfoPO == null) {
                return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
            }

            String category = userCurrentInfoPO.getTalentCategory();
            ArrayList categoryList = null;
            //拆分人才类别
            if (category != null && !"".equals(category)) {
                categoryList = TalentActivityUtil.splitCategory(category);
            }
            //封装人才特点加redis缓存
            talentTypeVO.setCardId(talentPO.getCardId());
            talentTypeVO.setCategory(userCurrentInfoPO.getTalentCategory());
            talentTypeVO.setEducation(userCurrentInfoPO.getEducation());
            talentTypeVO.setTitle(userCurrentInfoPO.getPtCategory());
            talentTypeVO.setQuality(userCurrentInfoPO.getPqCategory());
            talentTypeVO.setTalentHonour(userCurrentInfoPO.getHonourId());
            talentTypeVO.setCategoryList(categoryList);
            log.info("com.talentcard.front.service.impl.TalentFarmhouseServiceImpl: TalentType Data In DB [{}]:", talentTypeVO);
            this.redisMapUtil.hset(openId,"findTalentType", JSON.toJSONString(talentTypeVO));
        }


        //先从redis中找
        String code = talentTypeVO.toString();
        List<FarmhouseVO> list = null;

        String s_list = redisMapUtil.hget("talentfarmhouse", code);
        if(!StringUtils.isEmpty(s_list)){
            list = StringToObjUtil.strToObj(mapStr, List.class);
        }
        String lon = redisMapUtil.hget("talentfarmhouse","benefitNum");

        Map<String, Object> resultMap = new HashMap<>(2);
        log.info("com.talentcard.front.service.impl.TalentFarmhouseServiceImpl:Talent Benefit Farmhouse In Redis [{}]:",
                s_list, " : With Redis Key [{}]:", "talentfarmhouse ", code);
        log.info("com.talentcard.front.service.impl.TalentFarmhouseServiceImpl: Number Of Benefit In Redis [{}]:",
                lon, " : With Redis Key [{}]:", "talentfarmhouse benefitNum");
        /**
         * 农家乐idList，去中间表查询
         */
        if (list == null || StringUtils.isEmpty(lon)) {

            List<Long> farmhouseIdList = farmhouseGroupAuthorityMapper.findByCode(code);
            /**
             *  中间表没找到景区idList，去大表查询
             */
            if (farmhouseIdList == null || farmhouseIdList.size() == 0) {

                farmhouseIdList = farmhouseEnjoyMapper.findSecondContent(talentTypeVO.getCardId(),
                        talentTypeVO.getCategoryList(), talentTypeVO.getEducation(), talentTypeVO.getTitle(),
                        talentTypeVO.getQuality(), talentTypeVO.getTalentHonour());

                if (farmhouseIdList.size() == 0) {
                    return new ResultVO(2504, "查无景区！");
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
            //景区表，查询符合条件的景区
            List<FarmhousePO> farmhousePOList = farmhouseMapper.findEnjoyFarmhouse(farmhouseIdList);

            if(farmhousePOList !=null && farmhousePOList.size() > 0){
                List<FarmhouseVO> farmhouseVOList = FarmhouseVO.convert(farmhousePOList);
                //拼结果
                Long benefitNum = ActivityResidueNumUtil.getResidueNum();
                resultMap.put("farmhouseVOList", farmhouseVOList);
                resultMap.put("benefitNum", benefitNum);
                redisMapUtil.hset("talentfarmhouse", code, JSON.toJSONString(farmhouseVOList));
                redisMapUtil.hset("talentfarmhouse","benefitNum", String.valueOf(benefitNum));
                log.info("com.talentcard.front.service.impl.TalentFarmhouseServiceImpl: farmhouseVOList In DB [{}]:",
                        farmhouseVOList);
                log.info("com.talentcard.front.service.impl.TalentFarmhouseServiceImpl: benefitNum In DB [{}]:",
                        farmhouseVOList);
            }else {
                resultMap.put("farmhouseVOList", new ArrayList<>(0));
                resultMap.put("benefitNum", 0);
            }

        }else{
            resultMap.put("farmhouseVOList", list);
            resultMap.put("benefitNum", Long.parseLong(lon));
        }
        return new ResultVO(1000, resultMap);
    }

    @Override
    public ResultVO findOne(Long activitySecondContentId) {
        FarmhouseBO farmhouseBO = farmhouseMapper.findOne(activitySecondContentId);
        if (farmhouseBO == null) {
            return new ResultVO(2504, "查无农家乐");
        }
        FarmhouseVO farmhouseVO = FarmhouseVO.convert(farmhouseBO);
        return new ResultVO(1000, farmhouseVO);
    }

}
