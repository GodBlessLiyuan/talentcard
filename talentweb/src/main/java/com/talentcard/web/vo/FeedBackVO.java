package com.talentcard.web.vo;

import com.talentcard.common.bo.FeedbackBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 10:50
 * @description: 意见反馈
 * @version: 1.0
 */
@Data
public class FeedBackVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 反馈ID
     */
    private Long fid;
    /**
     * 名称
     */
    private String name;
    /**
     * 人才卡号
     */
    private String card;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 反馈时间
     */
    private Date ctime;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 图片
     */
    private String picture;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<FeedBackVO> convert(List<FeedbackBO> bos) {
        List<FeedBackVO> vos = new ArrayList<>();
        for (FeedbackBO bo : bos) {
            vos.add(FeedBackVO.convert(bo));
        }

        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static FeedBackVO convert(FeedbackBO bo) {
        FeedBackVO vo = new FeedBackVO();

        vo.setFid(bo.getFeedbackId());
        vo.setName(bo.getName());
        vo.setCard(bo.getCard());
        vo.setContact(bo.getContact());
        vo.setContent(bo.getContent());
        vo.setCtime(bo.getCreateTime());
        vo.setPicture(bo.getPicture());

        return vo;
    }
}
