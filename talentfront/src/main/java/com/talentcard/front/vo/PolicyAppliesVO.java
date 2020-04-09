package com.talentcard.front.vo;

import com.talentcard.common.pojo.PolicyApplyPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 18:45
 * @description: TODO
 * @version: 1.0
 */
@Data
public class PolicyAppliesVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long paId;
    private String name;
    private Date applyTime;
    private String status;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<PolicyAppliesVO> convert(List<PolicyApplyPO> pos) {
        List<PolicyAppliesVO> vos = new ArrayList<>();
        for (PolicyApplyPO po : pos) {
            vos.add(PolicyAppliesVO.convert(po));
        }
        return vos;
    }

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static PolicyAppliesVO convert(PolicyApplyPO po) {
        PolicyAppliesVO vo = new PolicyAppliesVO();
        vo.setPaId(po.getPaId());
        vo.setApplyTime(po.getCreateTime());
        vo.setName(po.getPolicyName());
        vo.setStatus(po.getStatus() == 1 ? "审核通过" : po.getStatus() == 2 ? "审核中" : po.getStatus() == 3 ? "审核驳回" : "无");
        return vo;
    }
}
