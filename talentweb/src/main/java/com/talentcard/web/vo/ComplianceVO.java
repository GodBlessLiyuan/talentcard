
package com.talentcard.web.vo;

import com.talentcard.common.bo.PoComplianceBO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:47 2020/8/20
 * @ Description：人数详情查询前端展示页
 * @ Modified By：
 * @ Version:     1.0
 */

@Slf4j
@Data
public class ComplianceVO implements Serializable {

    /**
     * 人才政策id
     */

    private Long pid;

    /**
     * 人才id
     */

    private Long tid;

    /**
     * 申请时间
     */

    private Date atime;

    /**
     * 申请状态：0：未申请；1：已同意；2：已驳回；3：待审批
     */

    private Byte status;

    /**
     * 姓名
     */

    private String name;

    /**
     * 姓别
     */

    private Byte sex;

    /**
     * 工作单位
     */

    private String wunit;

    /**
     * 手机号码
     */

    private String phone;

    /**
     * 政策名称
     */


    private String pname;

    /**
     * 政策编号num
     */


    private String num;


    /**
     * 政策资金
     */


    private Integer funds;
    /**
     * 政策资金
     */
    private String openid;
    /**
     * 政策申请id
     */
    private Long paid;
    /**
     * 责任单位名称
     */
    private String runit;
    /**
     * 角色类型
     */
    private Byte rtype;
    /**
     * 角色id
     */
    private Long rid;



    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */

    public static List<ComplianceVO> convert(List<PoComplianceBO> bos) {
        List<ComplianceVO> vos = new ArrayList<>();
        for (PoComplianceBO bo : bos) {
            vos.add(ComplianceVO.convert(bo));
        }
        return vos;
    }

    public static ComplianceVO convert(PoComplianceBO bo) {
        ComplianceVO vo = new ComplianceVO();
        vo.setPid(bo.getPolicyId());
        vo.setPname(bo.getPolicyName());
        vo.setAtime(bo.getApplyTime());
        vo.setFunds(bo.getPolicyFunds());
        vo.setName(bo.getName());
        vo.setStatus(bo.getStatus());
        vo.setNum(bo.getPolicyNum());
        vo.setPhone(bo.getPhone());
        vo.setSex(bo.getSex());
        vo.setTid(bo.getTalentId());
        vo.setWunit(bo.getWorkUnit());
        vo.setOpenid(bo.getOpenId());
        vo.setPaid(bo.getPaId());
        vo.setRunit(bo.getResponsibleUnit());
        vo.setRtype(bo.getRoleType());
        vo.setRid(bo.getRoleId());
        return vo;
    }
}
