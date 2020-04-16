package com.talentcard.common.bo;

import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.pojo.TalentPO;
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

    private Long certId;
    private String political;
    private Byte certificationStatus;
    private Byte currentType;
    List<EducationPO> educationPOList;
    List<ProfTitlePO> profTitlePOList;
    List<ProfQualityPO> profQualityPOList;
}
