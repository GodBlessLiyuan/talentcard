package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.bo.CertApprovalBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ICertApprovalService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.ApprovalItemsVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.vo.ApprovalTalentVO;
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
    public ResultVO certApprovalShowItems(Long talentId, Long certId) {
        // 根据人才id和认证id连表查询所有信息
        ApprovalBO bo = certificationMapper.queryAllMsg(talentId, certId);
        if (null == bo) {
            //当前用户没有审批需求
            return new ResultVO(2115);
        }
        // 根据talentId 和 certId 查询 当前certId在certApproval表中的审批记录
        List<CertApprovalBO> pos = certApprovalMapper.queryApprovalById(talentId, certId);
        ApprovalItemsVO approvalItemsVO = new ApprovalItemsVO();
        approvalItemsVO.setApprovalBO(bo);
        approvalItemsVO.setApprovalItems(pos);
        // 作学历，职称等的路径拼接
        ApprovalItemsVO vo = ApprovalItemsVO.convert(approvalItemsVO);
        return new ResultVO(1000, vo);
    }

    @Override
    public ResultVO detailsLookItems(Long talentId, Long certId) {
        // 和上面的检索status不同
        ApprovalBO bo = certificationMapper.queryAllMsgLook(talentId, certId);
        List<CertApprovalBO> pos = certApprovalMapper.queryApprovalById(talentId, certId);
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
        String category = (String) reqData.get("category");

        /**
         * 根据openId 发送领卡通知
         */
        TalentPO currentTalent = talentMapper.selectByPrimaryKey(talentId);
        if (currentTalent == null) {
            return new ResultVO(2500);
        }
        String openId = currentTalent.getOpenId();
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(currentTalent.getOpenId());
        //姓名
        messageDTO.setKeyword1(currentTalent.getName());
        //身份证号，屏蔽八位
        String encryptionIdCard = currentTalent.getIdCard().substring(0, 9) + "********";
        messageDTO.setKeyword2(encryptionIdCard);

        //领卡机构
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword4(currentTime);

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
            /**
             * 驳回
             */
            certApprovalPo.setResult(FAILURE);
            int insertResult = certApprovalMapper.insertSelective(certApprovalPo);
            if (insertResult == 0) {
                //新增认证审批表失败
                return new ResultVO(2112);
            }
            // 若审批拒绝，则将当前认证表设置为失效状态
            CertificationPO certificationPo = new CertificationPO();
            certificationPo.setCertId(certId);
            certificationPo.setStatus((byte) 10);
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
            int resultProfQuality = profQualityMapper.updateStatusByCertId(certId, (byte) 10);
            if (resultProfQuality == 0) {
                //更新职业资格表状态失败
                return new ResultVO(2370);
            }
            //模版编号
            messageDTO.setTemplateId(2);
            //推送驳回微信消息
            logger.info("发通知之前");
            messageDTO.setKeyword3("未通过");
            messageDTO.setFirst("您提交的认证信息与本人真实情况存在不符，请修改后重新提交。");
            MessageUtil.sendTemplateMessage(messageDTO);

        } else {
            /**
             * 审批通过
             */
            //校验是否存在脏数据
            Integer checkIfDirty = certificationMapper.checkIfDirty(talentId, (byte) 5, (byte) 2);
            if (checkIfDirty != 1) {
                return new ResultVO(2311, "当前有脏数据，无法领取高级卡");
            }
            //人才卡编号根据人才卡当前卡id的总数+1
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
            certificationPo.setStatus((byte) 4);
            int resultUpdateCertification = certificationMapper.updateByPrimaryKeySelective(certificationPo);
            if (resultUpdateCertification == 0) {
                //更新认证表失败
                return new ResultVO(2114);
            }
            //(3) 更新人才表
            TalentPO talentPO = new TalentPO();
            talentPO.setTalentId(talentId);
            talentPO.setStatus((byte) 1);
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
            int resultProfQuality = profQualityMapper.updateStatusByCertId(certId, (byte) 4);
            if (resultProfQuality == 0) {
                //更新职称表状态失败
                return new ResultVO(2370);
            }

            //(7) 将人才类别更新到t_user_current_info当中
            int resultUserCurrentInfo = userCurrentInfoMapper.updateCategoryByTalentId(talentId, category);
            if (resultUserCurrentInfo == 0) {
                //更新人才用户信息状态失败
                return new ResultVO(2372);
            }

            //(7) 人卡表新增新增（status=1）（代表待领卡）
            UserCardPO userCardPO = new UserCardPO();
            userCardPO.setTalentId(talentId);
            userCardPO.setCardId(cardId);
            userCardPO.setStatus((byte) 1);
            userCardPO.setCreateTime(new Date());

            /**
             * 用基本卡的当前人数和编号数据
             */
            CardPO defaultCardPO = cardMapper.findDefaultCard();
            String membershipNumber = defaultCardPO.getInitialWord() + defaultCardPO.getAreaNum();
            //写死，初始后字段总共6位
            Integer initialNumLength = 6;
            Integer currentNumLength = defaultCardPO.getCurrNum().toString().length();
            if ((initialNumLength - currentNumLength) > 0) {
                for (int i = 0; i < (initialNumLength - currentNumLength); i++) {
                    membershipNumber = membershipNumber + "0";
                }
            }
            membershipNumber = membershipNumber + defaultCardPO.getCurrNum();

            userCardPO.setNum(membershipNumber);
            userCardPO.setName(cardPO.getTitle());
            cardPO.setCurrNum(cardPO.getCurrNum() + 1);
            cardPO.setWaitingMemberNum(cardPO.getWaitingMemberNum() + 1);
            cardMapper.updateByPrimaryKeySelective(cardPO);

            //推送审批通过微信消息
            messageDTO.setKeyword3("个人");
            messageDTO.setRemark("领取后可享受多项人才权益哦");
            logger.info("getIndexUrl之前");
            messageDTO.setUrl(WebParameterUtil.getIndexUrl());
            logger.info("getIndexUrl之前");
            messageDTO.setFirst("您好，请您领取衢江区人才卡");
            //模版编号
            messageDTO.setTemplateId(1);
            /**
             * 设置旧卡券失效
             */
            ActivcateBO oldCard = talentMapper.activate(openId, (byte) 5, (byte) 2);
            if (oldCard != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", oldCard.getCode());
                jsonObject.put("card_id", oldCard.getWxCardId());
                String url = "https://api.weixin.qq.com/card/code/unavailable?access_token="
                        + AccessTokenUtil.getAccessToken();
                JSONObject vertifyResult = WechatApiUtil.postRequest(url, jsonObject);
                if (vertifyResult.getInteger("errcode") != 0) {
                    logger.info("销毁旧卡 {}", vertifyResult);

                }
            }
            //领卡通知
            MessageUtil.sendTemplateMessage(messageDTO);
            //uc表最后insert，防止oldCard找到俩，因为按照uc状态为1的，用户删了卡，则会有两条uc状态为1的
            int resultUserCard = userCardMapper.insertSelective(userCardPO);
            if (resultUserCard == 0) {
                //新增人才卡状态失败
                return new ResultVO(2373);
            }
            return new ResultVO(1000);
        }
        /**
         * 更新认证审批表，发起认证（提交）的updateTime
         */
        CertApprovalPO oldCertApprovalPO = certApprovalMapper.findByCertId(certId, (byte) 1, null);
        oldCertApprovalPO.setUpdateTime(new Date());
        certApprovalMapper.updateByPrimaryKeySelective(oldCertApprovalPO);
        return new ResultVO(1000);
    }


    @Override
    public ResultVO queryByNumApproval() {
        int number = certificationMapper.findWaitApprovalNum();
        return new ResultVO(1000, number);
    }

    @Override
    public ResultVO findOne(Long talentId, Long certId) {
        // 和上面的检索status不同
        TalentBO talentBO = talentMapper.certApprovalDetail(certId);
        List<CertApprovalBO> certApprovalBOList = certApprovalMapper.queryApprovalById(talentId, certId);
        ApprovalTalentVO approvalTalentVO = ApprovalTalentVO.convert(talentBO, certApprovalBOList);
        return new ResultVO(1000, approvalTalentVO);
    }

}
