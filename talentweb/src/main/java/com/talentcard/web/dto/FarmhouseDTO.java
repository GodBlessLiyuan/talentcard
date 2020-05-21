package com.talentcard.web.dto;

import com.talentcard.common.pojo.FarmhouseEnjoyPO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 18:48
 * @description: 农家乐
 * @version: 1.0
 */
@Data
public class FarmhouseDTO implements Serializable {
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
    private Long[] cardIds;

    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private Long[] categoryIds;

    /**
     * 可查看与申请此农家乐的人才学历IDs
     */
    private Integer[] educIds;

    /**
     * 可查看与申请此农家乐的人才职称IDs
     */
    private Integer[] titleIds;

    /**
     * 可查看与申请此农家乐的人才职业资格IDs
     */
    private Integer[] qualityIds;

    /**
     * 农家乐头图
     */
    private String avatar;

    /**
     * 农家乐轮播图
     */
    private String[] picture;

    /**
     * 农家乐介绍信息
     */
    private String desc;

    /**
     * 备注
     */
    private String extra;

    private static String publicPath;

    /**
     * 可查看与申请此景区的人才荣誉IDs
     */
    private Long[] honourIds;

    @Value("${file.publicPath}")
    private void setPublicPath(String publicPath) {
        FarmhouseDTO.publicPath = publicPath;
    }

    /**
     * 根据 dto 设置 po
     *
     * @param po
     * @param dto
     */
    public static FarmhousePO buildPO(FarmhousePO po, FarmhouseDTO dto) {
        po.setName(dto.getName());
        po.setDiscount(dto.getDiscount());
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
     * @param farmhouseId
     * @return
     */
    public static List<FarmhouseEnjoyPO> buildEnjoyPOs(FarmhouseDTO dto, Long farmhouseId) {
        List<FarmhouseEnjoyPO> pos = new ArrayList<>();

        if (null != dto.getCardIds() && dto.getCardIds().length > 0) {
            for (Long cardId : dto.getCardIds()) {
                FarmhouseEnjoyPO po = new FarmhouseEnjoyPO();
                po.setFarmhouseId(farmhouseId);
                po.setCardId(cardId);
                po.setType((byte) 1);
                pos.add(po);
            }
        }
        if (null != dto.getCategoryIds() && dto.getCategoryIds().length > 0) {
            for (Long category : dto.getCategoryIds()) {
                FarmhouseEnjoyPO po = new FarmhouseEnjoyPO();
                po.setFarmhouseId(farmhouseId);
                po.setCategoryId(category);
                po.setType((byte) 2);
                pos.add(po);
            }
        }
        if (null != dto.getEducIds() && dto.getEducIds().length > 0) {
            for (Integer educId : dto.getEducIds()) {
                FarmhouseEnjoyPO po = new FarmhouseEnjoyPO();
                po.setFarmhouseId(farmhouseId);
                po.setEducationId(educId);
                po.setType((byte) 3);
                pos.add(po);
            }
        }
        if (null != dto.getTitleIds() && dto.getTitleIds().length > 0) {
            for (Integer titleId : dto.getTitleIds()) {
                FarmhouseEnjoyPO po = new FarmhouseEnjoyPO();
                po.setFarmhouseId(farmhouseId);
                po.setTitleId(titleId);
                po.setType((byte) 4);
                pos.add(po);
            }
        }
        if (null != dto.getQualityIds() && dto.getQualityIds().length > 0) {
            for (Integer qualityId : dto.getQualityIds()) {
                FarmhouseEnjoyPO po = new FarmhouseEnjoyPO();
                po.setFarmhouseId(farmhouseId);
                po.setQuality(qualityId);
                po.setType((byte) 5);
                pos.add(po);
            }
        }
        if (null != dto.getHonourIds() && dto.getHonourIds().length > 0) {
            for (Long honourId : dto.getHonourIds()) {
                FarmhouseEnjoyPO po = new FarmhouseEnjoyPO();
                po.setFarmhouseId(farmhouseId);
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
     * @param farmhouseId
     * @return
     */
    public static List<FarmhousePicturePO> buildPicturePOs(FarmhouseDTO dto, Long farmhouseId) {
        List<FarmhousePicturePO> pos = new ArrayList<>();
        if (null != dto.getPicture() && dto.getPicture().length > 0) {
            for (String picture : dto.getPicture()) {
                FarmhousePicturePO po = new FarmhousePicturePO();
                po.setFarmhouseId(farmhouseId);
                po.setPicture(picture.split(FilePathConfig.getStaticPublicBasePath())[1]);
                pos.add(po);
            }
        }
        return pos;
    }
}
