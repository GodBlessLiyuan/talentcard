package com.talentcard.web.vo;

import com.talentcard.common.pojo.PolicyPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:27
 * @description: 政策
 * @version: 1.0
 */
@Data
public class PolicyVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策权益ID
     */
    private Long pid;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 政策权益名称
     */
    private String name;
    /**
     * 政策权益编号
     */
    private String num;
    /**
     * 是否需要申请
     */
    private String apply;
    /**
     * 频次规则
     */
    private String frequency;
    /**
     * 备注信息
     */
    private String desc;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<PolicyVO> convert(List<PolicyPO> pos) {
        List<PolicyVO> vos = new ArrayList<>();
        for (PolicyPO po : pos) {
            vos.add(PolicyVO.convert(po));
        }
        return vos;
    }

    public static PolicyVO convert(PolicyPO po) {
        PolicyVO vo = new PolicyVO();

        vo.setPid(po.getPolicyId());
        vo.setCtime(po.getCreateTime());
        vo.setName(po.getName());
        vo.setNum(po.getNum());
        vo.setApply(po.getApply() == 1 ? "需要" : "不需要");
        String frequency = "无";
        if (null != po.getRate() && null != po.getUnit() && null != po.getTimes()) {
            if (po.getUnit() == 4) {
                frequency = "终身" + po.getTimes() + "次";
            } else {
                frequency = po.getRate() + (po.getUnit() == 1 ? "年/" : po.getUnit() == 2 ? "季/" : "月/") + po.getTimes() + "次";
            }
        }
        vo.setFrequency(frequency);
        vo.setDesc(po.getDescription());

        return vo;
    }
}
