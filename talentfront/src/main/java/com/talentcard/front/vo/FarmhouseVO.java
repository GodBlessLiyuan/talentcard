package com.talentcard.front.vo;

import com.talentcard.common.bo.FarmhouseBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-13 19:07
 * @description
 */
@Data
public class FarmhouseVO {
    private Long farmhouseId;
    private String name;
    private Double discount;
    private String avatar;
    private String description;
    private String extra;
    private String qrCode;
    private Date createTime;

    private List<FarmhousePicturePO> farmhousePicturePOList;


    /**
     * PO转VO
     *
     * @param farmhousePO
     * @return
     */
    public static FarmhouseVO convert(FarmhousePO farmhousePO) {
        FarmhouseVO farmhouseVO = new FarmhouseVO();
        farmhouseVO.setFarmhouseId(farmhousePO.getFarmhouseId());
        farmhouseVO.setName(farmhousePO.getName());
        farmhouseVO.setDiscount(farmhousePO.getDiscount());
        farmhouseVO.setDescription(farmhousePO.getDescription());
        farmhouseVO.setExtra(farmhousePO.getExtra());
        farmhouseVO.setCreateTime(farmhousePO.getCreateTime());

        //avatar
        if (farmhousePO.getAvatar() != null && !farmhousePO.getAvatar().equals("")) {
            farmhouseVO.setAvatar(FilePathConfig.getStaticPublicBasePath() + farmhousePO.getAvatar());
        }

        //qrCode
        if (farmhousePO.getQrCode() != null && !farmhousePO.getQrCode().equals("")) {
            farmhouseVO.setQrCode(FilePathConfig.getStaticPublicBasePath() + farmhousePO.getQrCode());
        }
        return farmhouseVO;
    }

    /**
     * BO转VO
     *
     * @param farmhouseBO
     * @return
     */
    public static FarmhouseVO convert(FarmhouseBO farmhouseBO) {
        FarmhouseVO farmhouseVO = FarmhouseVO.convert((FarmhousePO) farmhouseBO);
        //scenicPicturePOList
        if (farmhouseBO.getFarmhousePicturePOList() != null) {
            for (FarmhousePicturePO farmhousePicturePO : farmhouseBO.getFarmhousePicturePOList()) {
                if (farmhousePicturePO.getPicture() != null && !farmhousePicturePO.getPicture().equals("")) {
                    farmhousePicturePO.setPicture(FilePathConfig.getStaticPublicBasePath() + farmhousePicturePO.getPicture());
                }
            }
        }
        farmhouseVO.setFarmhousePicturePOList(farmhouseBO.getFarmhousePicturePOList());
        return farmhouseVO;
    }

    /**
     * POS转VOS
     *
     * @param farmhousePOList
     * @return
     */
    public static List<FarmhouseVO> convert(List<FarmhousePO> farmhousePOList) {
        List<FarmhouseVO> farmhouseVOList = new ArrayList<>();
        for (FarmhousePO farmhousePO : farmhousePOList) {
            farmhouseVOList.add(FarmhouseVO.convert(farmhousePO));
        }
        return farmhouseVOList;
    }
}
