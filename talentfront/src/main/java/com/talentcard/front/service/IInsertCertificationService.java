package com.talentcard.front.service;

import com.talentcard.common.dto.*;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 10:03
 * @description
 */
public interface IInsertCertificationService {
    /**
     * 新增/编辑学历
     *
     * @param educationDTO
     * @return
     */
    ResultVO addEducation(EducationDTO educationDTO);


    /**
     * 新增/编辑职称
     */
    ResultVO addProfQuality(ProfQualityDTO profQualityDTO);


    /**
     * 新增/编辑职业资格
     */
    ResultVO addProfTitle(ProfTitleDTO profTitleDTO);

    /**
     * 新增/编辑人才荣誉
     */
    ResultVO addTalentHonour(TalentHonourDTO talentHonourDTO);

    /**
     * 删除新增认证
     */
    ResultVO delete(Long insertCertId);


    /**
     * 编辑基本信息
     */
    ResultVO editBasicInfo(BasicInfoDTO basicInfoDTO);

    /**
     * 根据id查找单个基本信息的详情
     */
    ResultVO findOneDetail(String openId, Long insertCertId);

    /**
     * 编辑手机号
     */
    ResultVO editPhone(String openId,String phone);

    /**
     * 根据openId查找新增认证待审批数量
     * @param openId
     * @return
     */
    ResultVO findInsertCertificationTimes(String openId);

    ResultVO findResultByInsertCertId(Long insertCertId);
}
