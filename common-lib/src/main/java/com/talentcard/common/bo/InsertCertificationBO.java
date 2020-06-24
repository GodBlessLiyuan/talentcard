package com.talentcard.common.bo;

import com.talentcard.common.pojo.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-23 18:11
 * @description
 */
@Data
public class InsertCertificationBO extends InsertCertificationPO {
    private InsertEducationPO insertEducationPO;
    private InsertQualityPO insertQualityPO;
    private InsertTitlePO insertTitlePO;
    private InsertHonourPO insertHonourPO;
    private String name;
    private Byte sex;
}
