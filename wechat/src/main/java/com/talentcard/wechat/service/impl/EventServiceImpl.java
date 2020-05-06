package com.talentcard.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.controller.WxCardController;
import com.talentcard.wechat.service.IEventService;
import com.talentcard.wechat.utils.AccessTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


@EnableTransactionManagement
@Service
public class EventServiceImpl implements IEventService {
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    CertificationMapper certificationMapper;
    @Autowired
    EducationMapper educationMapper;
    @Autowired
    ProfTitleMapper profTitleMapper;
    @Autowired
    ProfQualityMapper profQualityMapper;
    @Autowired
    UserCardMapper userCardMapper;
    @Autowired
    CertApprovalMapper certApprovalMapper;
    @Autowired
    UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    CardMapper cardMapper;

    /**
     * 监控到用户领取卡的操作
     *
     * @param openId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO activate(String openId) {
        //判断是第一次激活还是第二次激活
        ActivcateBO newCard = talentMapper.activate(openId, (byte) 2, (byte) 1);
        JSONObject result = new JSONObject();
        //不为null代表存在c表状态2，uc表状态1，即为待领取的卡是基本卡。
        if (newCard != null) {
            /**
             * 基本卡
             */
            logger.info("第一次激活");
            //第一次激活
            Long certId = newCard.getCertId();
            //更新认证表
            CertificationPO certificationPO = certificationMapper.selectByPrimaryKey(certId);
            //certification表和三表，更新status=5:基础卡正在使用
            Byte status = 5;
            certificationPO.setStatus(status);
            certificationMapper.updateByPrimaryKeySelective(certificationPO);
            //更新三表
            educationMapper.updateStatusByCertId(certId, status);
            profTitleMapper.updateStatusByCertId(certId, status);
            profQualityMapper.updateStatusByCertId(certId, status);
            //user_card更新（查询status=1的卡，改为status=2）（之前待使用的旧卡变为正在使用）
            userCardMapper.updateStatusById(newCard.getTalentId(), (byte) 1, (byte) 2);

            //新卡card会员数量+1，待领卡数量-1
            CardPO newCardPO = cardMapper.selectByPrimaryKey(newCard.getCardId());
            newCardPO.setMemberNum(newCardPO.getMemberNum() + 1);
            //判断当前数量是否是0，是0不能再减少了
            if (newCardPO.getWaitingMemberNum() > 0) {
                newCardPO.setWaitingMemberNum(newCardPO.getWaitingMemberNum() - 1);
            } else {
                return new ResultVO(2212, "卡当前数量是0，不能再减少了！");
            }
            cardMapper.updateByPrimaryKeySelective(newCardPO);
        } else {
            /**
             * 高级卡
             */
            logger.info("第二次激活");
            //第二次激活
            /**
             * 新卡操作：待领取的卡
             */
            newCard = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (newCard == null) {
                return new ResultVO(2600, "数据库无待领取的卡");
            }
            //更新user_current_info表
            UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByPrimaryKey(newCard.getUciId());
            Long newCardCertId = newCard.getCertId();
            CertificationPO newCardCertificationPO = certificationMapper.selectByPrimaryKey(newCardCertId);
            EducationPO newCardEducationPO = educationMapper.selectByCertId(newCardCertId);
            ProfTitlePO newCardProfTitlePO = profTitleMapper.selectByCertId(newCardCertId);
            ProfQualityPO newCardProfQualityPO = profQualityMapper.selectByCertId(newCardCertId);
            if (newCardEducationPO != null) {
                userCurrentInfoPO.setEducation(newCardEducationPO.getEducation());
                userCurrentInfoPO.setSchool(newCardEducationPO.getSchool());
                userCurrentInfoPO.setFirstClass(newCardEducationPO.getFirstClass());
                userCurrentInfoPO.setMajor(newCardEducationPO.getMajor());
            }
            if (newCardProfTitlePO != null) {
                userCurrentInfoPO.setPtCategory(newCardProfTitlePO.getCategory());
                userCurrentInfoPO.setPtInfo(newCardProfTitlePO.getInfo());
            }
            if (newCardProfQualityPO != null) {
                userCurrentInfoPO.setPqCategory(newCardProfQualityPO.getCategory());
                userCurrentInfoPO.setPqInfo(newCardProfQualityPO.getInfo());
            }
            if (newCardCertificationPO != null) {
                userCurrentInfoPO.setPolitical(newCardCertificationPO.getPolitical());
            }
            userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);

            //新卡card会员数量+1，待领卡数量-1
            CardPO newCardPO = cardMapper.selectByPrimaryKey(newCard.getCardId());
            newCardPO.setMemberNum(newCardPO.getMemberNum() + 1);
            //判断当前数量是否是0，是0不能再减少了
            if (newCardPO.getWaitingMemberNum() > 0) {
                newCardPO.setWaitingMemberNum(newCardPO.getWaitingMemberNum() - 1);
            } else {
                return new ResultVO(2212, "卡当前数量是0，不能再减少了！");
            }
            cardMapper.updateByPrimaryKeySelective(newCardPO);

            /**
             * 旧卡操作
             */
            Byte oldCardStatus;
            Byte oldCardFormerStatus;
            ActivcateBO oldCard;
            //判断旧卡是基本卡还是高级卡，基本卡就改为失效9，高级卡改为失效10
            Integer ifOldCardIsBaseCard = certificationMapper.ifOldCardIsBaseCard(openId);
            if (ifOldCardIsBaseCard == 0) {
                //正在使用的卡（旧卡）是基本卡
                oldCardFormerStatus = 5;
                oldCardStatus = 9;
                oldCard = talentMapper.activate(openId, oldCardFormerStatus, (byte) 2);
                //说明c表里找不到状态9的，代表这是基本卡换高级卡
            } else {
                //正在使用的卡（旧卡）是高级卡，高级卡换高级卡
                oldCardFormerStatus = 1;
                oldCardStatus = 10;
                oldCard = talentMapper.activate(openId, oldCardFormerStatus, (byte) 2);
                //找到了状态9的，说明这是高级卡换高级卡
            }
            /**
             * 如果找不到旧卡，则是用户弱智把当前高级卡删了！！！
             */
            if (oldCard == null) {
                //user_card更新（查询status=1的卡，改为status=2）（之前待使用的旧卡变为正在使用）
                userCardMapper.updateStatusById(newCard.getTalentId(), (byte) 1, (byte) 2);
                return new ResultVO(1000, "这货把当前卡删了，真蠢");

            }
            //取出旧卡的talentId和certId
            Long oldTalentId = oldCard.getTalentId();
            Long oldCertId = oldCard.getCertId();

            //取出旧卡oldCardPO
            CardPO oldCardPO = cardMapper.selectByPrimaryKey(oldCard.getCardId());
            if (oldCardPO.getMemberNum() > 0) {
                oldCardPO.setMemberNum(oldCardPO.getMemberNum() - 1);
            } else {
                return new ResultVO(2212, "卡当前数量是0，不能再减少了！");

            }
            //旧卡数量-1
            cardMapper.updateByPrimaryKeySelective(oldCardPO);
            //旧卡4表从状态5或者1改为状态9或10
            certificationMapper.updateStatusById(oldTalentId, oldCardFormerStatus, oldCardStatus);
            educationMapper.updateStatusByCertId(oldCertId, oldCardStatus);
            profTitleMapper.updateStatusByCertId(oldCertId, oldCardStatus);
            profQualityMapper.updateStatusByCertId(oldCertId, oldCardStatus);

            //2.certification表和三表，更新status=1:正在使用
            Byte newCardStatus = 1;
            //新卡4表状态从4变为1
            certificationMapper.updateStatusById(oldTalentId, (byte) 4, newCardStatus);
            educationMapper.updateStatusByCertId(newCardCertId, newCardStatus);
            profTitleMapper.updateStatusByCertId(newCardCertId, newCardStatus);
            profQualityMapper.updateStatusByCertId(newCardCertId, newCardStatus);

            //user_card更新（查询status=2的卡，改为status=3）（之前正在使用的旧卡变为废弃）
            userCardMapper.updateStatusById(newCard.getTalentId(), (byte) 2, (byte) 3);
            //user_card更新（查询status=1的卡，改为status=2）（之前待使用的旧卡变为正在使用）
            userCardMapper.updateStatusById(newCard.getTalentId(), (byte) 1, (byte) 2);

            /**
             * 设置旧卡券失效
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", oldCard.getCode());
            jsonObject.put("card_id", oldCard.getWxCardId());
            String url = "https://api.weixin.qq.com/card/code/unavailable?access_token="
                    + AccessTokenUtil.getAccessToken();
            result = WechatApiUtil.postRequest(url, jsonObject);
            if (result.getInteger("errcode") != 0) {
                return new ResultVO(2330, "激活失败");
            }
            logger.info("销毁旧卡", result);
        }
        /**
         * 激活，更改持卡人信息
         */
        String cardHolderUrl = "https://api.weixin.qq.com/card/membercard/updateuser?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject customField1 = new JSONObject();
        customField1.put("code", newCard.getCode());
        customField1.put("card_id", newCard.getWxCardId());
        //持卡人姓名
        TalentPO cardHolder = talentMapper.selectByPrimaryKey(newCard.getTalentId());
        String cardHolderName = cardHolder.getName();
        if (cardHolderName.length() > 4) {
            cardHolderName = cardHolderName.substring(0, 4);
        }
        customField1.put("custom_field_value1", cardHolderName);
        WechatApiUtil.postRequest(cardHolderUrl, customField1);
        return new ResultVO(1000, result);
    }

    /**
     * 监控到用户删除卡的操作
     *
     * @param openId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(String openId, String eventCardId) {
        //找到记录，uc表status=1，c表status=2或者4，来判断当前是否有待领取的卡
        ActivcateBO ifExistGetCard = talentMapper.findGetCard(openId);
        //c表status=1，uc表status=2
        //判断正在使用的卡是基本卡还是高级卡
        ActivcateBO currentCard;
        //基本卡
        ActivcateBO baseCard = talentMapper.activate(openId, (byte) 5, (byte) 2);
        //高级卡
        ActivcateBO highCard = talentMapper.activate(openId, (byte) 1, (byte) 2);
        //正在使用的卡改为废弃的状态：9 or 10。9是基本卡废弃；10是高级卡废弃
        Byte dropCardStatus;
        //正在使用的卡改为待领取的状态：2 or 4。2是基本卡待领取；4是高级卡待领取
        Byte oldCardStatus;
        if (baseCard != null) {
            //基本卡
            currentCard = baseCard;
            dropCardStatus = 9;
            oldCardStatus = 2;
        } else if (highCard != null) {
            //高级卡
            currentCard = highCard;
            dropCardStatus = 10;
            oldCardStatus = 4;
        } else {
            return new ResultVO(2210, "没有正在使用的卡");
        }
        //判断删卡事件的wxCardId是否和数据库里的一致
        if (!currentCard.getWxCardId().equals(eventCardId)) {
            return new ResultVO(2211, "当前正在使用的卡和内部卡不一致！");
        }
        if (ifExistGetCard != null) {
            //有待领取的卡，把正在使用的卡改为失效
            Long talentId = currentCard.getTalentId();
            //c表status=1找正在使用的certId
            Long certId = currentCard.getCertId();
            //4表，status从1或者5改成10或者9
            CertificationPO certificationPO = certificationMapper.selectByPrimaryKey(certId);
            certificationPO.setStatus(dropCardStatus);
            certificationMapper.updateByPrimaryKeySelective(certificationPO);
            educationMapper.updateStatusByCertId(certId, dropCardStatus);
            profTitleMapper.updateStatusByCertId(certId, dropCardStatus);
            profQualityMapper.updateStatusByCertId(certId, dropCardStatus);
            //uc表，status从2改为3
            userCardMapper.updateStatusById(talentId, (byte) 2, (byte) 3);
        } else {
            //没有待领取的卡，则把正在使用的卡改为待领取
            Long talentId = currentCard.getTalentId();
            Long certId = currentCard.getCertId();
            CardPO cardPO = cardMapper.selectByPrimaryKey(currentCard.getCardId());
            //uc的status=2的改为=1
            userCardMapper.updateStatusById(talentId, (byte) 2, (byte) 1);
            //4表status=5的改为=2或者4
            CertificationPO certificationPO = certificationMapper.selectByPrimaryKey(certId);
            certificationPO.setStatus(oldCardStatus);
            certificationMapper.updateByPrimaryKeySelective(certificationPO);
            educationMapper.updateStatusByCertId(certId, oldCardStatus);
            profTitleMapper.updateStatusByCertId(certId, oldCardStatus);
            profQualityMapper.updateStatusByCertId(certId, oldCardStatus);
        }
        //无论是有待领取的卡，还是没有待领取的卡，正在使用的卡都数量-1，待领取数量+1
        CardPO cardPO = cardMapper.selectByPrimaryKey(currentCard.getCardId());
        //判断当前数量是否是0，是0不能再减少了
        if (cardPO.getMemberNum() > 0) {
            cardPO.setMemberNum(cardPO.getMemberNum() - 1);
        } else {
            return new ResultVO(2212, "卡当前数量是0，不能再减少了！");
        }
        cardPO.setWaitingMemberNum(cardPO.getWaitingMemberNum() + 1);
        cardMapper.updateByPrimaryKeySelective(cardPO);
        //修改状态
        return new ResultVO(1000);
    }
}
