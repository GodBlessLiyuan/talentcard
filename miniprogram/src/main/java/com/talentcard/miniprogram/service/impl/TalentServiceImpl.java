package com.talentcard.miniprogram.service.impl;


import com.alibaba.fastjson.JSON;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentCertificationInfoPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.TalentActivityUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.miniprogram.service.ITalentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-02 09:50
 * @description
 */
@Service
public class TalentServiceImpl implements ITalentService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;

    /**
     * 获取微信用户信息，类型，会员卡号等
     *
     * @param openId
     * @return
     */
    @Override
    public TalentTypeVO getTalentInfo(String openId) {

        String redisCache = redisMapUtil.hget(openId, "getTalentInfo");
        if (!StringUtils.isEmpty(redisCache)) {
            TalentTypeVO vo = StringToObjUtil.strToObj(redisCache, TalentTypeVO.class);
            if (vo != null) {
                return vo;
            }
        }

        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            talentPO = getDefaultTalent();
            if (talentPO == null) {
                return null;
            }
        }

        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (userCurrentInfoPO == null) {
            return null;
        }

        Long cardId = talentPO.getCardId();
        String talentCategory = userCurrentInfoPO.getTalentCategory();
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        Long talentHonour = userCurrentInfoPO.getHonourId();
        //拆分人才类别
        ArrayList categoryList = null;
        if (!StringUtils.isEmpty(talentCategory)) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        /**
         * 拆分学历；职称；职业资格；人才荣誉等
         */
        ArrayList educationList = null;
        ArrayList titleList = null;
        ArrayList qualityList = null;
        ArrayList honourList = null;
        String educationString = "";
        String titleString = "";
        String qualityString = "";
        String honourString = "";
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (talentCertificationInfoPO != null) {
            educationString = talentCertificationInfoPO.getEducation();
            titleString = talentCertificationInfoPO.getPtCategory();
            qualityString = talentCertificationInfoPO.getPqCategory();
            honourString = talentCertificationInfoPO.getHonourId();
        }
        if (!StringUtils.isEmpty(educationString)) {
            educationList = TalentActivityUtil.splitCategory(educationString);
        }

        if (!StringUtils.isEmpty(titleString)) {
            titleList = TalentActivityUtil.splitCategory(titleString);
        }

        if (!StringUtils.isEmpty(qualityString)) {
            qualityList = TalentActivityUtil.splitCategory(qualityString);
        }

        if (!StringUtils.isEmpty(honourString)) {
            honourList = TalentActivityUtil.splitCategory(honourString);
        }


        TalentTypeVO vo = new TalentTypeVO();
        vo.setCardId(cardId);
        vo.setCategoryList(categoryList);
        vo.setEducation(education);
        vo.setTitle(title);
        vo.setQuality(quality);
        vo.setTalentHonour(talentHonour);
        vo.setCategory(talentCategory);

        //学历；职称；职业资格；人才荣誉
        vo.setEducationList(educationList);
        vo.setQualityList(qualityList);
        vo.setTitleList(titleList);
        vo.setHonourList(honourList);
        vo.setEducationString(educationString);
        vo.setTitleString(titleString);
        vo.setQualityString(qualityString);
        vo.setHonourString(honourString);

        redisMapUtil.hset(openId, "getTalentInfo", JSON.toJSONString(vo));
        return vo;
    }

    /**
     * 清除用户redis缓存
     *
     * @param openId
     */
    @Override
    public void clearRedisCache(String openId) {
        /**
         * 清除redis缓存
         */
        this.redisMapUtil.del(openId);
    }

    /**
     * 获取游客账号
     *
     * @return
     */
    private TalentPO getDefaultTalent() {
        TalentPO talentPO = null;
        String defaultTalent = redisMapUtil.hget(TalentConstant.DEFAULT_TALENT_OPENID, "getTalentInfo");
        if (!StringUtils.isEmpty(defaultTalent)) {
            talentPO = StringToObjUtil.strToObj(defaultTalent, TalentPO.class);
        }
        if (talentPO == null) {
            talentPO = talentMapper.selectByOpenId(TalentConstant.DEFAULT_TALENT_OPENID);
            redisMapUtil.hset(TalentConstant.DEFAULT_TALENT_OPENID, "getTalentInfo", JSON.toJSONString(talentPO));
        }
        return talentPO;
    }

}
