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
        ActivcateBO firstActivate = talentMapper.activate(openId, (byte) 2, (byte) 1);
        //不为null代表存在c表状态2，uc表状态1，即为待领取的卡是基本卡。
        if (firstActivate != null) {
            logger.info("第一次激活");
            //第一次激活
            Long certId = firstActivate.getCertId();
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
            userCardMapper.updateStatusById(firstActivate.getTalentId(), (byte) 1, (byte) 2);
        } else {
            logger.info("第二次激活");
            //第二次激活
            ActivcateBO activateInfo = talentMapper.activate(openId, (byte) 4, (byte) 1);
            //1.cert_approval里的人才类别信息更新user_current_info表
            CertApprovalPO certApprovalPO = certApprovalMapper.findByCertId(activateInfo.getCertId());
            UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByPrimaryKey(activateInfo.getUciId());
            userCurrentInfoPO.setTalentCategory(certApprovalPO.getCategory());
            userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
            //2.certification表和三表，更新status=1:正在使用
            Long certId = activateInfo.getCertId();
            CertificationPO certificationPO = certificationMapper.selectByPrimaryKey(certId);
            Byte status = 1;
            certificationPO.setStatus(status);
            certificationMapper.updateByPrimaryKeySelective(certificationPO);
            educationMapper.updateStatusByCertId(certId, status);
            profTitleMapper.updateStatusByCertId(certId, status);
            profQualityMapper.updateStatusByCertId(certId, status);
            //3.user_card更新（查询status=2的卡，改为status=3）（之前正在使用的旧卡变为废弃）
            userCardMapper.updateStatusById(activateInfo.getTalentId(), (byte) 2, (byte) 3);
            //4.user_card更新（查询status=1的卡，改为status=2）（之前待使用的旧卡变为正在使用）
            userCardMapper.updateStatusById(activateInfo.getTalentId(), (byte) 1, (byte) 2);
            //旧卡数量-1
            CardPO cardPO = cardMapper.selectByPrimaryKey(activateInfo.getCardId());
            cardPO.setMemberNum(cardPO.getMemberNum() - 1);
            cardMapper.updateByPrimaryKeySelective(cardPO);
            //旧卡（基本卡）更改状态：c表状态1，uc表状态2，即为正在使用的卡
            ActivcateBO oldCard = talentMapper.activate(openId, (byte) 1, (byte) 2);
            Long oldTalentId = oldCard.getTalentId();
            Long oldCertId = oldCard.getCertId();
            Byte oldStatus;
            //判断旧卡是基本卡还是高级卡，基本卡就改为失效9，高级卡改为失效10
            Integer ifOldCardIsBaseCard = certificationMapper.ifOldCardIsBaseCard(oldTalentId);
            if (ifOldCardIsBaseCard == 0) {
                //说明c表里找不到状态9的，代表这是基本卡换高级卡
                oldStatus = 9;
            } else {
                //找到了状态9的，说明这是高级卡换高级卡
                oldStatus = 10;
            }
            //旧卡从状态1改为状态10
            certificationMapper.updateStatusById(oldTalentId, (byte) 1, oldStatus);
            educationMapper.updateStatusByCertId(oldCertId, oldStatus);
            profTitleMapper.updateStatusByCertId(oldCertId, oldStatus);
            profQualityMapper.updateStatusByCertId(oldCertId, oldStatus);
            //设置旧卡券失效
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", activateInfo.getCode());
            jsonObject.put("card_id", activateInfo.getWxCardId());
            String url = "https://api.weixin.qq.com/card/code/unavailable?access_token="
                    + AccessTokenUtil.getAccessToken();
            JSONObject result = WechatApiUtil.postRequest(url, jsonObject);
            if (result.getInteger("errcode") != 0) {
                return new ResultVO(2330, "激活失败");
            }
        }
        return new ResultVO(1000);
    }

    /**
     * 监控到用户删除卡的操作
     *
     * @param openId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(String openId) {
        //找到记录，uc表status=1，c表status=2或者4，来判断当前是否有待领取的卡
        ActivcateBO ifExistGetCard = talentMapper.findGetCard(openId);
        //c表status=1，uc表status=2
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (ifExistGetCard != null) {
            //有待领取的卡，把正在使用的卡改为失效
            Long talentId = activcateBO.getTalentId();
            //c表status=1找正在使用的certId
            Long certId = activcateBO.getCertId();
            Byte status = 10;
            //4表，status从1改为10
            certificationMapper.updateStatusById(talentId, (byte) 1, status);
            educationMapper.updateStatusByCertId(certId, status);
            profTitleMapper.updateStatusByCertId(certId, status);
            profQualityMapper.updateStatusByCertId(certId, status);
            //uc表，status从2改为3
            userCardMapper.updateStatusById(talentId, (byte) 2, (byte) 3);
        } else {
            //没有待领取的卡，则把正在使用的卡改为待领取
            Long talentId = activcateBO.getTalentId();
            Long certId = activcateBO.getCertId();
            CardPO cardPO = cardMapper.selectByPrimaryKey(activcateBO.getCardId());
            Byte cardType = cardPO.getStatus();
            //uc的status=2的改为=1
            userCardMapper.updateStatusById(talentId, (byte) 2, (byte) 1);
            //4表status=1的改为=2或者4
            Byte status;
            //判断是否是基本卡
            if (cardType == 1) {
                status = 2;
            } else {
                status = 4;
            }
            certificationMapper.updateStatusById(talentId, (byte) 1, status);
            educationMapper.updateStatusByCertId(certId, status);
            profTitleMapper.updateStatusByCertId(certId, status);
            profQualityMapper.updateStatusByCertId(certId, status);
        }
        //无论是有待领取的卡，还是没有待领取的卡，正在使用的卡都数量-1
        CardPO cardPO = cardMapper.selectByPrimaryKey(activcateBO.getCardId());
        cardPO.setMemberNum(cardPO.getMemberNum() - 1);
        cardMapper.updateByPrimaryKeySelective(cardPO);
        //修改状态
        return new ResultVO(1000);
    }
}
