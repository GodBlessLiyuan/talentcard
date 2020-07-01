package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EditTalentPolicyDTO;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.IEditTalentService;
import com.talentcard.web.service.ITalentInfoCertificationService;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-28 14:18
 * @description
 */
@Service
public class EditTalentServiceImpl implements IEditTalentService {
    @Autowired
    TalentMapper talentMapper;
    @Autowired
    InsertCertificationMapper insertCertificationMapper;
    @Autowired
    InsertEducationMapper insertEducationMapper;
    @Autowired
    InsertQualityMapper insertQualityMapper;
    @Autowired
    InsertTitleMapper insertTitleMapper;
    @Autowired
    InsertHonourMapper insertHonourMapper;
    @Autowired
    InsertCertApprovalMapper insertCertApprovalMapper;
    @Autowired
    private CertificationMapper certificationMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private TalentHonourMapper talentHonourMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    ITalentService iTalentService;
    @Autowired
    ITalentInfoCertificationService iTalentInfoCertificationService;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    PolicyMapper policyMapper;
    @Autowired
    private CertApprovalMapper certApprovalMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editBasicInfo(BasicInfoDTO basicInfoDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(basicInfoDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        talentPO.setPhone(basicInfoDTO.getPhone());
        talentPO.setPolitical(basicInfoDTO.getPolitical());
        talentPO.setIndustry(basicInfoDTO.getIndustry());
        talentPO.setWorkUnit(basicInfoDTO.getWorkUnit());
        talentPO.setWorkLocation(basicInfoDTO.getWorkLocation());
        talentPO.setWorkLocationType(basicInfoDTO.getWorkLocationType());
        talentMapper.updateByPrimaryKeySelective(talentPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editEducation(EducationDTO educationDTO) {
        String openId = educationDTO.getOpenId();
        /**
         * 链接学历职称职业资格人才荣誉
         */

        EducationPO educationPO = educationMapper.selectByPrimaryKey(educationDTO.getEducId());
        if (educationPO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        educationPO.setGraduateTime(educationDTO.getGraduateTime());
        educationPO.setFirstClass(educationDTO.getFirstClass());
        educationPO.setEducPicture(educationDTO.getEducPicture());
        educationPO.setSchool(educationDTO.getSchool());
        educationPO.setMajor(educationDTO.getMajor());
        educationPO.setEducation(educationDTO.getEducation());
        educationMapper.updateByPrimaryKeySelective(educationPO);
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editProfQuality(ProfQualityDTO profQualityDTO) {
        String openId = profQualityDTO.getOpenId();
        ProfQualityPO profQualityPO = profQualityMapper.selectByPrimaryKey(profQualityDTO.getPqId());
        if (profQualityPO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        profQualityPO.setPicture(profQualityDTO.getPicture());
        profQualityPO.setInfo(profQualityDTO.getInfo());
        profQualityPO.setCategory(profQualityDTO.getCategory());
        profQualityMapper.updateByPrimaryKeySelective(profQualityPO);
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editProfTitle(ProfTitleDTO profTitleDTO) {
        String openId = profTitleDTO.getOpenId();
        ProfTitlePO profTitlePO = profTitleMapper.selectByPrimaryKey(profTitleDTO.getPtId());
        if (profTitlePO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        profTitlePO.setPicture(profTitleDTO.getPicture());
        profTitlePO.setInfo(profTitleDTO.getInfo());
        profTitlePO.setCategory(profTitleDTO.getCategory());
        profTitleMapper.updateByPrimaryKeySelective(profTitlePO);
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editTalentHonour(TalentHonourDTO talentHonourDTO) {
        String openId = talentHonourDTO.getOpenId();
        TalentHonourPO talentHonourPO = talentHonourMapper.selectByPrimaryKey(talentHonourDTO.getThId());
        if (talentHonourPO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        talentHonourPO.setInfo(talentHonourDTO.getInfo());
        talentHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        talentHonourPO.setHonourId(talentHonourDTO.getHonourId());
        talentHonourMapper.updateByPrimaryKeySelective(talentHonourPO);
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editTalentCategory(String openId, String talentCategory) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        talentPO.setCategory(talentCategory);
        talentMapper.updateByPrimaryKeySelective(talentPO);
        //更新uci表
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (userCurrentInfoPO == null) {
            return new ResultVO(2500);
        }
        userCurrentInfoPO.setTalentCategory(talentCategory);
        userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
        /**
         * 更新tci表
         */
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (talentCertificationInfoPO == null) {
            return new ResultVO(2500);
        }
        talentCertificationInfoPO.setTalentCategory(talentCategory);
        talentCertificationInfoMapper.updateByPrimaryKeySelective(talentCertificationInfoPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findPolicy(EditTalentPolicyDTO editTalentPolicyDTO) {
        return new ResultVO(1000, policyUtil(editTalentPolicyDTO));
    }

    @Override
    public ResultVO findTalentCertificationDetail(String openId) {
        HashMap<String, Object> hashMap = new HashMap(3);
        hashMap.put("openId", openId);
        hashMap.put("status", (byte) 1);
        TalentBO talentBO = talentMapper.findOne(hashMap);
        if (talentBO == null) {
            return new ResultVO(2500);
        }
        CardPO cardPO = cardMapper.selectByPrimaryKey(talentBO.getCardId());
        /**
         * 政策查询
         */
        Long talentId = talentBO.getTalentId();
        List<Integer> educationList = educationMapper.queryNameByTalentId(talentId);
        List<Integer> titleList = profTitleMapper.queryNameByTalentId(talentId);
        List<Integer> qualityList = profQualityMapper.queryNameByTalentId(talentId);
        List<Long> honourList = talentHonourMapper.queryNameByTalentId(talentId);
        EditTalentPolicyDTO editTalentPolicyDTO = new EditTalentPolicyDTO();
        editTalentPolicyDTO.setCardId(talentBO.getCardId());
        editTalentPolicyDTO.setCategory(talentBO.getCategory());
        editTalentPolicyDTO.setEducationList(educationList);
        editTalentPolicyDTO.setTitleList(titleList);
        editTalentPolicyDTO.setQualityList(qualityList);
        editTalentPolicyDTO.setHonourList(honourList);
        List<PolicyPO> policyPOList = policyUtil(editTalentPolicyDTO);
        HashMap<String, Object> result = new HashMap<>(2);
        result.put("talentInfo", talentBO);
        result.put("policyPOList", policyPOList);
        result.put("cardInfo", cardPO);
        return new ResultVO(1000, result);
    }

    /**
     * 根据talentId和cardId，高级卡更换高级卡
     *
     * @param talentId
     * @param newCardId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO changeCard(Long talentId, Long newCardId) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        String openId = talentPO.getOpenId();

        //找到uc表status=2，当前正在使用的卡的cardId
        ActivcateBO talentInfo = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (talentInfo == null) {
            return new ResultVO(2500);
        }
        Long oldCardId = talentInfo.getCardId();
        if (newCardId.equals(oldCardId)) {
            return new ResultVO(2671, "更新卡与原本拥有卡号重复！");
        }


        CardPO oldCardPO = cardMapper.selectByPrimaryKey(oldCardId);
        CardPO newCardPO = cardMapper.selectByPrimaryKey(newCardId);
        UserCardPO oldUserCardPO = userCardMapper.selectByPrimaryKey(talentInfo.getUcId());
        if (oldCardPO == null || newCardPO == null || oldUserCardPO == null) {
            return new ResultVO<>(2600, "查无此卡！");
        }
        //当前编号
        String currentNum = oldUserCardPO.getCurrentNum();
        //人才卡编号
        String membershipNumber = oldCardPO.getInitialWord() + oldCardPO.getAreaNum() + currentNum;
        //新增uc表状态1；更新card表
        UserCardPO newUserCardPO = new UserCardPO();
        newUserCardPO.setCardId(newCardId);
        newUserCardPO.setTalentId(talentId);
        newUserCardPO.setNum(membershipNumber);
        newUserCardPO.setName(newCardPO.getTitle());
        newUserCardPO.setCurrentNum(currentNum);
        newUserCardPO.setCreateTime(new Date());
        newUserCardPO.setStatus((byte) 1);
        userCardMapper.insertSelective(newUserCardPO);

        //更新老uc表
        oldUserCardPO.setStatus((byte)3);
        userCardMapper.updateByPrimaryKeySelective(oldUserCardPO);

        //更新认证表状态
        certificationMapper.updateStatusByCertId(talentInfo.getCertId(), (byte) 4);

        /**
         * 更新card表新旧卡
         */
        //新卡待领取数量+1
        newCardPO.setWaitingMemberNum(newCardPO.getWaitingMemberNum() + 1);
        cardMapper.updateByPrimaryKeySelective(newCardPO);
        //旧卡会员数量-1
        oldCardPO.setWaitingMemberNum(oldCardPO.getMemberNum() - 1);
        cardMapper.updateByPrimaryKeySelective(oldCardPO);

        /**
         * 设置旧卡券失效
         */
        if (oldCardId != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", talentInfo.getCode());
            jsonObject.put("card_id", talentInfo.getWxCardId());
            String url = "https://api.weixin.qq.com/card/code/unavailable?access_token="
                    + AccessTokenUtil.getAccessToken();
            WechatApiUtil.postRequest(url, jsonObject);
        }

        /**
         * 用消息模板推送微信消息
         */
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        messageDTO.setFirst("您好，请您领取衢江区人才卡");
        //姓名
        messageDTO.setKeyword1(talentPO.getName());
        //身份证号，屏蔽八位
        String encryptionIdCard = talentPO.getIdCard().substring(0, 9) + "********";
        messageDTO.setKeyword2(encryptionIdCard);
        //领卡机构
        messageDTO.setKeyword3("个人");
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword4(currentTime);
        //模版编号
        messageDTO.setTemplateId(1);
        //结束
        messageDTO.setRemark("领取后可享受多项人才权益哦");
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        /**
         * 清缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    /**
     * 政策查询工具类
     *
     * @param editTalentPolicyDTO
     * @return
     */
    private List<PolicyPO> policyUtil(EditTalentPolicyDTO editTalentPolicyDTO) {
        List<Integer> existEducations = editTalentPolicyDTO.getEducationList();
        List<Integer> existTitles = editTalentPolicyDTO.getTitleList();
        List<Integer> existQualities = editTalentPolicyDTO.getQualityList();
        List<Long> existHonours = editTalentPolicyDTO.getHonourList();
        String[] existCategories = null;
        if (null != editTalentPolicyDTO.getCategory()) {
            existCategories = editTalentPolicyDTO.getCategory().split(",");
        }

        Long existCardId = editTalentPolicyDTO.getCardId();

        List<PolicyPO> pos = policyMapper.queryByDr((byte) 1);
        List<PolicyPO> showPOs = new ArrayList<>();
        for (PolicyPO po : pos) {
            String[] cardIds = null;
            String[] categories = null;
            String[] educations = null;
            String[] titles = null;
            String[] qualities = null;
            String[] honourIds = null;
            if (null != po.getCards()) {
                cardIds = po.getCards().split(",");
            }
            if (null != po.getCategories()) {
                categories = po.getCategories().split(",");
            }
            if (null != po.getEducations()) {
                educations = po.getEducations().split(",");
            }
            if (null != po.getTitles()) {
                titles = po.getTitles().split(",");
            }
            if (null != po.getQualities()) {
                qualities = po.getQualities().split(",");
            }
            if (null != po.getHonourIds()) {
                honourIds = po.getHonourIds().split(",");
            }

            boolean show = false;
            if (null != cardIds && null != existCardId) {
                for (String cardId : cardIds) {
                    if (!StringUtils.isEmpty(cardId) && existCardId.toString().equals(cardId)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != categories && null != existCategories) {
                for (String category : categories) {
                    if (!show) {
                        for (String existCategory : existCategories) {
                            if (!StringUtils.isEmpty(existCategory) && category.equals(existCategory)) {
                                show = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!show && null != educations && null != existEducations) {
                for (String educ : educations) {
                    if (!StringUtils.isEmpty(educ) && existEducations.toString().contains(educ)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != titles && null != existTitles) {
                for (String title : titles) {
                    if (!StringUtils.isEmpty(title) && existTitles.toString().contains(title)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != qualities && null != existQualities) {
                for (String quality : qualities) {
                    if (!StringUtils.isEmpty(quality) && existQualities.toString().contains(quality)) {
                        show = true;
                        break;
                    }
                }
            }

            if (!show && null != honourIds && null != existHonours) {
                for (String honourId : honourIds) {
                    if (!StringUtils.isEmpty(honourId) && existHonours.toString().contains(honourId)) {
                        show = true;
                        break;
                    }
                }
            }

            if (show) {
                showPOs.add(po);
            }
        }
        return showPOs;
    }

}
