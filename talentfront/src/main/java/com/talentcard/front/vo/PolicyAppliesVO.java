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

    /**
     * 权益申请ID
     */
    private Long paId;
    /**
     * 权益名称
     */
    private String name;
    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 申请状态
     */
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
        vo.setStatus(po.getStatus() == 1 ? "审核通过" : po.getStatus() == 2 ? "审核驳回" : po.getStatus() == 3 ? "审核中" : "无");
        return vo;
    }
}
