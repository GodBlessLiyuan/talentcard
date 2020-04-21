package com.talentcard.web.vo;

import com.talentcard.common.bo.TalentBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:35
 * @description: 普通用户
 * @version: 1.0
 */
@Data
public class TalentVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 人才ID
     */
    private Long tid;
    /**
     * 注册时间
     */
    private Date ctime;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Byte sex;
    /**
     * 学历
     */
    private Integer educ;
    /**
     * 职称
     */
    private Integer title;
    /**
     * 职业资格
     */
    private Integer quality;
    /**
     * 人才卡编号
     */
    private String cnum;
    /**
     * 人才卡名称
     */
    private String cname;
    /**
     * 人才类别
     */
    private String category;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<TalentVO> convert(List<TalentBO> bos) {
        List<TalentVO> vos = new ArrayList<>();

        for (TalentBO bo : bos) {
            vos.add(TalentVO.convert(bo));
        }

        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static TalentVO convert(TalentBO bo) {
        TalentVO vo = new TalentVO();

        vo.setTid(bo.getTalentId());
        vo.setCtime(bo.getCreateTime());
        vo.setName(bo.getName());
        vo.setSex(bo.getSex());
        vo.setEduc(bo.getEduc());
        vo.setTitle(bo.getTitle());
        vo.setQuality(bo.getQuality());
        vo.setCname(bo.getCname());
        vo.setCnum(bo.getCnum());

        return vo;
    }
}
