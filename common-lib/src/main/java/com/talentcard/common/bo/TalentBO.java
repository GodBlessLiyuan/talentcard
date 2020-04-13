package com.talentcard.common.bo;

import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.pojo.TalentPO;
import lombok.Data;

import java.util.List;

@Data
public class TalentBO extends TalentPO {
    private Long certId;
    private String political;
    private Byte certificationStatus;
    private Byte currentType;
    List<EducationPO> educationPOList;
    List<ProfTitlePO> profTitlePOList;
    List<ProfQualityPO> profQualityPOList;
}
