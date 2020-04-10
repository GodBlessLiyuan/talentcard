package com.talentcard.front.vo;

import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 18:45
 * @description: 我的申请
 * @version: 1.0
 */
@Data
public class PolicyAppliesVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long paId;
    private String name;
    private String applyDate;
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
        vo.setApplyDate(DateUtil.date2Str(po.getCreateTime(), DateUtil.YMD));
        vo.setName(po.getPolicyName());
        vo.setStatus(po.getStatus() == 1 ? "审核通过" : po.getStatus() == 2 ? "审核中" : po.getStatus() == 3 ? "审核驳回" : "无");
        return vo;
    }
}
