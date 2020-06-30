package com.talentcard.front.controller;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IInsertCertificationService;
import com.talentcard.front.utils.VerificationCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:28
 * @description
 */
@RequestMapping("insertCertification")
@RestController
public class InsertCertificationController {
    @Autowired
    IInsertCertificationService iInsertCertificationService;

    /**
     * 学历
     *
     * @param educationDTO
     * @return
     */
    @RequestMapping("addEducation")
    public ResultVO addEducation(@RequestBody EducationDTO educationDTO) {
        return iInsertCertificationService.addEducation(educationDTO);
    }

    /**
     * 职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("addProfQuality")
    public ResultVO addProfQuality(@RequestBody ProfQualityDTO profQualityDTO) {
        return iInsertCertificationService.addProfQuality(profQualityDTO);
    }

    /**
     * 职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("addProfTitle")
    public ResultVO addProfTitle(@RequestBody ProfTitleDTO profTitleDTO) {
        return iInsertCertificationService.addProfTitle(profTitleDTO);
    }

    /**
     * 人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("addTalentHonour")
    public ResultVO addTalentHonour(@RequestBody TalentHonourDTO talentHonourDTO) {
        return iInsertCertificationService.addTalentHonour(talentHonourDTO);
    }

    /**
     * 删除
     *
     * @param insertCertId
     * @return
     */
    @RequestMapping("delete")
    public ResultVO delete(@RequestParam(value = "insertCertId") Long insertCertId) {
        return iInsertCertificationService.delete(insertCertId);
    }

    /**
     * 编辑基本信息
     *
     * @param basicInfoDTO
     * @return
     */
    @RequestMapping("editBasicInfo")
    public ResultVO editBasicInfo(@RequestBody BasicInfoDTO basicInfoDTO) {
        return iInsertCertificationService.editBasicInfo(basicInfoDTO);
    }

    /**
     * 编辑手机号
     *
     * @param openId
     * @param phone
     * @param verificationCode
     * @return
     */
    @RequestMapping("editPhone")
    public ResultVO editPhone(@RequestParam(value = "openId") String openId,
                              @RequestParam(value = "phone") String phone,
                              @RequestParam(value = "verificationCode") String verificationCode) {
        //判断验证码
        String correctVerificationCode = VerificationCodeUtil.getCode(phone);
        if (StringUtils.isEmpty(correctVerificationCode)) {
            //查不到验证码
            return new ResultVO(2302);
        }
        if (!correctVerificationCode.equals(verificationCode)) {
            //验证码错误
            return new ResultVO(2301);
        }
        return iInsertCertificationService.editPhone(openId, phone);
    }

    /**
     * 根据id查找单个基本信息的详情
     *
     * @param openId
     * @param insertCertId
     * @return
     */
    @RequestMapping("findOneDetail")
    public ResultVO findOneDetail(@RequestParam(value = "openId") String openId,
                                  @RequestParam(value = "insertCertId") Long insertCertId) {
        return iInsertCertificationService.findOneDetail(openId, insertCertId);
    }

    /**
     * 新增认证次数查询
     *
     * @param openId
     * @return
     */
    @RequestMapping("findCertificationTimes")
    public ResultVO findInsertCertificationTimes(@RequestParam(value = "openId") String openId) {
        return iInsertCertificationService.findInsertCertificationTimes(openId);
    }


    /**
     * 新增认证审批结果查询
     *
     * @param insertCertId
     * @return
     */
    @RequestMapping("findResultByInsertCertId")
    public ResultVO findResultByInsertCertId(@RequestParam(value = "insertCertId") Long insertCertId) {
        return iInsertCertificationService.findResultByInsertCertId(insertCertId);
    }
}
