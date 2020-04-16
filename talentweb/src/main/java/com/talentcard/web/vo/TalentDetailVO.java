package com.talentcard.web.vo;

import com.talentcard.common.bo.TalentBO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:37
 * @description: 人才详情
 * @version: 1.0
 */
@Data
public class TalentDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Byte sex;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 卡片编号
     */
    private String cnum;
    /**
     * 卡片名称
     */
    private String cname;
    /**
     * 学历
     */
    private Integer educ;
    /**
     * 学校
     */
    private String school;
    /**
     * 学校为双一流（原985/211） 1：是；2：否
     */
    private Byte first;
    /**
     * 专业
     */
    private String major;
    /**
     * 职称
     */
    private Integer title;
    /**
     * 职称信息
     */
    private String ptInfo;
    /**
     * 职业资格
     */
    private Integer quality;
    /**
     * 职业资格信息
     */
    private String pqInfo;
    /**
     * 所在行业
     */
    private String industry;
    /**
     * 现工作单位
     */
    private String unit;
    /**
     * 注册时间
     */
    private Date ctime;

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static TalentDetailVO convert(TalentBO bo) {
        TalentDetailVO vo = new TalentDetailVO();

        vo.setName(bo.getName());
        vo.setSex(bo.getSex());
        vo.setIdCard(bo.getIdCard());
        vo.setPhone(bo.getPhone());
        vo.setCname(bo.getCname());
        vo.setCnum(bo.getCnum());
        vo.setEduc(bo.getEduc());
        vo.setSchool(bo.getSchool());
        vo.setFirst(bo.getFirst());
        vo.setMajor(bo.getMajor());
        vo.setTitle(bo.getTitle());
        vo.setPtInfo(bo.getPtInfo());
        vo.setQuality(bo.getQuality());
        vo.setPqInfo(bo.getPqInfo());
        vo.setIndustry(bo.getIndustry());
        vo.setUnit(bo.getWorkUnit());
        vo.setCtime(bo.getCreateTime());

        return vo;
    }
}
