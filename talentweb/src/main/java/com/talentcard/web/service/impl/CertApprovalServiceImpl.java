package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ICertApprovalService;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.ApprovalItemsVO;
import com.talentcard.common.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:31 2020/4/15
 * @version: 1.0.0
 * @description:
 */
@Service
public class CertApprovalServiceImpl implements ICertApprovalService {
    private static final Logger logger = LoggerFactory.getLogger(CertApprovalServiceImpl.class);
    @Resource
    CertApprovalMapper certApprovalMapper;
    @Resource
    CertificationMapper certificationMapper;
    @Resource
    TalentMapper talentMapper;
    @Resource
    EducationMapper educationMapper;
    @Resource
    ProfQualityMapper profQualityMapper;
    @Resource
    ProfTitleMapper profTitleMapper;
    @Resource
    UserCardMapper userCardMapper;
    @Resource
    CardMapper cardMapper;
    @Resource
    UserCurrentInfoMapper userCurrentInfoMapper;

    /**
     * 审批result的值含义
     */
    public static final Byte SUCCESS = 1;
    public static final Byte FAILURE = 2;


    @Override
    public ResultVO certApprovalShowItems(Long talentId) {
        ApprovalBO bo = certificationMapper.queryAllMsg(talentId);
        if (null == bo) {
            //当前用户没有审批需求
            return new ResultVO(2115);
        }
        List<CertApprovalPO> pos = certApprovalMapper.queryApprovalById(talentId);
        ApprovalItemsVO approvalItemsVO = new ApprovalItemsVO();
        approvalItemsVO.setApprovalBO(bo);
        approvalItemsVO.setApprovalItems(pos);
        ApprovalItemsVO vo = ApprovalItemsVO.convert(approvalItemsVO);
        return new ResultVO(1000, vo);
    }
    @Override
    public ResultVO detailsLookItems(Long talentId){
        ApprovalBO bo = certificationMapper.queryAllMsgLook(talentId);
        List<CertApprovalPO> pos = certApprovalMapper.queryApprovalById(talentId);
        ApprovalItemsVO approvalItemsVO = new ApprovalItemsVO();
        approvalItemsVO.setApprovalBO(bo);
        approvalItemsVO.setApprovalItems(pos);
        ApprovalItemsVO vo = ApprovalItemsVO.convert(approvalItemsVO);
        return new ResultVO(1000, vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO confirmCert(HttpSession session, Map<String, Object> reqData) {

        // 判断审批结果，如果审批通过，需要多表更新；审批不通过则只新增认证审批表的信息
        //首先获取审批人的用户id
        Long userId = (Long) session.getAttribute("userId");

        Byte result = (Byte) reqData.get("result");
        Long certId = (Long) reqData.get("certId");
        Long talentId = (Long) reqData.get("talentId");
        Long cardId = (Long) reqData.get("cardId");
        String category =(String) reqData.get("category");

        logger.info("发通知之前");
        /**
         * 根据openId 发送领卡通知
         */
        TalentPO currentTalent = talentMapper.selectByPrimaryKey(talentId);
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(currentTalent.getOpenId());
        //姓名
        messageDTO.setKeyword1(currentTalent.getName());
        //身份证号，屏蔽八位
        String encryptionIdCard = currentTalent.getIdCard().substring(0, 9) + "********";
        messageDTO.setKeyword2(encryptionIdCard);
        messageDTO.setKeyword3("个人");
        //领卡机构
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword4(currentTime);
        //结束


        // 2 代表审批驳回
        //(1) 新增认证审批表
        CertApprovalPO certApprovalPo = new CertApprovalPO();
        certApprovalPo.setCertId(certId);
        //将审批类型更新到2审批
        certApprovalPo.setCreateTime(new Date());
        certApprovalPo.setType((byte) 2);
        certApprovalPo.setCategory(category);
        certApprovalPo.setUserId(userId);
        certApprovalPo.setOpinion((String) reqData.get("opinion"));
        certApprovalPo.setUpdateTime(new Date());
        if (result.equals(FAILURE)) {
            certApprovalPo.setResult(FAILURE);
            int insertResult = certApprovalMapper.insertSelective(certApprovalPo);
            if (insertResult == 0) {
                //新增认证审批表失败
                return new ResultVO(2112);
            }
            // 若审批拒绝，则将当前认证表设置为失效状态
            CertificationPO certificationPo = new CertificationPO();
            certificationPo.setCertId(certId);
            certificationPo.setStatus((byte)10);
            int resultUpdateCertification = certificationMapper.updateByPrimaryKeySelective(certificationPo);
            if (resultUpdateCertification == 0) {
                //更新认证表失败
                return new ResultVO(2114);
            }
            //(4) 更新学历表的认证状态
            int resultEducation = educationMapper.updateStatusByCertId(certId, (byte) 10);
            if (resultEducation == 0) {
                //更新学历表状态失败
                return new ResultVO(2368);
            }
            //(5) 更新职称表的认证状态
            int resultProfTitle = profTitleMapper.updateStatusByCertId(certId, (byte) 10);
            if (resultProfTitle == 0) {
                //更新职称表状态失败
                return new ResultVO(2369);
            }
            //(6) 更新职业资格表的认证状态
            int resultProfQuality = profQualityMapper.updateStatusByCertId(certId,(byte) 10);
            if (resultProfQuality == 0) {
                //更新职业资格表状态失败
                return new ResultVO(2370);
            }
            //推送驳回微信消息
            messageDTO.setFirst("您的认证信息未通过审批，可进入微信小程序重新提交");
            MessageUtil.sendTemplateMessage(messageDTO);

        }else {
            /**
             * 人才卡编号根据人才卡当前卡id的总数+1
             */
            CardPO cardPO = cardMapper.selectByPrimaryKey(cardId);
            if (null == cardPO) {
                // 当前未有人才卡
                return new ResultVO(2165);
            }

            certApprovalPo.setResult(SUCCESS);
            certApprovalPo.setCardId(cardId);
            int insertResult = certApprovalMapper.insertSelective(certApprovalPo);
            if (insertResult == 0) {
                //新增认证审批表失败
                return new ResultVO(2112);
            }
            // (2) 更新认证表
            CertificationPO certificationPo = new CertificationPO();
            certificationPo.setCertId(certId);
            certificationPo.setStatus((byte)4);
            int resultUpdateCertification = certificationMapper.updateByPrimaryKeySelective(certificationPo);
            if (resultUpdateCertification == 0) {
                //更新认证表失败
                return new ResultVO(2114);
            }
            //(3) 更新人才表
            TalentPO talentPO = new TalentPO();
            talentPO.setTalentId(talentId);
            talentPO.setStatus((byte)1);
            talentPO.setCardId(cardId);
            talentPO.setCategory(category);
            int resultUpdateTalent = talentMapper.updateByPrimaryKeySelective(talentPO);
            if (resultUpdateTalent == 0) {
                //更新人才表失败
                return new ResultVO(2113);
            }
            //(4) 更新学历表的认证状态
            int resultEducation = educationMapper.updateStatusByCertId(certId, (byte) 4);
            if (resultEducation == 0) {
                //更新学历表状态失败
                return new ResultVO(2368);
            }
            //(5) 更新职称表的认证状态
            int resultProfTitle = profTitleMapper.updateStatusByCertId(certId, (byte) 4);
            if (resultProfTitle == 0) {
                //更新职称表状态失败
                return new ResultVO(2369);
            }
            //(6) 更新职业资格表的认证状态
            int resultProfQuality = profQualityMapper.updateStatusByCertId(certId,(byte) 4);
            if (resultProfQuality == 0) {
                //更新职称表状态失败
                return new ResultVO(2370);
            }

            //(7) 将人才类别更新到t_user_current_info当中
            int resultUserCurrentInfo = userCurrentInfoMapper.updateCategoryByTalentId(talentId,category);
            if (resultUserCurrentInfo == 0) {
                //更新人才用户信息状态失败
                return new ResultVO(2372);
            }

            //(7) 人卡表新增新增（status=1）（代表待领卡）
            UserCardPO userCardPO = new UserCardPO();
            userCardPO.setTalentId(talentId);
            userCardPO.setCardId(cardId);
            userCardPO.setStatus((byte)1);
            userCardPO.setCreateTime(new Date());

            String membershipNumber = cardPO.getInitialWord();
            Integer initialNumLength = cardPO.getInitialNum().length();
            Integer currentNumlength = cardPO.getCurrNum().toString().length();
            if ((initialNumLength - currentNumlength) > 0) {
                for (int i = 0; i < (initialNumLength - currentNumlength); i++) {
                    membershipNumber = membershipNumber + "0";
                }
            }
            membershipNumber = membershipNumber + cardPO.getCurrNum();
            userCardPO.setNum(membershipNumber);
            userCardPO.setName(cardPO.getName());
            cardPO.setCurrNum(cardPO.getCurrNum() + 1);
            cardPO.setWaitingMemberNum(cardPO.getWaitingMemberNum() + 1);
            cardMapper.updateByPrimaryKeySelective(cardPO);
            int resultUserCard = userCardMapper.insertSelective(userCardPO);
            if (resultUserCard == 0) {
                //新增人才卡状态失败
                return new ResultVO(2373);
            }
            //推送审批通过微信消息
            messageDTO.setRemark("领取后可享受多项人才权益哦");
            logger.info("getIndexUrl之前");
            messageDTO.setUrl(WebParameterUtil.getIndexUrl());
            logger.info("getIndexUrl之前");
            messageDTO.setFirst("您好，请您领取衢江区人才卡");
            MessageUtil.sendTemplateMessage(messageDTO);
            return new ResultVO(1000);
        }
        return new ResultVO(1000);
    }


    @Override
    public ResultVO queryByNumApproval() {
        int number = certApprovalMapper.queryWaitApprovalNum();
        return new ResultVO(1000,number);
    }

}
