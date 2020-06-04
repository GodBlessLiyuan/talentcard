package com.talentcard.front.vo;

import com.talentcard.common.bo.FarmhouseBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    private String subtitle;
    private Integer area;
    private String location;
    private Long averageCost;

    //是否收藏
    private Byte ifCollect;
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
        farmhouseVO.setSubtitle(farmhousePO.getSubtitle());
        farmhouseVO.setArea(farmhousePO.getArea());
        farmhouseVO.setLocation(farmhousePO.getLocation());
        farmhouseVO.setAverageCost(farmhousePO.getAverageCost());

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
    /**
     * 判断是否收藏
     * @param farmhouseVOList
     * @param activitySecondContentIdList
     * @return
     */
    public static List<FarmhouseVO> setIfCollect(List<FarmhouseVO> farmhouseVOList, List<Long> activitySecondContentIdList) {
        HashMap hashMap = new HashMap();
        if (activitySecondContentIdList != null) {
            for (Long activitySecondContentId : activitySecondContentIdList) {
                hashMap.put(activitySecondContentId, activitySecondContentId);
            }
        }
        for (FarmhouseVO farmhouseVO : farmhouseVOList) {
            if (hashMap.get(farmhouseVO.getFarmhouseId()) != null) {
                farmhouseVO.setIfCollect((byte) 1);
            } else {
                farmhouseVO.setIfCollect((byte) 2);
            }
        }
        return farmhouseVOList;
    }

    /**
     * 判断是否收藏
     *
     * @param farmhouseVO
     * @param activitySecondContentIdList
     * @return
     */
    public static FarmhouseVO setIfCollect(FarmhouseVO farmhouseVO, List<Long> activitySecondContentIdList) {
        HashMap<Long, Long> hashMap = new HashMap();
        if (activitySecondContentIdList != null) {
            for (Long activitySecondContentId : activitySecondContentIdList) {
                hashMap.put(activitySecondContentId, activitySecondContentId);
            }
        }
        if (hashMap.get(farmhouseVO.getFarmhouseId()) != null) {
            farmhouseVO.setIfCollect((byte) 1);
        } else {
            farmhouseVO.setIfCollect((byte) 2);
        }
        return farmhouseVO;
    }
}
