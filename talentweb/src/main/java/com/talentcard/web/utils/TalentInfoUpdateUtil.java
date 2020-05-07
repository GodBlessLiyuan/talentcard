package com.talentcard.web.utils;

import com.talentcard.common.mapper.CertificationMapper;
import com.talentcard.common.mapper.EducationMapper;
import com.talentcard.common.mapper.ProfQualityMapper;
import com.talentcard.common.mapper.ProfTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-07 09:56
 * @description
 */
@Component
public class TalentInfoUpdateUtil {
    @Autowired
    private CertificationMapper cMapper;
    @Autowired
    private EducationMapper eMapper;
    @Autowired
    private ProfTitleMapper ptMapper;
    @Autowired
    private ProfQualityMapper pqMapper;
    private static CertificationMapper certificationMapper;
    private static EducationMapper educationMapper;
    private static ProfTitleMapper profTitleMapper;
    private static ProfQualityMapper profQualityMapper;


    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void mapperInitialize() {
        certificationMapper = cMapper;
        educationMapper = eMapper;
        profTitleMapper = ptMapper;
        profQualityMapper = pqMapper;
    }

    /**
     * 根据certId，更新4表对应状态
     * @param certId
     * @param status
     */
    public static void fourTableUpdate(Long certId, Byte status) {
        certificationMapper.updateStatusByCertId(certId, status);
        educationMapper.updateStatusByCertId(certId, status);
        profTitleMapper.updateStatusByCertId(certId, status);
        profQualityMapper.updateStatusByCertId(certId, status);
    }
}
