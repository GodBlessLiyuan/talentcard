package com.talentcard.common.bo;

import com.talentcard.common.pojo.PoCompliancePO;
import lombok.Data;

import java.util.Date;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 18:51 2020/8/19
 * @ Description：查询符合政策人才数业务类
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class PoComplianceBO extends PoCompliancePO {
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Byte sex;
    /**
     * 现工作单位
     */
    private String workUnit;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 政策名称
     */
    private String policyName;
    /**
     * 政策编号
     */
    private String policyNum;
    /**
     * 政策资金
     */
    private Integer policyFunds;
    /**
     * 卡号
     */
    private String bankNum;
    /**
     * 开户行名
     */
    private String bankName;
    /**
     * openid
     */
    private String openId;
    /**
     * bankid
     */
    private String bankId;
    /**
     * 政策申请id
     */
    private Long paId;
    /**
     * 责任单位名称
     */
    private String responsibleUnit;
    /**
     * 角色类型
     */
    private Byte roleType;
    /**
     * 角色id
     */
    private Long roleId;
}
