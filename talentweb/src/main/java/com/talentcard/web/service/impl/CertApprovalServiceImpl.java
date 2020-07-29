package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.bo.CertApprovalBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ICertApprovalService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.ApprovalItemsVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.vo.ApprovalTalentVO;
import com.talentcard.web.vo.TalentCertificationRecordVO;
import com.talentcard.web.vo.TalentJsonRecordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    TalentHonourMapper talentHonourMapper;
    @Resource
    UserCardMapper userCardMapper;
    @Resource
    CardMapper cardMapper;
    @Resource
    UserCurrentInfoMapper userCurrentInfoMapper;
    @Resource
    ITalentService talentService;
    @Autowired
    TestTalentInfoMapper testTalentInfoMapper;
    @Autowired
    CertExamineRecordMapper certExamineRecordMapper;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    TalentJsonRecordMapper talentJsonRecordMapper;
    @Autowired
    InsertCertificationMapper insertCertificationMapper;
    @Autowired
    CertApprovalPassRecordMapper certApprovalPassRecordMapper;
    @Autowired
    private ILogService logService;
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
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        // 判断审批结果，如果审批通过，需要多表更新；审批不通过则只新增认证审批表的信息
        //首先获取审批人的用户id
        userId = (Long) session.getAttribute("userId");
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());

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

        //更新审批认证记录表
        CertExamineRecordPO certExamineRecordPO = certExamineRecordMapper.selectByCertId(certId);
        certExamineRecordPO.setResult(result);
        certExamineRecordMapper.updateByPrimaryKeySelective(certExamineRecordPO);

        if (FAILURE.equals(result)) {
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
            //更新学历表的认证状态
            educationMapper.updateStatusByCertId(certId, (byte) 10);
            //更新职称表的认证状态
            profTitleMapper.updateStatusByCertId(certId, (byte) 10);
            //更新职业资格表的认证状态
            profQualityMapper.updateStatusByCertId(certId, (byte) 10);
            //更新人才荣誉表的认证状态
            talentHonourMapper.updateStatusByCertId(certId, (byte) 10);
            /**
             * 用消息模板推送微信消息
             */
            MessageDTO messageDTO = new MessageDTO();
            //openId
            messageDTO.setOpenid(currentTalent.getOpenId());
            //first内容
            messageDTO.setFirst("您好，您的认证信息审批被驳回，请按照驳回意见修改后重新提交");
            //申请姓名
            messageDTO.setKeyword1(currentTalent.getName());
            //申请时间
            messageDTO.setKeyword2(currentTime);
            //审核状态
            messageDTO.setKeyword3("驳回");
            //原因说明
            messageDTO.setKeyword4((String) reqData.get("opinion"));
            //模版编号
            messageDTO.setTemplateId(2);
            //推送驳回微信消息
            MessageUtil.sendTemplateMessage(messageDTO);

            /**
             * 清除redis缓存
             */
            talentService.clearRedisCache(openId);

        } else {
            /**
             * 审批通过
             */
            /**
             * 校验是否存在脏数据
             */
            Integer checkIfDirty = certificationMapper.checkIfDirty(talentId, (byte) 5, (byte) 2);
            Integer checkIfInCertificate = certificationMapper.checkIfDirty(talentId, (byte) 3, null);
            Integer checkIfCompleteCertificate = certificationMapper.checkIfDirty(talentId, (byte) 4, null);
            if (checkIfDirty != 1 || checkIfInCertificate != 1 || checkIfCompleteCertificate != 0) {
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
            TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
            talentPO.setStatus((byte) 1);
            talentPO.setCardId(cardId);
            talentPO.setCategory(category);
            int resultUpdateTalent = talentMapper.updateByPrimaryKeySelective(talentPO);
            if (resultUpdateTalent == 0) {
                //更新人才表失败
                return new ResultVO(2113);
            }
            //(4) 更新学历表
            EducationPO educationPO = educationMapper.selectByCertId(certId);
            if (educationPO != null) {
                educationPO.setStatus((byte) 4);
                educationPO.setIfCertificate((byte) 1);
                educationMapper.updateByPrimaryKeySelective(educationPO);
            }
            //(5) 更新职称表
            ProfTitlePO profTitlePO = profTitleMapper.selectByCertId(certId);
            if (profTitlePO != null) {
                profTitlePO.setStatus((byte) 4);
                profTitlePO.setIfCertificate((byte) 1);
                profTitleMapper.updateByPrimaryKeySelective(profTitlePO);
            }
            //(6) 更新职业资格
            ProfQualityPO profQualityPO = profQualityMapper.selectByCertId(certId);
            if (profQualityPO != null) {
                profQualityPO.setStatus((byte) 4);
                profQualityPO.setIfCertificate((byte) 1);
                profQualityMapper.updateByPrimaryKeySelective(profQualityPO);
            }
            //更新人才荣誉表
            TalentHonourPO talentHonourPO = talentHonourMapper.selectByCertId(certId);
            if (talentHonourPO != null) {
                talentHonourPO.setStatus((byte) 4);
                talentHonourPO.setIfCertificate((byte) 1);
                talentHonourMapper.updateByPrimaryKeySelective(talentHonourPO);
            }
            /**
             * 更新uci表和tci表
             */
            UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentId);
            if (userCurrentInfoPO == null) {
                return new ResultVO(2500);
            }
            TalentCertificationInfoPO talentCertificationInfoPO = new TalentCertificationInfoPO();
            talentCertificationInfoPO.setTalentId((talentId));
            talentCertificationInfoPO.setTalentCategory(category);
            userCurrentInfoPO.setPolitical(talentPO.getPolitical());
            if (educationPO != null) {
                userCurrentInfoPO.setEducation(educationPO.getEducation());
                userCurrentInfoPO.setSchool(educationPO.getSchool());
                userCurrentInfoPO.setFirstClass(educationPO.getFirstClass());
                userCurrentInfoPO.setMajor(educationPO.getMajor());
                userCurrentInfoPO.setGraduateTime(educationPO.getGraduateTime());
                talentCertificationInfoPO.setEducation(educationPO.getEducation().toString());
            } else {
                talentCertificationInfoPO.setEducation("0");
            }
            if (profTitlePO != null) {
                userCurrentInfoPO.setPtCategory(profTitlePO.getCategory());
                userCurrentInfoPO.setPtInfo(profTitlePO.getInfo());
                talentCertificationInfoPO.setPtCategory(profTitlePO.getCategory().toString());
            } else {
                talentCertificationInfoPO.setPtCategory("0");
            }
            if (profQualityPO != null) {
                userCurrentInfoPO.setPqCategory(profQualityPO.getCategory());
                userCurrentInfoPO.setPqInfo(profQualityPO.getInfo());
                talentCertificationInfoPO.setPqCategory(profQualityPO.getCategory().toString());
            } else {
                talentCertificationInfoPO.setPqCategory("0");
            }
            userCurrentInfoPO.setTalentCategory(category);
            if (talentHonourPO != null) {
                userCurrentInfoPO.setHonourId(talentHonourPO.getHonourId());
                userCurrentInfoPO.setThInfo(talentHonourPO.getInfo());
                talentCertificationInfoPO.setHonourId(talentHonourPO.getHonourId().toString());
            } else {
                talentCertificationInfoPO.setHonourId("0");
            }
            //新增tci表
            talentCertificationInfoMapper.insertSelective(talentCertificationInfoPO);
            int resultUserCurrentInfo = userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
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
             * 用上一张卡的当前人数和编号数据
             */

            HashMap<String, Object> currentCard = userCardMapper.findCurrentCard(openId, (byte) 2);
            if (currentCard == null) {
                return new ResultVO<>(2500, "查无此人！");
            }
            //当前编号
            String currentNum = (String) currentCard.get("currentNum");
            //人才卡编号
            String membershipNumber = cardPO.getInitialWord() + cardPO.getAreaNum() + currentNum;
            userCardPO.setNum(membershipNumber);
            userCardPO.setName(cardPO.getTitle());
            userCardPO.setCurrentNum(currentNum);

            //高级卡更新待领取数量
            cardPO.setWaitingMemberNum(cardPO.getWaitingMemberNum() + 1);
            int updateResult = cardMapper.updateByPrimaryKeySelective(cardPO);
            if (updateResult == 0) {
                logger.error("update cardMapper error");
            }

            /**
             * 用消息模板推送微信消息
             */
            MessageDTO messageDTO = new MessageDTO();
            //openId
            messageDTO.setOpenid(currentTalent.getOpenId());
            //first
            messageDTO.setFirst("您好，您的认证申请已通过，请您点击领取衢江人才卡");
            //申请人姓名
            messageDTO.setKeyword1(currentTalent.getName());
            //身份证号
            String identificationCardNum = identificationCardEncryption(currentTalent);
            messageDTO.setKeyword2(identificationCardNum);
            //领卡机构
            messageDTO.setKeyword3("个人");
            //通知时间
            messageDTO.setKeyword4(currentTime);
            //remark
            messageDTO.setRemark("领取后可享受多项人才权益哦");
            //url
            messageDTO.setUrl(WebParameterUtil.getIndexUrl());
            //模版编号
            messageDTO.setTemplateId(1);
            /**
             * 我是标记，测试完毕后删除
             */
            int flagTest = 0;
            TestTalentInfoPO testTalentInfoPO = testTalentInfoMapper.selectByOpenId(openId);
            if (testTalentInfoPO != null) {
                userCardPO.setNum(testTalentInfoPO.getSeniorCardNum());
                flagTest = 1;
            }
            /**
             * 我是结束标记，测试完毕后删除
             */
            /**
             * 设置旧卡券失效
             */
            ActivcateBO oldCard = talentMapper.activate(openId, (byte) 5, (byte) 2);
            if (oldCard != null && flagTest == 0) {
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

            /**
             * 清除redis缓存
             */
            talentService.clearRedisCache(openId);

            /**
             * 审批通过后，让talentBO存入t_cert_approval_pass_record里
             */
            TalentBO talentBO = talentMapper.certApprovalDetail(certId);
            String talentBoJson = "";
            if (talentBO != null) {
                talentBoJson = JSONObject.toJSONString(talentBO);
            }
            CertApprovalPassRecordPO certApprovalPassRecordPO = new CertApprovalPassRecordPO();
            certApprovalPassRecordPO.setCertId(certId);
            certApprovalPassRecordPO.setCreateTime(new Date());
            certApprovalPassRecordPO.setTalentId(talentId);
            certApprovalPassRecordPO.setTalentBoJson(talentBoJson);
            certApprovalPassRecordMapper.insertSelective(certApprovalPassRecordPO);
        }

        /**
         * 更新认证审批表，发起认证（提交时那一条）的updateTime
         */
        CertApprovalPO oldCertApprovalPO = certApprovalMapper.findByCertId(certId, (byte) 1, null);
        oldCertApprovalPO.setUpdateTime(new Date());
        int updateResult = certApprovalMapper.updateByPrimaryKeySelective(oldCertApprovalPO);
        if (updateResult == 0) {
            logger.error("update certApprovalMapper error");
        }

        /**
         * 增加record日志
         */
        TalentJsonRecordVO talentJsonRecordVO = new TalentJsonRecordVO();
        //一般认证VO
        TalentCertificationRecordVO talentCertificationRecordVO = new TalentCertificationRecordVO();
        talentCertificationRecordVO.setEducationPO(educationMapper.selectByCertId(certId));
        talentCertificationRecordVO.setProfQualityPO(profQualityMapper.selectByCertId(certId));
        talentCertificationRecordVO.setProfTitlePO(profTitleMapper.selectByCertId(certId));
        talentCertificationRecordVO.setTalentHonourPO(talentHonourMapper.selectByCertId(certId));
        talentCertificationRecordVO.setCertApprovalPO(certApprovalPo);
        talentCertificationRecordVO.setCertId(certId);
        //recordVO
        talentJsonRecordVO.setType((byte) 1);
        talentJsonRecordVO.setTalentCertificationRecordVO(talentCertificationRecordVO);
        //recordPO，新增record表
        TalentJsonRecordPO talentJsonRecordPO = new TalentJsonRecordPO();
        talentJsonRecordPO.setInfo(JSONObject.toJSONString(talentJsonRecordVO));
        talentJsonRecordPO.setCreateTime(new Date());
        talentJsonRecordPO.setOpenId(openId);
        talentJsonRecordMapper.insertSelective(talentJsonRecordPO);
        /**
         * 清除redis缓存
         */
        talentService.clearRedisCache(openId);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmExam,"审批同意%s的人才信息",currentTalent.getName());
        return new ResultVO(1000);

    }


    @Override
    public ResultVO queryByNumApproval() {
        //普通认证待审批
        Integer certNumber = certificationMapper.findWaitApprovalNum();
        //新增认证待审批
        Integer insertCertNumber = insertCertificationMapper.findWaitApprovalNum();
        //求和，总数
        Integer allNumber = certNumber + insertCertNumber;
        return new ResultVO(1000, allNumber);
    }

    @Override
    public ResultVO findOne(Long talentId, Long certId) {
        // 和上面的检索status不同
        TalentBO talentBO = talentMapper.certApprovalDetail(certId);
        if (talentBO == null) {
            return new ResultVO(2500, "查无此人");
        }
        //c表状态为1或者4，代表这次为审批通过，则从record表取数据
        //因为审批通过的数据，有可能会更改
        //驳回的审批数据则永远不会变，不需要从record表获取
        if (talentBO.getCertificationStatus() == 1 || talentBO.getCertificationStatus() == 4) {
            CertApprovalPassRecordPO certApprovalPassRecordPO = certApprovalPassRecordMapper.selectByCertId(certId);
            if (certApprovalPassRecordPO == null) {
                return new ResultVO(2500, "查无此人");
            }
            talentBO = JSONObject.parseObject(certApprovalPassRecordPO.getTalentBoJson(), TalentBO.class);
        }
        List<CertApprovalBO> certApprovalBOList = certApprovalMapper.queryApprovalById(talentId, certId);
        ApprovalTalentVO approvalTalentVO = ApprovalTalentVO.convert(talentBO, certApprovalBOList);
        return new ResultVO(1000, approvalTalentVO);
    }

    /**
     * 证件号码，后四位加密,打星星
     *
     * @return
     */
    public String identificationCardEncryption(TalentPO talentPO) {

        Byte cardType = talentPO.getCardType();
        String identificationCardNum = "";
        if (cardType == 1) {
            //身份证
            identificationCardNum = talentPO.getIdCard();
        } else if (cardType == 2) {
            //护照
            identificationCardNum = talentPO.getPassport();
        } else if (cardType == 3) {
            //驾照
            identificationCardNum = talentPO.getDriverCard();
        }
        if (identificationCardNum.equals("") || identificationCardNum == null
                || identificationCardNum.length() <= 4) {
            return "当前号码出现异常！";
        }
        Integer end = identificationCardNum.length() - 4;
        String encryptionIdCardNum = identificationCardNum.substring(0, end) + "****";
        return encryptionIdCardNum;
    }

}
