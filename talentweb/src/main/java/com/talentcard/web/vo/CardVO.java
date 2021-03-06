package com.talentcard.web.vo;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.CardPO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-04-28 14:06
 * @description
 */
@Data
@Component
public class CardVO {
    private Long cardId;
    private String name;
    private String title;
    private Long memberNum;
    private Long waitingMemberNum;
    private Long currNum;
    private String description;
    private String picture;
    private String pictureCdn;
    private String logoUrl;
    private String prerogative;
    private String initialWord;
    private String initialNum;
    private String businessDescription;
    private List<String> policyInfo;
    private String createPerson;
    private String updatePerson;
    private Date createTime;
    private Date updateTime;
    private String areaNum;
    private Integer tripTimes;

    /**
     * PO转VO
     *
     * @param cardPO
     * @return
     */
    public static CardVO convert(CardPO cardPO) {
        CardVO cardVO = new CardVO();
        cardVO.setCardId(cardPO.getCardId());
        cardVO.setName(cardPO.getName());
        cardVO.setTitle(cardPO.getTitle());
        cardVO.setMemberNum(cardPO.getMemberNum());
        cardVO.setWaitingMemberNum(cardPO.getWaitingMemberNum());
        cardVO.setCurrNum(cardPO.getCurrNum());
        cardVO.setDescription(cardPO.getDescription());
        cardVO.setPicture(cardPO.getPicture());
        cardVO.setPictureCdn(cardPO.getPictureCdn());
        cardVO.setLogoUrl(cardPO.getLogoUrl());
        cardVO.setPrerogative(cardPO.getPrerogative());
        cardVO.setInitialWord(cardPO.getInitialWord());
        cardVO.setInitialNum(cardPO.getInitialNum());
        cardVO.setBusinessDescription(cardPO.getBusinessDescription());
        cardVO.setCreatePerson(cardPO.getCreatePerson());
        cardVO.setUpdatePerson(cardPO.getUpdatePerson());
        cardVO.setCreateTime(cardPO.getCreateTime());
        cardVO.setUpdateTime(cardPO.getUpdateTime());
        cardVO.setAreaNum(cardPO.getAreaNum());
        cardVO.setTripTimes(cardPO.getTripTimes());

        //背景图
        if (!StringUtils.isEmpty(cardVO.getPicture())) {
            cardVO.setPicture(FilePathConfig.getStaticPublicBasePath() + cardVO.getPicture());
        }
        //logo图
        //背景图
        if (!StringUtils.isEmpty(cardVO.getLogoUrl())) {
            cardVO.setLogoUrl(FilePathConfig.getStaticPublicBasePath() + cardVO.getLogoUrl());
        }
        return cardVO;
    }

    /**
     * BOS转VOS
     *
     * @param cardPOList
     * @return
     */
    public static List<CardVO> convert(List<CardPO> cardPOList) {
        List<CardVO> cardVOList = new ArrayList<>();
        for (CardPO cardPO : cardPOList) {
            cardVOList.add(CardVO.convert(cardPO));
        }
        return cardVOList;
    }
}
