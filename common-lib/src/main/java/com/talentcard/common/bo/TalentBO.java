package com.talentcard.common.bo;

import com.talentcard.common.pojo.*;
import lombok.Data;

import java.util.List;

@Data
public class TalentBO extends TalentPO {
    /**
     * 学历
     */
    private Integer educ;
    /**
     * 职称
     */
    private Integer title;
    /**
     * 职业资格
     */
    private Integer quality;
    /**
     * 卡片编号
     */
    private String cnum;
    /**
     * 卡片名称
     */
    private String cname;

    /**
     * 学校
     */
    private String school;
    /**
     * 学校为双一流（原985/211）
     */
    private Byte first;
    /**
     * 专业
     */
    private String major;
    /**
     * 职称信息
     */
    private String ptInfo;
    /**
     * 职业资格信息
     */
    private String pqInfo;
    /**
     * 人才类别
     */
    private String category;
    /**
     *
     */
    private Byte cstatus;
    /**
     * 人才荣誉
     */
    private Long honour;
    /**
     * 政治面貌
     */
    private Byte political;
    /**
     * 证件类型
     */
    private Byte cardType;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 护照号
     */
    private String passport;
    /**
     * 驾照号
     */
    private String driverCard;

    private Long certId;
    private Byte certificationStatus;
    private Byte currentType;
    private List<EducationPO> educationPOList;
    private List<ProfTitlePO> profTitlePOList;
    private List<ProfQualityPO> profQualityPOList;
    private List<TalentHonourPO> talentHonourPOList;
}
