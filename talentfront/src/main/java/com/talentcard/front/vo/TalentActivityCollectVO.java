package com.talentcard.front.vo;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.FarmhouseMapper;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.TalentActivityCollectPO;
import com.talentcard.front.utils.AccessTokenUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-05 18:38
 * @description
 */
@Data
@Component
public class TalentActivityCollectVO {
    private Long talentId;
    private Long activityFirstContentId;
    private Long activitySecondContentId;
    private Date createTime;
    private String activitySecondContentName;
    private Integer area;
    private String location;
    private String subtitle;
    private String avatar;
    private Byte starlevel;
    private Double discount;
    //是否上下架
    private Byte ifReady;

    private static ScenicMapper scenicMapper;
    private static FarmhouseMapper farmhouseMapper;
//    @Autowired
//    private ScenicMapper sMapper;
//    @Autowired
//    private FarmhouseMapper fMapper;
//    @PostConstruct
//    public void TalentActivityCollectVOInitialize() {
//        scenicMapper = sMapper;
//        farmhouseMapper = fMapper;
//    }

    /**
     * POList转VOList
     *
     * @param talentActivityCollectPOList
     * @return
     */
    public static List<TalentActivityCollectVO> convert(List<TalentActivityCollectPO> talentActivityCollectPOList) {
        List<TalentActivityCollectVO> talentActivityCollectVOList = new ArrayList<>();
        if (talentActivityCollectPOList != null) {
            for (TalentActivityCollectPO talentActivityCollectPO : talentActivityCollectPOList) {
                talentActivityCollectVOList.add(TalentActivityCollectVO.convert(talentActivityCollectPO));
            }
        }
        return talentActivityCollectVOList;
    }


    /**
     * PO转VO
     *
     * @param talentActivityCollectPO
     * @return
     */
    public static TalentActivityCollectVO convert(TalentActivityCollectPO talentActivityCollectPO) {
        TalentActivityCollectVO talentActivityCollectVO = new TalentActivityCollectVO();
        talentActivityCollectVO.setActivityFirstContentId(talentActivityCollectPO.getActivityFirstContentId());
        talentActivityCollectVO.setActivitySecondContentId(talentActivityCollectPO.getActivitySecondContentId());
        talentActivityCollectVO.setCreateTime(talentActivityCollectPO.getCreateTime());
        talentActivityCollectVO.setTalentId(talentActivityCollectPO.getTalentId());
        /**
         * 景区
         */
        if (talentActivityCollectPO.getActivityFirstContentId() == 1) {
            ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(talentActivityCollectPO.getActivitySecondContentId());
            if (scenicPO != null) {
                talentActivityCollectVO.setActivitySecondContentName(scenicPO.getName());
                talentActivityCollectVO.setArea(scenicPO.getArea());
                if (scenicPO.getAvatar() != null && !StringUtils.isEmpty(scenicPO.getAvatar())) {
                    talentActivityCollectVO.setAvatar(FilePathConfig.getStaticPublicBasePath() + scenicPO.getAvatar());
                }
                talentActivityCollectVO.setLocation(scenicPO.getLocation());
                talentActivityCollectVO.setStarlevel(scenicPO.getStarlevel());
                talentActivityCollectVO.setSubtitle(scenicPO.getSubtitle());
                talentActivityCollectVO.setIfReady(scenicPO.getStatus());
            }
        } else {
            /**
             * 农家乐
             */
            FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(talentActivityCollectPO.getActivitySecondContentId());
            talentActivityCollectVO.setActivitySecondContentName(farmhousePO.getName());
            talentActivityCollectVO.setArea(farmhousePO.getArea());
            if (farmhousePO.getAvatar() != null && !StringUtils.isEmpty(farmhousePO.getAvatar())) {
                talentActivityCollectVO.setAvatar(FilePathConfig.getStaticPublicBasePath() + farmhousePO.getAvatar());
            }
            talentActivityCollectVO.setLocation(farmhousePO.getLocation());
            talentActivityCollectVO.setDiscount(farmhousePO.getDiscount());
            talentActivityCollectVO.setSubtitle(farmhousePO.getSubtitle());
            talentActivityCollectVO.setIfReady(farmhousePO.getStatus());
        }

        return talentActivityCollectVO;
    }

    @Autowired
    private void setScenicMapper(ScenicMapper scenicMapper) {
        TalentActivityCollectVO.scenicMapper = scenicMapper;
    }

    @Autowired
    private void setFarmhouseMapper(FarmhouseMapper farmhouseMapper) {
        TalentActivityCollectVO.farmhouseMapper = farmhouseMapper;
    }
}
