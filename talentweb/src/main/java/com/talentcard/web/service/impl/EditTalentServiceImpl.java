package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EditTalentPolicyDTO;
import com.talentcard.web.service.IEditTalentService;
import com.talentcard.web.service.ITalentInfoCertificationService;
import com.talentcard.web.service.ITalentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        HashMap<String, Object> hashMap = new HashMap(2);
        hashMap.put("openId", openId);
        hashMap.put("status", (byte) 1);
        TalentBO talentBO = talentMapper.findOne(hashMap);
        if (talentBO == null) {
            return new ResultVO(2500);
        }
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
        return new ResultVO(1000, result);
    }

    /**
     * 政策查询工具类
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
