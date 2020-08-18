package com.talentcard.web.vo;

import com.talentcard.common.bo.PolicyTypeBO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 18:44 2020/8/12
 * @ Description：政策类型前端交互
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Data
public class PolicyTypeVO implements Serializable {
    /**
     * 政策类型id
     */
    private Long ptid;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 政策类型名称
     */
    private String ptname;
    /**
     * 政策类型互斥id
     */
    private List<Long> eids;
    /**
     * 状态：1：上架  2：下架
     */
    private Byte status;
    /**
     * 描述
     */
    private String desc;
    /**
     * 互斥政策类型名称
     */
    private List<String> eptnames;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<PolicyTypeVO> convert(List<PolicyTypeBO> bos) {
        List<PolicyTypeVO> vos = new ArrayList<>();
        for (PolicyTypeBO bo : bos) {
            vos.add(PolicyTypeVO.convert(bo));
        }
        return vos;
    }

    public static PolicyTypeVO convert(PolicyTypeBO bo) {
        PolicyTypeVO vo = new PolicyTypeVO();
        vo.setPtid(bo.getPTid());
        vo.setUtime(bo.getUpdateTime());
        vo.setPtname(bo.getPTypeName());
        vo.setEids(bo.getExcludeIds());
        vo.setDesc(bo.getDescription());
        vo.setEptnames(bo.getExcludeNames());
        vo.setStatus(bo.getStatus());
        return vo;
    }
}
