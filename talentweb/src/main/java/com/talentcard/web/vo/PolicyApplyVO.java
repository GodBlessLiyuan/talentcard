package com.talentcard.web.vo;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/15 10:31
 * @description: 政策审批
 * @version: 1.0
 */
@Data
public class PolicyApplyVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策申请ID
     */
    private Long paid;
    /**
     * 申请时间
     */
    private String ctime;
    /**
     * 政策权益编号
     */
    private String num;
    /**
     * 政策名称
     */
    private String name;
    /**
     * 申请人
     */
    private String apply;
    /**
     * 状态
     */
    private String status;

    /**
     * 责任单位名称
     */
    private String roleName;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<PolicyApplyVO> convert(List<PolicyApplyBO> bos) {
        List<PolicyApplyVO> vos = new ArrayList<>();
        for (PolicyApplyBO bo : bos) {
            vos.add(PolicyApplyVO.convert(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static PolicyApplyVO convert(PolicyApplyBO bo) {
        PolicyApplyVO vo = new PolicyApplyVO();

        vo.setPaid(bo.getPaId());
        vo.setCtime(DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD_HMS));
        vo.setNum(bo.getNum());
        vo.setName(bo.getPolicyName());
        vo.setApply(bo.getTalentName());
        vo.setStatus(bo.getStatus() == 1 ? "已通过" : bo.getStatus() == 2 ? "已驳回" : "待审批");
        vo.setRoleName(bo.getRoleName());
        return vo;
    }
}
