package com.talentcard.front.vo;

import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.*;
import com.talentcard.front.service.impl.TalentServiceImpl;
import com.talentcard.front.service.impl.TalentTripServiceImpl;
import lombok.Data;

import java.util.*;

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
    /**
     * 总次数；
     * 原本是scienicPO里的字段，
     * 后因第六期取消了频次，全部由card决定，
     * 所以之后全部为卡决定的总次数。
     */
    private Integer times;
    private String description;
    private String extra;
    private Date createTime;

    private String avatar;
    private String qrCode;

    private String subtitle;
    private Byte starlevel;
    private Integer area;
    private String location;

    //是否收藏
    private Byte ifCollect;
    //剩余次数
    private Integer getTimes;
    //使用期限
    private String usagePeriod;
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
        scenicVO.setSubtitle(scenicPO.getSubtitle());
        scenicVO.setStarlevel(scenicPO.getStarlevel());
        scenicVO.setArea(scenicPO.getArea());
        scenicVO.setLocation(scenicPO.getLocation());

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

    /**
     * 判断是否收藏
     *
     * @param scenicVOList
     * @param activitySecondContentIdList
     * @return
     */
    public static List<ScenicVO> assignIfCollect(List<ScenicVO> scenicVOList, List<Long> activitySecondContentIdList) {
        HashMap hashMap = new HashMap();
        if (activitySecondContentIdList != null) {
            for (Long activitySecondContentId : activitySecondContentIdList) {
                hashMap.put(activitySecondContentId, activitySecondContentId);
            }
        }
        for (ScenicVO scenicVO : scenicVOList) {
            if (hashMap.get(scenicVO.getScenicId()) != null) {
                scenicVO.setIfCollect((byte) 1);
            } else {
                scenicVO.setIfCollect((byte) 2);
            }
        }
        return scenicVOList;
    }

    /**
     * 判断是否收藏
     *
     * @param scenicVO
     * @param activitySecondContentIdList
     * @return
     */
    public static ScenicVO assignIfCollect(ScenicVO scenicVO, List<Long> activitySecondContentIdList) {
        HashMap<Long, Long> hashMap = new HashMap();
        if (activitySecondContentIdList != null) {
            for (Long activitySecondContentId : activitySecondContentIdList) {
                hashMap.put(activitySecondContentId, activitySecondContentId);
            }
        }
        if (hashMap.get(scenicVO.getScenicId()) != null) {
            scenicVO.setIfCollect((byte) 1);
        } else {
            scenicVO.setIfCollect((byte) 2);
        }
        return scenicVO;
    }

    public static ScenicVO assignUsagePeriod(ScenicVO scenicVO) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int lastDay = 0;
        if (month == 2) {
            lastDay = calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        String startTime = year + "-" + month + "-01";
        String endTime = year + "-" + month + "-" + lastDay;
        String usagePeriod = startTime + "~" + endTime;
        scenicVO.setUsagePeriod(usagePeriod);
        return scenicVO;
    }
}
