package com.talentcard.front.vo;

import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.InsertEducationPO;
import com.talentcard.common.pojo.InsertHonourPO;
import com.talentcard.common.pojo.InsertQualityPO;
import com.talentcard.common.pojo.InsertTitlePO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-24 10:02
 * @description
 */
@Component
@Data
public class InsertCertificationVO {
    private Long insertCertId;
    private Long talentId;
    private Byte type;
    private Date createTime;
    private Byte status;
    private InsertEducationPO insertEducationPO;
    private InsertQualityPO insertQualityPO;
    private InsertTitlePO insertTitlePO;
    private InsertHonourPO insertHonourPO;

    private Integer currentCertificationTimes;

    public static InsertCertificationVO convert(InsertCertificationBO insertCertificationBO) {
        InsertCertificationVO insertCertificationVO = new InsertCertificationVO();
        insertCertificationVO.setInsertCertId(insertCertificationBO.getInsertCertId());
        insertCertificationVO.setTalentId(insertCertificationBO.getTalentId());
        insertCertificationVO.setType(insertCertificationBO.getType());
        insertCertificationVO.setCreateTime(insertCertificationBO.getCreateTime());
        insertCertificationVO.setStatus(insertCertificationBO.getStatus());

//        if (insertCertificationBO.getType() == 1) {
//            //学历
//            if (insertCertificationBO.getInsertEducationPO() != null) {
//                String picture = insertCertificationBO.getInsertEducationPO().getEducPicture();
//                if (!StringUtils.isEmpty(picture)) {
//                    insertCertificationBO.getInsertEducationPO().
//                            setEducPicture(FilePathConfig.getStaticPublicBasePath() + picture);
//                }
//                String picture2 = insertCertificationBO.getInsertEducationPO().getEducPicture();
//                if (!StringUtils.isEmpty(picture2)) {
//                    insertCertificationBO.getInsertEducationPO().
//                            setEducPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
//                }
//                String picture3 = insertCertificationBO.getInsertEducationPO().getEducPicture3();
//                if (!StringUtils.isEmpty(picture3)) {
//                    insertCertificationBO.getInsertEducationPO().
//                            setEducPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
//                }
//            }
//        } else if (insertCertificationBO.getType() == 2) {
//            //职称
//            if (insertCertificationBO.getInsertTitlePO() != null) {
//                String picture = insertCertificationBO.getInsertTitlePO().getPicture();
//                if (!StringUtils.isEmpty(picture)) {
//                    insertCertificationBO.getInsertTitlePO().
//                            setPicture(FilePathConfig.getStaticPublicBasePath() + picture);
//                }
//                String picture2 = insertCertificationBO.getInsertTitlePO().getPicture2();
//                if (!StringUtils.isEmpty(picture2)) {
//                    insertCertificationBO.getInsertTitlePO().
//                            setPicture3(FilePathConfig.getStaticPublicBasePath() + picture2);
//                }
//                String picture3 = insertCertificationBO.getInsertTitlePO().getPicture3();
//                if (!StringUtils.isEmpty(picture3)) {
//                    insertCertificationBO.getInsertTitlePO().
//                            setPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
//                }
//            }
//        } else if (insertCertificationBO.getType() == 3) {
//            //职业资格
//            if (insertCertificationBO.getInsertQualityPO() != null) {
//                String picture = insertCertificationBO.getInsertQualityPO().getPicture();
//                if (!StringUtils.isEmpty(picture)) {
//                    insertCertificationBO.getInsertQualityPO().
//                            setPicture(FilePathConfig.getStaticPublicBasePath() + picture);
//                }
//                String picture2 = insertCertificationBO.getInsertQualityPO().getPicture2();
//                if (!StringUtils.isEmpty(picture2)) {
//                    insertCertificationBO.getInsertQualityPO().
//                            setPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
//                }
//                String picture3 = insertCertificationBO.getInsertQualityPO().getPicture3();
//                if (!StringUtils.isEmpty(picture3)) {
//                    insertCertificationBO.getInsertQualityPO().
//                            setPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
//                }
//            }
//        } else {
//            if (insertCertificationBO.getInsertHonourPO() != null) {
//                //人才荣誉
//                String picture = insertCertificationBO.getInsertHonourPO().getHonourPicture();
//                if (!StringUtils.isEmpty(picture)) {
//                    insertCertificationBO.getInsertHonourPO().
//                            setHonourPicture(FilePathConfig.getStaticPublicBasePath() + picture);
//                }
//                String picture2 = insertCertificationBO.getInsertHonourPO().getHonourPicture2();
//                if (!StringUtils.isEmpty(picture2)) {
//                    insertCertificationBO.getInsertHonourPO().
//                            setHonourPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
//                }
//                String picture3 = insertCertificationBO.getInsertHonourPO().getHonourPicture3();
//                if (!StringUtils.isEmpty(picture3)) {
//                    insertCertificationBO.getInsertHonourPO().
//                            setHonourPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
//                }
//            }
//
//        }
        insertCertificationVO.setInsertEducationPO(insertCertificationBO.getInsertEducationPO());
        insertCertificationVO.setInsertQualityPO(insertCertificationBO.getInsertQualityPO());
        insertCertificationVO.setInsertTitlePO(insertCertificationBO.getInsertTitlePO());
        insertCertificationVO.setInsertHonourPO(insertCertificationBO.getInsertHonourPO());
        return insertCertificationVO;
    }
}
