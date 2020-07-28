package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.netflix.ribbon.proxy.annotation.Http;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.bo.TalentCertificationBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.ExcelUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.BatchCertificateDTO;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.BatchCertificateUtil;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.TalentDetailVO;
import com.talentcard.web.vo.TalentVO;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    CertApprovalPassRecordMapper certApprovalPassRecordMapper;

    private static final String[] EXCEL_TITLE = {"姓名", "证件号码"};
    private static final String[] EXCEL_TITLE_RES = {"姓名", "证件号码", "人才卡", "人才类别", "人才荣誉", "认证结果", "说明"};

    private static final Integer SUCCESS = 1;
    private static final Integer NO_TALENT = 11;
    //已认证或者待审批
    private static final Integer IN_CERTIFICATE_STATUS = 12;
    private static final Integer ERROR_TALENT_STATUS = 13;
    @Autowired
    private ILogService logService;
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
//        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
//        List<TalentBO> bos = talentMapper.queryCert(reqMap);
//        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), TalentVO.convert(bos)));
        Page<TalentCertificationBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TalentCertificationBO> talentCertificationBOList = talentCertificationInfoMapper.queryCertTalent(reqMap);
        /**
         * 设置卡的信息
         */
        CardPO cardPO;
        TalentCertificationBO talentCertificationBO;
        List<TalentCertificationBO> resultList = new ArrayList<>();
        for (int i = 0; i < talentCertificationBOList.size(); i++) {
            cardPO = null;
            talentCertificationBO = null;
            talentCertificationBO = talentCertificationBOList.get(i);
            cardPO = cardMapper.selectByPrimaryKey(talentCertificationBO.getCardId());
            if (cardPO == null) {
                continue;
            }
            talentCertificationBO.setCInitialWord(cardPO.getInitialWord());
            talentCertificationBO.setCTitle(cardPO.getTitle());

            /**
             * 判断人才类别
             */
            int flag = 1;
            String categoryFactor = (String) reqMap.get("category");
            if (!StringUtils.isEmpty(categoryFactor)) {
                String[] categoryArray = talentCertificationBO.getCategory().split(",");
                flag = 0;
                for (String category : categoryArray) {
                    if (category.equals(categoryFactor)) {
                        flag = 1;
                    }
                }
            }
            if (flag == 1) {
                resultList.add(talentCertificationBO);
            }
        }

        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), resultList));
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
        batchCertificatePO.setFileName(fileName);

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

        //都成功了再添加表
        batchCertificateMapper.add(batchCertificatePO);
        return batchCertificateDTO;
    }

    @Override
    @Async
    public ResultVO batchCertificate(HttpSession session,BatchCertificateDTO batchCertificateDTO) throws InterruptedException {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        List<String> names = batchCertificateDTO.getNames();
        List<String> idCards = batchCertificateDTO.getIdCards();
        BatchCertificatePO batchCertificatePO = batchCertificateDTO.getBatchCertificatePO();
        /**
         * 去工具类字典里把人才类别和人才荣誉一一对应
         */
        String talentCategory = batchCertificateDTO.getTalentCategory();
        Long talentHonour = batchCertificateDTO.getTalentHonour();
        String honour = BatchCertificateUtil.convertTalentHonour(talentHonour);
        String talentCategoryString = BatchCertificateUtil.convertTalentCategory(talentCategory);

        Long cardId = batchCertificateDTO.getCardId();
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
                    row[3] = talentCategoryString;
                    row[4] = honour;
                    row[5] = "成功";
                    row[6] = "";
                    successNum++;
                } else if (result.equals(NO_TALENT)) {
                    row[5] = "失败";
                    row[6] = "找不到此用户";
                    failureNum++;
                } else if (result.equals(IN_CERTIFICATE_STATUS)) {
                    row[5] = "失败";
                    row[6] = "已认证或认证中";
                    failureNum++;
                } else {
                    row[5] = "失败";
                    row[6] = "人才状态错误";
                    failureNum++;
                }
                rows[i] = row;
            }

            url = ExcelUtil.save(ExcelUtil.buildExcel("批量认证结果", EXCEL_TITLE_RES, rows),
                    filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getExcelDir(), "batch_certificate_result");

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
        batchCertificateMapper.updateByPrimaryKeySelective(batchCertificatePO);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_CommentUser,"批量认证"
                );
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
        Integer checkIfCertificate = talentMapper.ifInCertificate(openId);
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
        //老BO
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("openId", openId);
        paramsMap.put("status", (byte) 5);
        TalentBO oldTalentBO = talentMapper.findOne(paramsMap);
        if (oldTalentBO == null) {
            return NO_TALENT;
        }
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

        TalentCertificationInfoPO talentCertificationInfoPO = new TalentCertificationInfoPO();
        //学历表
        EducationPO educationPO = new EducationPO();
        educationPO.setCertId(certificationId);
        educationPO.setTalentId(talentId);
        educationPO.setStatus(status);
        educationPO.setIfCertificate((byte) 1);
        //老BO里取值
        if (oldTalentBO.getEducationPOList() != null) {
            EducationPO oldEducationPO = oldTalentBO.getEducationPOList().get(0);
            if (oldEducationPO != null) {
                educationPO.setEducation(oldEducationPO.getEducation());
                educationPO.setSchool(oldEducationPO.getSchool());
                educationPO.setFirstClass(oldEducationPO.getFirstClass());
                educationPO.setMajor(oldEducationPO.getMajor());
                educationPO.setEducPicture(oldEducationPO.getEducPicture());
                educationPO.setGraduateTime(oldEducationPO.getGraduateTime());
                educationPO.setFullTime(oldEducationPO.getFullTime());
            }
        }
        if (educationPO != null && educationPO.getEducation() != null && educationPO.getEducation() != 0) {
            talentCertificationInfoPO.setEducation(educationPO.getEducation().toString());
            educationMapper.insertSelective(educationPO);
        } else {
            talentCertificationInfoPO.setEducation("0");
        }

        //职称表
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setCertId(certificationId);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setStatus(status);
        profTitlePO.setIfCertificate((byte) 1);
        //老BO里取值
        if (oldTalentBO.getProfTitlePOList() != null) {
            ProfTitlePO oldProfTitlePO = oldTalentBO.getProfTitlePOList().get(0);
            if (oldProfTitlePO != null) {
                profTitlePO.setCategory(oldProfTitlePO.getCategory());
                profTitlePO.setInfo(oldProfTitlePO.getInfo());
                profTitlePO.setPicture(oldProfTitlePO.getPicture());
            }
        }
        if (profTitlePO != null && profTitlePO.getCategory() != null && profTitlePO.getCategory() != 0) {
            profTitleMapper.insertSelective(profTitlePO);
            talentCertificationInfoPO.setPtCategory(profTitlePO.getCategory().toString());
        } else {
            talentCertificationInfoPO.setPtCategory("0");
        }


        //职业资格表
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setCertId(certificationId);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setStatus(status);
        profQualityPO.setIfCertificate((byte) 1);
        //老BO里取值
        if (oldTalentBO.getProfQualityPOList() != null) {
            ProfQualityPO oldProfQualityPO = oldTalentBO.getProfQualityPOList().get(0);
            if (oldProfQualityPO != null) {
                profQualityPO.setCategory(oldProfQualityPO.getCategory());
                profQualityPO.setInfo(oldProfQualityPO.getInfo());
                profQualityPO.setPicture(oldProfQualityPO.getPicture());
            }
        }
        if (profQualityPO != null && profQualityPO.getCategory() != null && profQualityPO.getCategory() != 0) {
            profQualityMapper.insertSelective(profQualityPO);
            talentCertificationInfoPO.setPqCategory(profQualityPO.getCategory().toString());
        } else {
            talentCertificationInfoPO.setPqCategory("0");
        }

        //荣誉表
        TalentHonourPO talentHonourPO = new TalentHonourPO();
        talentHonourPO.setCertId(certificationId);
        talentHonourPO.setTalentId(talentId);
        talentHonourPO.setStatus(status);
        talentHonourPO.setIfCertificate((byte) 1);
        //老BO里取值
        if (oldTalentBO.getTalentHonourPOList() != null) {
            TalentHonourPO oldTalentHonourPO = oldTalentBO.getTalentHonourPOList().get(0);
            if (oldTalentHonourPO != null) {
                talentHonourPO.setHonourId(talentHonour);
                talentHonourPO.setHonourPicture(oldTalentHonourPO.getHonourPicture());
                talentHonourPO.setInfo(oldTalentHonourPO.getInfo());
            }
        }
        if (talentHonourPO != null && talentHonourPO.getHonourId() != null && talentHonourPO.getHonourId() != 0) {
            talentHonourMapper.insertSelective(talentHonourPO);
            talentCertificationInfoPO.setHonourId(talentHonourPO.getHonourId().toString());
        } else {
            talentCertificationInfoPO.setHonourId("0");
        }

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
        certApprovalPO.setCertId(certificationId);
        certApprovalPO.setCreateTime(new Date());
        certApprovalPO.setCardId(cardId);
        certApprovalPO.setUpdateTime(new Date());
        certApprovalPO.setResult((byte) 1);
        certApprovalMapper.insertSelective(certApprovalPO);

        /**
         * 更新talent表
         */
        talentPO.setCardId(cardId);
        talentMapper.updateByPrimaryKeySelective(talentPO);
        /**
         * 新增tci表
         */
        talentCertificationInfoPO.setTalentId((talentId));
        talentCertificationInfoPO.setTalentCategory(talentPO.getCategory());
        talentCertificationInfoMapper.insertSelective(talentCertificationInfoPO);
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

    @Override
    public ResultVO findBatchCertificate(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<BatchCertificatePO> batchCertificatePOList = batchCertificateMapper.findBatchCertificate(hashMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), batchCertificatePOList));
    }

    @Override
    public ResultVO sendMessage(HttpSession session,String openId) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        TalentPO talentPO = this.talentMapper.selectByOpenId(openId);
        this.sendMessage(talentPO);
        /**
         * ly注释：后面的代码没有null的处理，所以我也没做
         * */
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_SendMessage,"给%s用户发送信息",
                talentPO.getName());
        return new ResultVO(1000);
    }
}
