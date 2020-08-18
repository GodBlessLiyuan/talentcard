package com.talentcard.web.vo;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.PolicyPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:38
 * @description: 政策-详细信息
 * @version: 1.0
 */
@Slf4j
@Data
public class PolicyDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策权益ID
     */
    private Long pid;
    /**
     * 政策权益名称
     */
    private String name;
    /**
     * 政策权益编号
     */
    private String num;
    /**
     * 政策详细描述
     */
    private String desc;
    /**
     * 可查看与申请此政策权益的人才卡IDs
     */
    private String[] cardIds;
    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private String[] categoryIds;
    /**
     * 可查看与申请此政策权益的人才学历IDs
     */
    private String[] educIds;
    /**
     * 可查看与申请此政策权益的人才职称IDs
     */
    private String[] titleIds;
    /**
     * 可查看与申请此政策权益的人才职业资格IDs
     */
    private String[] qualityIds;
    /**
     * 可查看与申请此政策权益的人才荣誉IDs
     */
    private Long[] talentHonourIds;
    /**
     * 颜色，当apply是1的时候，这里必须给
     */
    private String color;
    /**
     * 政策权益是否需要申请: 1：需要；2：不需要
     */
    private Byte apply;
    /**
     * 政策权益申请频次 - 频
     */
    private Integer rate;
    /**
     * 政策权益申请频次 - 单位； 1：年；2：季；3：月
     */
    private Byte unit;
    /**
     * 政策权益申请频次 - 次
     */
    private Integer times;
    /**
     * 银行卡信息；1：需要；2：不需要
     */
    private Byte bank;
    /**
     * 附件信息；1：必填；2：不填；3：选填
     */
    private Byte annex;
    /**
     * 附件信息描述
     */
    private String info;
    /**
     * 申请表单
     */
    private String form;
    /**
     * 政策资金
     */
    private Integer funds;

    /**
     * 政策类型
     */
    private Long policyType;
    /**
     * 责任单位/角色id
     */
    private Long roleId;
    /**
     * 是否社保要求
     */
    private Byte ifSocialSecurity;
    /**
     * 地区
     */
    private Byte socialArea;
    /**
     * 社保时间
     */
    private Byte socialTimes;
    /**
     * 社保频次
     */
    private Byte socialUnit;
    /**
     * 资金发放形式
     */
    private Byte fundsForm;
    /**
     * 申报对象
     */
    private String declarationTarget;
    /**
     * 申请开始时间
     */
    private Date startTime;
    /**
     * 申请结束时间
     */
    private Date endTime;
    /**
     * 申请资料
     */
    private String applyMaterials;
    /**
     * 奖励/补助额度
     */
    private String bonus;
    /**
     * 办事流程
     */
    private String businessProcess;
    /**
     * 咨询电话
     */
    private String phone;
    /**
     * 状态
     * 1、可申请
     * 2、未开启
     * 3、已失效
     * 4、已下架
     */
    private Byte status;

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static PolicyDetailVO convert(PolicyPO po) {
        PolicyDetailVO vo = new PolicyDetailVO();
        vo.setPid(po.getPolicyId());
        vo.setName(po.getName());
        vo.setNum(po.getNum());
        vo.setDesc(po.getDescription());
        vo.setCardIds(po.getCards().split(","));
        vo.setCategoryIds(po.getCategories().split(","));
        vo.setEducIds(po.getEducations().split(","));
        vo.setTitleIds(po.getTitles().split(","));
        vo.setQualityIds(po.getQualities().split(","));
        String tempStr = po.getHonourIds();
        vo.setTalentHonourIds(stringToLongList(tempStr, ","));
        vo.setColor(po.getColor());
        vo.setApply(po.getApply());
        vo.setRate(po.getRate());
        vo.setUnit(po.getUnit());
        vo.setTimes(po.getTimes());
        vo.setBank(po.getBank());
        vo.setAnnex(po.getAnnex());
        vo.setInfo(po.getAnnexInfo());
        if (!StringUtils.isEmpty(po.getApplyForm())) {
            vo.setForm(FilePathConfig.getStaticPublicBasePath() + po.getApplyForm());
        }
        vo.setFunds(po.getFunds());

        vo.setPolicyType(po.getPTid());
        vo.setRoleId(po.getRoleId());
        vo.setIfSocialSecurity(po.getIfSocialSecurity());
        vo.setSocialArea(po.getSocialArea());
        vo.setSocialTimes(po.getSocialTimes());
        vo.setSocialUnit(po.getSocialUnit());
        vo.setFundsForm(po.getFundsForm());
        vo.setDeclarationTarget(po.getDeclarationTarget());
        vo.setStartTime(po.getStartTime());
        vo.setEndTime(po.getEndTime());
        vo.setApplyMaterials(po.getApplyMaterials());
        vo.setBonus(po.getBonus());
        vo.setBusinessProcess(po.getBusinessProcess());
        vo.setPhone(po.getPhone());
        //判断政策状态
        if (po.getUpDown() == 2) {
            //已下架
            vo.setStatus((byte) 4);
        } else {
            Long start = po.getStartTime().getTime();
            Long end = po.getEndTime().getTime();
            Long current = System.currentTimeMillis();
            if (current >= end) {
                //已失效
                vo.setStatus((byte) 3);
            } else if (current <= start) {
                //未开启
                vo.setStatus((byte) 2);
            } else if (current >= start && current <= end) {
                //可申请
                vo.setStatus((byte) 1);
            }
        }
        return vo;
    }

    private static Long[] stringToLongList(String target, String sign) {
        if (target == null) {
            return null;
        }
        String[] temp = target.split(sign);
        int le = temp.length;
        Long[] longs = new Long[le];
        for (int i = 0; i < le; i++) {
            try {
                longs[i] = Long.parseLong(temp[i]);
            } catch (Exception e) {
                log.info("com.talentcard.web.vo.PolicyDetailVO人才荣誉Id错误:[{}]", target);
                return null;
            }
        }
        return longs;
    }
}
