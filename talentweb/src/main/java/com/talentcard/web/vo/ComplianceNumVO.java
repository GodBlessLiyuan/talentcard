package com.talentcard.web.vo;

import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.pojo.PoStatisticsPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 9:52 2020/8/21
 * @ Description：符合条件人数与前端交互
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Data
public class ComplianceNumVO implements Serializable {

    /**
     * 人才政策id
     */

    private Long pid;

    /**
     * 符合条件总人数
     */

    private Long total;

    /**
     * 待审批人数
     */

    private Long napproval;

    /**
     * 未申请人数
     */

    private Long napply;

    /**
     * 已通过人数
     */

    private Long pass;

    /**
     * 已驳回人数
     */

    private Long reject;

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */



    public static ComplianceNumVO convert(PoStatisticsPO po) {
        ComplianceNumVO vo = new ComplianceNumVO();
        vo.setPid(po.getPolicyId());
        vo.setTotal(po.getTotal());
        vo.setNapply(po.getNotApply());
        vo.setNapproval(po.getNotApproval());
        vo.setPass(po.getPass());
        vo.setReject(po.getReject());
        return vo;
    }
}
