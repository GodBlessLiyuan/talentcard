package com.talentcard.common.bo;

import com.talentcard.common.pojo.TalentCertificationInfoPO;
import lombok.Data;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 10:21 2020/8/27
 * @ Description：导出认证人才信息，和社保局相关
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class ExportCertInfoBO extends TalentCertificationInfoPO {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 护照号
     */
    private String passport;
    /**
     * 驾照号码
     */
    private String driverCard;
    /**
     * 驾证件类型
     */
    private Byte cardType;
    /**
     * 现工作单位
     */
    private String workUnit;
}
