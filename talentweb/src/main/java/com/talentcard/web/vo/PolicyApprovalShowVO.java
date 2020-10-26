package com.talentcard.web.vo;

import com.talentcard.common.bo.PolicyApprovalBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 11:02 2020/10/23
 * @ Description：政策审批首页数据展示
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class PolicyApprovalShowVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private static final byte UP = 1;
    private static final byte DOWN = 2;
    /**
     * 政策id
     */
    private Long pid;
    /**
     * 政策编号
     */
    private String pname;
    /**
     * 政策编号
     */
    private String num;
    /**
     * 符合条件人数
     */
    private Integer total;
    /**
     * 待审批人数
     */
    private Integer napprovalnum;
    /**
     * 未申请人数
     */
    private Integer napplynum;
    /**
     * 已通过人数
     */
    private Integer pnum;
    /**
     * 已驳回人数
     */
    private Integer rnum;
    /**
     * 状态
     */
    private String status;
    /**
     * 政策类型名称
     */
    private String ptname;
    /**
     * 责任单位名称
     */
    private String runit;


    /**
     * pos 转 vos
     *
     * @param bos
     * @return
     */

    public static List<PolicyApprovalShowVO> convert(List<PolicyApprovalBO> bos) {
        List<PolicyApprovalShowVO> vos = new ArrayList<>();
        for (PolicyApprovalBO bo : bos) {
            vos.add(PolicyApprovalShowVO.convert(bo));
        }
        return vos;
    }

    public static PolicyApprovalShowVO convert(PolicyApprovalBO bo) {
        PolicyApprovalShowVO vo = new PolicyApprovalShowVO();
        vo.setPid(bo.getPolicyId());
        vo.setPname(bo.getPolicyName());
        vo.setNum(bo.getNum());
        vo.setTotal(bo.getTotal());
        vo.setNapprovalnum(bo.getNotApprovalNum());
        vo.setNapplynum(bo.getNotApplyNum());
        vo.setPnum(bo.getPassNum());
        vo.setRnum(bo.getRejectNum());
        vo.setPtname(bo.getPolicyTypeName());
        Date nowDate = new Date();
        if (nowDate.compareTo(bo.getStartTime()) >= 0 && nowDate.compareTo(bo.getEndTime()) < 0 && bo.getUpDown() == UP) {
            vo.setStatus("可申请");
        }
        if (nowDate.compareTo(bo.getStartTime()) < 0 && bo.getUpDown() == UP) {
            vo.setStatus("未开启");
        }
        if (nowDate.compareTo(bo.getEndTime()) >= 0 && bo.getUpDown() == UP) {
            vo.setStatus("已失效");
        }
        if (bo.getUpDown() == DOWN) {
            vo.setStatus("已下架");
        }
        vo.setRunit(bo.getResponsibleUnit());
        return vo;
    }


}
