package com.talentcard.web.vo;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.FarmhouseEnjoyPO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 19:06
 * @description: TODO
 * @version: 1.0
 */
@Component
@Data
public class FarmhouseDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 农家乐ID
     */
    private Long farmhouseId;

    /**
     * 农家乐名称
     */
    private String name;

    /**
     * 优惠折扣
     */
    private Double discount;

    /**
     * 可查看与申请此农家乐的人才卡IDs
     */
    private List<Long> cardIds;

    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private List<Long> categoryIds;

    /**
     * 可查看与申请此农家乐的人才学历IDs
     */
    private List<Integer> educIds;

    /**
     * 可查看与申请此农家乐的人才职称IDs
     */
    private List<Integer> titleIds;

    /**
     * 可查看与申请此农家乐的人才职业资格IDs
     */
    private List<Integer> qualityIds;

    /**
     * 农家乐头图
     */
    private String avatar;

    /**
     * 农家乐轮播图
     */
    private List<String> picture;

    /**
     * 农家乐介绍信息
     */
    private String desc;

    /**
     * 备注
     */
    private String extra;

    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 可查看与申请此政策权益的人才荣誉IDs
     */
    private List<Long> talentHonourIds;

    /**
     * 构建vo
     *
     * @param farmhousePO
     * @param enjoyPOs
     * @param picPOs
     * @return
     */
    public static FarmhouseDetailVO build(FarmhousePO farmhousePO, List<FarmhouseEnjoyPO> enjoyPOs, List<FarmhousePicturePO> picPOs) {
        FarmhouseDetailVO vo = new FarmhouseDetailVO();
        vo.setFarmhouseId(farmhousePO.getFarmhouseId());
        vo.setName(farmhousePO.getName());
        vo.setDiscount(farmhousePO.getDiscount());
        if (null != farmhousePO.getAvatar()) {
            vo.setAvatar(FilePathConfig.getStaticPublicBasePath() + farmhousePO.getAvatar());
        }
        vo.setDesc(farmhousePO.getDescription());
        vo.setExtra(farmhousePO.getExtra());
        if (null != farmhousePO.getQrCode()) {
            vo.setQrCode(FilePathConfig.getStaticPublicBasePath() + farmhousePO.getQrCode());
        }

        List<Long> cardIds = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        List<Integer> educIds = new ArrayList<>();
        List<Integer> titleIds = new ArrayList<>();
        List<Integer> qualityIds = new ArrayList<>();
        List<Long> talentHonourIds = new ArrayList<>();
        for (FarmhouseEnjoyPO po : enjoyPOs) {
            Byte type = po.getType();
            if (type == 1) {
                cardIds.add(po.getCardId());
            } else if (type == 2) {
                categoryIds.add(po.getCategoryId());
            } else if (type == 3) {
                educIds.add(po.getEducationId());
            } else if (type == 4) {
                titleIds.add(po.getTitleId());
            } else if (type == 5) {
                qualityIds.add(po.getQuality());
            } else if (type == 6) {
                talentHonourIds.add(po.getHonourId());
            }
        }

        vo.setCardIds(cardIds);
        vo.setCategoryIds(categoryIds);
        vo.setEducIds(educIds);
        vo.setTitleIds(titleIds);
        vo.setQualityIds(qualityIds);
        vo.setTalentHonourIds(talentHonourIds);

        List<String> picture = new ArrayList<>();
        for (FarmhousePicturePO po : picPOs) {
            picture.add(FilePathConfig.getStaticPublicBasePath() + po.getPicture());
        }
        vo.setPicture(picture);

        return vo;
    }
}

