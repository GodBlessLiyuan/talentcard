package com.talentcard.web.vo;

import com.talentcard.common.pojo.PolicyPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

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
     * 附件信息；1：需要；2：不需要
     */
    private Byte annex;

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

        return vo;
    }

    private static Long[] stringToLongList(String target, String sign){
        if (target == null) {return null;}
        String[] temp = target.split(sign);
        int le = temp.length;
        Long[] longs = new Long[le];
        for (int i = 0; i < le; i ++) {
            try{
                longs[i] = Long.parseLong(temp[i]);
            }catch(Exception e){
                log.info("com.talentcard.web.vo.PolicyDetailVO人才荣誉Id错误:[{}]", target);
                return null;
            }
        }
        return longs;
    }
}
