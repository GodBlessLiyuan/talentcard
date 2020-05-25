package com.talentcard.web.dto;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.ScenicEnjoyPO;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.ScenicPicturePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:05
 * @description: 景区
 * @version: 1.0
 */
@Data
public class ScenicDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 景区ID
     */
    private Long scenicId;

    /**
     * 景区名称
     */
    private String name;

    /**
     * 入园限制 - 频
     */
    private Integer rate;

    /**
     * 入园限制 - 单位； 1：年；2：季；3：月
     */
    private Byte unit;

    /**
     * 入园限制 - 次
     */
    private Integer times;

    /**
     * 可查看与申请此景区的人才卡IDs
     */
    private Long[] cardIds;

    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private Long[] categoryIds;

    /**
     * 可查看与申请此景区的人才学历IDs
     */
    private Integer[] educIds;

    /**
     * 可查看与申请此景区的人才职称IDs
     */
    private Integer[] titleIds;

    /**
     * 可查看与申请此景区的人才职业资格IDs
     */
    private Integer[] qualityIds;

    /**
     * 景区头图
     */
    private String avatar;

    /**
     * 景区轮播图
     */
    private String[] picture;

    /**
     * 景区介绍信息
     */
    private String desc;

    /**
     * 备注
     */
    private String extra;

    /**
     * 可查看与申请此景区的人才荣誉IDs
     */
    private Long[] talentHonourIds;

    /**
     * 根据 dto 设置 po
     *
     * @param po
     * @param dto
     */
    public static ScenicPO buildPO(ScenicPO po, ScenicDTO dto) {
        po.setName(dto.getName());
        po.setRate(dto.getRate());
        po.setUnit(dto.getUnit());
        po.setTimes(dto.getTimes());
        if (null != dto.getAvatar()) {
            po.setAvatar(dto.getAvatar().split(FilePathConfig.getStaticPublicBasePath())[1]);
        }
        po.setDescription(dto.getDesc());
        po.setExtra(dto.getExtra());

        return po;
    }

    /**
     * 根据 dto 构建 enjoyPOs
     *
     * @param dto
     * @param scenicId
     * @return
     */
    public static List<ScenicEnjoyPO> buildEnjoyPOs(ScenicDTO dto, Long scenicId) {
        List<ScenicEnjoyPO> pos = new ArrayList<>();

        if (null != dto.getCardIds() && dto.getCardIds().length > 0) {
            for (Long cardId : dto.getCardIds()) {
                ScenicEnjoyPO po = new ScenicEnjoyPO();
                po.setScenicId(scenicId);
                po.setCardId(cardId);
                po.setType((byte) 1);
                pos.add(po);
            }
        }
        if (null != dto.getCategoryIds() && dto.getCategoryIds().length > 0) {
            for (Long category : dto.getCategoryIds()) {
                ScenicEnjoyPO po = new ScenicEnjoyPO();
                po.setScenicId(scenicId);
                po.setCategoryId(category);
                po.setType((byte) 2);
                pos.add(po);
            }
        }
        if (null != dto.getEducIds() && dto.getEducIds().length > 0) {
            for (Integer educId : dto.getEducIds()) {
                ScenicEnjoyPO po = new ScenicEnjoyPO();
                po.setScenicId(scenicId);
                po.setEducationId(educId);
                po.setType((byte) 3);
                pos.add(po);
            }
        }
        if (null != dto.getTitleIds() && dto.getTitleIds().length > 0) {
            for (Integer titleId : dto.getTitleIds()) {
                ScenicEnjoyPO po = new ScenicEnjoyPO();
                po.setScenicId(scenicId);
                po.setTitleId(titleId);
                po.setType((byte) 4);
                pos.add(po);
            }
        }
        if (null != dto.getQualityIds() && dto.getQualityIds().length > 0) {
            for (Integer qualityId : dto.getQualityIds()) {
                ScenicEnjoyPO po = new ScenicEnjoyPO();
                po.setScenicId(scenicId);
                po.setQuality(qualityId);
                po.setType((byte) 5);
                pos.add(po);
            }
        }
        if (null != dto.getTalentHonourIds() && dto.getTalentHonourIds().length > 0) {
            for (Long honourId : dto.getTalentHonourIds()) {
                ScenicEnjoyPO po = new ScenicEnjoyPO();
                po.setScenicId(scenicId);
                po.setHonourId(honourId);
                po.setType((byte) 6);
                pos.add(po);
            }
        }

        return pos;
    }

    /**
     * 根据 dto 构建 picturePOs
     *
     * @param dto
     * @param scenicId
     * @return
     */
    public static List<ScenicPicturePO> buildPicturePOs(ScenicDTO dto, Long scenicId) {
        List<ScenicPicturePO> pos = new ArrayList<>();
        if (null != dto.getPicture() && dto.getPicture().length > 0) {
            for (String picture : dto.getPicture()) {
                ScenicPicturePO po = new ScenicPicturePO();
                po.setScenicId(scenicId);
                po.setPicture(picture.split(FilePathConfig.getStaticPublicBasePath())[1]);
                pos.add(po);
            }
        }
        return pos;
    }
}
