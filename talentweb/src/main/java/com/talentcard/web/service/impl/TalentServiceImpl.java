package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.netflix.ribbon.proxy.annotation.Http;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.ExcelUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.BatchCertificateDTO;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.TalentDetailVO;
import com.talentcard.web.vo.TalentVO;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.smartcardio.Card;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:15
 * @description: 人才管理
 * @version: 1.0
 */
@Service
public class TalentServiceImpl implements ITalentService {
    private static final Logger logger = LoggerFactory.getLogger(TalentServiceImpl.class);
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private CertificationMapper certificationMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private CertApprovalMapper certApprovalMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserCardMapper userCardMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private TalentHonourMapper talentHonourMapper;
    @Autowired
    private BatchCertificateMapper batchCertificateMapper;

    private static final String[] EXCEL_TITLE = {"姓名", "证件号码"};
    private static final String[] EXCEL_TITLE_RES = {"姓名", "证件号码", "人才卡", "人才类别", "人才荣誉", "认证结果", "说明"};

    private static final Integer SUCCESS = 1;
    private static final Integer NO_TALENT = 11;
    //已认证或者待审批
    private static final Integer IN_CERTIFICATE_STATUS = 12;
    private static final Integer ERROR_TALENT_STATUS = 13;

    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TalentBO> bos = talentMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), TalentVO.convert(bos)));
    }

    @Override
    public ResultVO detail(Long tid) {
        TalentBO bo = talentMapper.queryDetail(tid);
        if (null == bo) {
            // 数据不存在
            return new ResultVO(1001);
        }

        return new ResultVO<>(1000, TalentDetailVO.convert(bo));
    }

    @Override
    public ResultVO queryCert(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TalentBO> bos = talentMapper.queryCert(reqMap);
//        // 当状态1和2同时存在时，返回状态为1的数据
//        Map<Long, TalentBO> boMap = new HashMap<>();
//        for (TalentBO bo : bos) {
//            Long key = bo.getTalentId();
//            if (boMap.containsKey(key)) {
//                if (bo.getCstatus() == 1) {
//                    boMap.put(key, bo);
//                }
//            } else {
//                boMap.put(key, bo);
//            }
//        }
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), TalentVO.convert(bos)));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO edit(Long talentId, Long cardId) {
        //找到uc表status=2，当前正在使用的卡的cardId
        HashMap<String, Object> oldCard = talentMapper.findCurrentSeniorCard(talentId);
        if (oldCard == null) {
            return new ResultVO(2401, "当前没有正在使用的高级卡");
        }
        Long currentCardId = Integer.valueOf(oldCard.get("cardId").toString()).longValue();
        if (!cardId.equals(currentCardId)) {
            this.changeTalentCard(talentId, cardId);
        }
        return new ResultVO(1000);
    }

    @Override
    public BatchCertificateDTO readCertificateFile(HttpSession httpSession, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        BatchCertificateDTO batchCertificateDTO = new BatchCertificateDTO();
        if (null == fileName) {
            // 文件内表头信息或文件格式有误，请下载模板文件检查后重新上传！
            batchCertificateDTO.setResultStatus(1100);
            return batchCertificateDTO;
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (!".xlsx".equals(suffix)) {
            // 文件内表头信息或文件格式有误，请下载模板文件检查后重新上传！
            batchCertificateDTO.setResultStatus(1100);
            return batchCertificateDTO;
        }
        //新建表
        BatchCertificatePO batchCertificatePO = new BatchCertificatePO();
        batchCertificatePO.setStatus((byte) 1);
        batchCertificatePO.setCreateTime(new Date());
        Long userId = (Long) httpSession.getAttribute("userId");
        String userName = (String) httpSession.getAttribute("username");
        batchCertificatePO.setUserId(userId);
        batchCertificatePO.setUserName(userName);
        batchCertificateMapper.add(batchCertificatePO);

        List<String> names = new LinkedList<>();
        List<String> idCards = new LinkedList<>();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);
            if (!ExcelUtil.isNormTitle(sheet.getRow(1), EXCEL_TITLE)) {
                // 文件内表头信息或文件格式有误，请下载模板文件检查后重新上传！
                batchCertificateDTO.setResultStatus(1100);
                return batchCertificateDTO;
            }

            for (int r = 2; r <= sheet.getLastRowNum(); r++) {
                XSSFRow row = sheet.getRow(r);
                names.add(ExcelUtil.getStringValue(row, 0));
                idCards.add(ExcelUtil.getStringValue(row, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            batchCertificateDTO.setResultStatus(1100);
            return batchCertificateDTO;
        }
        batchCertificateDTO.setNames(names);
        batchCertificateDTO.setIdCards(idCards);
        batchCertificateDTO.setResultStatus(1000);
        batchCertificateDTO.setBatchCertificatePO(batchCertificatePO);
        return batchCertificateDTO;
    }

    @Override
    @Async
    public ResultVO batchCertificate(BatchCertificateDTO batchCertificateDTO) throws InterruptedException {
        String fileName = batchCertificateDTO.getFileName();
        List<String> names = batchCertificateDTO.getNames();
        List<String> idCards = batchCertificateDTO.getIdCards();
        BatchCertificatePO batchCertificatePO = batchCertificateDTO.getBatchCertificatePO();
        String talentCategory = batchCertificateDTO.getTalentCategory();
        Long talentHonour = batchCertificateDTO.getTalentHonour();
        Long cardId = batchCertificateDTO.getCardId();
//        Thread.sleep(15000);
        //定义所需要的参数
        String url;
        Integer successNum = 0;
        Integer failureNum = 0;
        try {
            // TODO: 业务逻辑
            List<Integer> resultList = new ArrayList<>();
            Integer result;
            for (int i = 0; i < names.size(); i++) {
                result = certificate(idCards.get(i), names.get(i), cardId, talentCategory, talentHonour);
                resultList.add(result);
            }

            CardPO cardPO = cardMapper.selectByPrimaryKey(cardId);
            if (cardPO == null) {
                logger.info("拿不到cardPO，cardId为：{}", cardId);
            }
            String initialWord = cardPO.getInitialWord();
            if (initialWord == null) {
                initialWord = "";
            }
            String talentCard = cardPO.getTitle() + "/" + initialWord;
            String[][] rows = new String[names.size()][];
            for (int i = 0; i < names.size(); i++) {
                String[] row = new String[EXCEL_TITLE_RES.length];
                row[0] = names.get(i);
                row[1] = idCards.get(i);
                result = resultList.get(i);
                if (result.equals(SUCCESS)) {
                    row[2] = talentCard;
                    row[3] = talentCategory;
                    row[4] = talentHonour.toString();
                    row[5] = "成功";
                    row[6] = "";
                    successNum++;
                } else if (result.equals(NO_TALENT)) {
                    row[5] = "找不到此用户";
                    failureNum++;
                } else if (result.equals(IN_CERTIFICATE_STATUS)) {
                    row[5] = "已认证或认证中";
                    failureNum++;
                } else {
                    row[5] = "人才状态错误";
                    failureNum++;
                }
                rows[i] = row;
            }

            url = ExcelUtil.save(ExcelUtil.buildExcel("批量认证结果", EXCEL_TITLE_RES, rows),
                    filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getExcelDir(), "批量认证结果");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(1100);
        }

        //更新表
        batchCertificatePO.setSuccessNum(successNum);
        batchCertificatePO.setFailureNum(failureNum);
        batchCertificatePO.setTotalNum(successNum + failureNum);
        batchCertificatePO.setDownloadUrl(url);
        batchCertificatePO.setUpdateTime(new Date());
        batchCertificatePO.setStatus((byte) 2);
        batchCertificatePO.setFileName(fileName);
        batchCertificateMapper.updateByPrimaryKeySelective(batchCertificatePO);

        return new ResultVO(1000, url);
    }


    @Override
    public void clearRedisCache(String openId) {
        this.redisMapUtil.del(openId);
    }


    /**
     * 批量认证中的认证逻辑部分
     *
     * @param idCard
     * @param cardId
     * @param talentCategory
     * @param talentHonour
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer certificate(String idCard, String name, Long cardId,
                               String talentCategory, Long talentHonour) {
        TalentPO talentPO = talentMapper.selectByIdCard(idCard);
        if (talentPO == null || !talentPO.getName().equals(name)) {
            return NO_TALENT;
        }
        String openId = talentPO.getOpenId();
        //清缓存
        clearRedisCache(openId);
        Long talentId = talentPO.getTalentId();
        //判断是否处在认证状态中
        Integer checkIfCertificate = talentMapper.ifInAudit(openId);
        if (checkIfCertificate != 0) {
            return IN_CERTIFICATE_STATUS;
        }
        /**
         * 校验是否存在脏数据
         */
        //是否有一张正常使用的基本卡
        Integer checkIfDirty = certificationMapper.checkIfDirty(talentId, (byte) 5, (byte) 2);
        //是否存在待领取的卡，判断是否删卡
        HashMap<String, Object> ifExistWaitingCard = userCardMapper.findCurrentCard(openId, (byte) 1);
        if (ifExistWaitingCard != null || checkIfDirty != 1) {
            return ERROR_TALENT_STATUS;
        }
        /**
         * 正常认证业务逻辑
         */
        //设置状态值 状态1为已认证
        Byte status = (byte) 4;
        //人才表
        talentPO.setStatus((byte) 1);
        talentPO.setCategory(talentCategory);
        talentMapper.updateByPrimaryKeySelective(talentPO);

        //认证表
        CertificationPO certificationPO = new CertificationPO();
        certificationPO.setCreateTime(new Date());
        certificationPO.setStatus(status);
        certificationPO.setTalentId(talentId);
        certificationPO.setType((byte) 4);
        certificationMapper.add(certificationPO);
        Long certificationId = certificationPO.getCertId();

        //学历表
        EducationPO educationPO = new EducationPO();
        educationPO.setCertId(certificationId);
        educationPO.setTalentId(talentId);
        educationPO.setStatus(status);
        educationPO.setIfCertificate((byte) 1);
        educationMapper.insertSelective(educationPO);

        //职称表
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setCertId(certificationId);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setStatus(status);
        profTitlePO.setIfCertificate((byte) 1);
        profTitleMapper.insertSelective(profTitlePO);


        //职业资格表
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setCertId(certificationId);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setStatus(status);
        profQualityPO.setIfCertificate((byte) 1);
        profQualityMapper.insertSelective(profQualityPO);

        //荣誉表
        TalentHonourPO talentHonourPO = new TalentHonourPO();
        talentHonourPO.setCertId(certificationId);
        talentHonourPO.setTalentId(talentId);
        talentHonourPO.setStatus(status);
        talentHonourPO.setIfCertificate((byte) 1);
        talentHonourMapper.insertSelective(talentHonourPO);

        ActivcateBO oldCard = talentMapper.activate(talentPO.getOpenId(), (byte) 5, (byte) 2);
        //修改基本信息表
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByPrimaryKey(oldCard.getUciId());
        if (userCurrentInfoPO == null) {
            logger.error("批量认证时：找不到userCurrentInfoPO！");
            return ERROR_TALENT_STATUS;
        }
        userCurrentInfoPO.setHonourId(talentHonour);
        userCurrentInfoPO.setTalentCategory(talentCategory);
        Integer updateResult = userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
        if (updateResult != 0) {
            logger.error("批量认证时：更新usi表失败！");
        }
        /**
         * 用上一张卡的当前人数和编号数据
         */
        CardPO cardPO = cardMapper.selectByPrimaryKey(cardId);
        HashMap<String, Object> currentCard = userCardMapper.findCurrentCard(openId, (byte) 2);
        if (currentCard == null) {
            logger.error("批量认证时：找不到userCardMapper.findCurrentCard！");
            return ERROR_TALENT_STATUS;
        }
        String currentNum = (String) currentCard.get("currentNum");
        String membershipNumber = cardPO.getInitialWord() + cardPO.getAreaNum() + currentNum;
        //新增人卡表
        UserCardPO userCardPO = new UserCardPO();
        userCardPO.setTalentId(talentId);
        userCardPO.setCardId(cardId);
        userCardPO.setNum(membershipNumber);
        userCardPO.setName(cardPO.getTitle());
        userCardPO.setCurrentNum(currentNum);
        userCardPO.setStatus((byte) 1);
        userCardPO.setCreateTime(new Date());
        userCardMapper.insertSelective(userCardPO);

        //卡表中 高级卡更新待领取数量
        cardPO.setWaitingMemberNum(cardPO.getWaitingMemberNum() + 1);
        updateResult = cardMapper.updateByPrimaryKeySelective(cardPO);
        if (updateResult == 0) {
            logger.error("update cardMapper error");
            return ERROR_TALENT_STATUS;
        }
        //certApproval表
        CertApprovalPO certApprovalPO = new CertApprovalPO();
        certApprovalPO.setType((byte) 2);
        certApprovalPO.setCertId(cardId);
        certApprovalPO.setCreateTime(new Date());
        certApprovalPO.setCardId(cardId);
        certApprovalPO.setUpdateTime(new Date());
        certApprovalPO.setResult((byte) 1);
        certApprovalMapper.insertSelective(certApprovalPO);

        sendMessage(talentPO);
        return SUCCESS;

    }

    private void sendMessage(TalentPO talentPO) {
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //姓名
        messageDTO.setKeyword1(talentPO.getName());
        //证件号码
        String identificationCardNum = identificationCardEncryption(talentPO);
        messageDTO.setKeyword2(identificationCardNum);

        //领卡机构
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword4(currentTime);

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
        ActivcateBO oldCard = talentMapper.activate(talentPO.getOpenId(), (byte) 5, (byte) 2);
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


    /**
     * 根据talentId和cardId，高级卡更换高级卡
     *
     * @param talentId
     * @param cardId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO changeTalentCard(Long talentId, Long cardId) {
        HashMap<String, Object> oldCard = talentMapper.findCurrentSeniorCard(talentId);
        Long oldCertId = Integer.valueOf(oldCard.get("certId").toString()).longValue();
        Byte newCStatus = (byte) 4;
        /**
         * 新卡
         */
        //新增4表状态4
        //c表
        CertificationPO oldCertificationPO = certificationMapper.selectByPrimaryKey(oldCertId);
        oldCertificationPO.setCertId(null);
        oldCertificationPO.setStatus(newCStatus);
        oldCertificationPO.setType((byte) 3);
        certificationMapper.add(oldCertificationPO);
        Long newCertId = oldCertificationPO.getCertId();
        //学历
        EducationPO oldEducationPO = educationMapper.selectByCertId(oldCertId);
        oldEducationPO.setEducId(null);
        oldEducationPO.setStatus(newCStatus);
        oldEducationPO.setCertId(newCertId);
        educationMapper.insertSelective(oldEducationPO);
        //职称
        ProfTitlePO oldProfTitlePO = profTitleMapper.selectByCertId(oldCertId);
        oldProfTitlePO.setPtId(null);
        oldProfTitlePO.setStatus(newCStatus);
        oldProfTitlePO.setCertId(newCertId);
        profTitleMapper.insertSelective(oldProfTitlePO);
        //职业资格
        ProfQualityPO oldProfQualityPO = profQualityMapper.selectByCertId(oldCertId);
        oldProfQualityPO.setPqId(null);
        oldProfQualityPO.setStatus(newCStatus);
        oldProfQualityPO.setCertId(newCertId);
        profQualityMapper.insertSelective(oldProfQualityPO);

        //新增uc表状态1；更新card表
        UserCardPO newUserCardPO = new UserCardPO();
        newUserCardPO.setTalentId(talentId);
        newUserCardPO.setCardId(cardId);
        //新卡PO
        CardPO newCardPO = cardMapper.selectByPrimaryKey(cardId);
        //设置当前编号，组合起来，并且更新卡的currentNum
        String membershipNumber = newCardPO.getInitialWord();
        Integer initialNumLength = newCardPO.getInitialNum().length();
        Integer currentNumLength = newCardPO.getCurrNum().toString().length();
        //补0
        if ((initialNumLength - currentNumLength) > 0) {
            for (int i = 0; i < (initialNumLength - currentNumLength); i++) {
                membershipNumber = membershipNumber + "0";
            }
        }
        membershipNumber = membershipNumber + newCardPO.getCurrNum();
        newUserCardPO.setNum(membershipNumber);
        newCardPO.setCurrNum(newCardPO.getCurrNum() + 1);
        newCardPO.setWaitingMemberNum(newCardPO.getWaitingMemberNum() + 1);
        //人卡表里设置参数；添加数据
        newUserCardPO.setCreateTime(new Date());
        newUserCardPO.setStatus((byte) 1);
        newUserCardPO.setName(newCardPO.getName());
        int updateResult = cardMapper.updateByPrimaryKeySelective(newCardPO);
        if (updateResult == 0) {
            logger.error("update cardMapper error");
        }
        userCardMapper.insertSelective(newUserCardPO);

        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        messageDTO.setFirst("您好，请您领取衢江区人才卡");
        //姓名
        messageDTO.setKeyword1(talentPO.getName());
        //身份证号，屏蔽八位
        String encryptionIdCard = talentPO.getIdCard().substring(0, 9) + "********";
        messageDTO.setKeyword2(encryptionIdCard);
        //领卡机构
        messageDTO.setKeyword3("个人");
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword4(currentTime);
        //模版编号
        messageDTO.setTemplateId(1);
        //结束
        messageDTO.setRemark("领取后可享受多项人才权益哦");
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findBatchCertificate(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<BatchCertificatePO> batchCertificatePOList = batchCertificateMapper.findBatchCertificate(hashMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), batchCertificatePOList));
    }
}
