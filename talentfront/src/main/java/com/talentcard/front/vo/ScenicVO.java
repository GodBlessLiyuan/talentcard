package com.talentcard.front.vo;

import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-12 15:03
 * @description
 */
@Data
public class ScenicVO {
    private Long scenicId;
    private String name;
    private Integer rate;
    private Byte unit;
    private Integer times;
    private String description;
    private String extra;
    private Date createTime;

    private String avatar;
    private String qrCode;

    private List<ScenicPicturePO> scenicPicturePOList;


    /**
     * PO转VO
     *
     * @param scenicPO
     * @return
     */
    public static ScenicVO convert(ScenicPO scenicPO) {
        ScenicVO scenicVO = new ScenicVO();
        scenicVO.setScenicId(scenicPO.getScenicId());
        scenicVO.setName(scenicPO.getName());
        scenicVO.setRate(scenicPO.getRate());
        scenicVO.setUnit(scenicPO.getUnit());
        scenicVO.setTimes(scenicPO.getTimes());
        scenicVO.setDescription(scenicPO.getDescription());
        scenicVO.setExtra(scenicPO.getExtra());
        scenicVO.setCreateTime(scenicPO.getCreateTime());

        //avatar
        if (scenicPO.getAvatar() != null && !scenicPO.getAvatar().equals("")) {
            scenicVO.setAvatar(FilePathConfig.getStaticPublicBasePath() + scenicPO.getAvatar());
        }

        //qrCode
        if (scenicPO.getQrCode() != null && !scenicPO.getQrCode().equals("")) {
            scenicVO.setQrCode(FilePathConfig.getStaticPublicBasePath() + scenicPO.getQrCode());
        }
        return scenicVO;
    }

    /**
     * BO转VO
     *
     * @param scenicBO
     * @return
     */
    public static ScenicVO convert(ScenicBO scenicBO) {
        ScenicVO scenicVO = ScenicVO.convert((ScenicPO) scenicBO);
        //scenicPicturePOList
        if (scenicBO.getScenicPicturePOList() != null) {
            for (ScenicPicturePO scenicPicturePO : scenicBO.getScenicPicturePOList()) {
                if (scenicPicturePO.getPicture() != null && !scenicPicturePO.getPicture().equals("")) {
                    scenicPicturePO.setPicture(FilePathConfig.getStaticPublicBasePath() + scenicPicturePO.getPicture());
                }
            }
        }
        scenicVO.setScenicPicturePOList(scenicBO.getScenicPicturePOList());
        return scenicVO;
    }

    /**
     * POS转VOS
     *
     * @param scenicPOList
     * @return
     */
    public static List<ScenicVO> convert(List<ScenicPO> scenicPOList) {
        List<ScenicVO> scenicVOList = new ArrayList<>();
        for (ScenicPO scenicPO : scenicPOList) {
            scenicVOList.add(ScenicVO.convert(scenicPO));
        }
        return scenicVOList;
    }
}
