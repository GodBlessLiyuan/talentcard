package com.talentcard.common.bo;

import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.pojo.TalentPO;
import lombok.Data;

import java.util.List;

@Data
public class TalentBO extends TalentPO {
    private Integer educ;
    private Integer title;
    private Integer quality;
    private String cnum;
    private String cname;

    private Long certId;
    private String political;
    private Byte certificationStatus;
    private Byte currentType;
    List<EducationPO> educationPOList;
    List<ProfTitlePO> profTitlePOList;
    List<ProfQualityPO> profQualityPOList;
}
