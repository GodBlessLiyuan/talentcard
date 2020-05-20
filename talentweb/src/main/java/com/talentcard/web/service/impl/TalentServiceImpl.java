package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.ExcelUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.TalentDetailVO;
import com.talentcard.web.vo.TalentVO;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    @Value("${file.rootDir}")
    private String rootDir;
    @Value("${file.projectDir}")
    private String projectDir;
    @Value("${file.excelDir}")
    private String excelDir;

    private static final String[] EXCEL_TITLE = {"姓名", "证件号码"};
    private static final String[] EXCEL_TITLE_RES = {"姓名", "证件号码", "人才卡", "人才类别", "人才荣誉", "认证结果", "说明"};

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
    public ResultVO batch(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (null == fileName) {
            // 文件内表头信息或文件格式有误，请下载模板文件检查后重新上传！
            return new ResultVO(1100);
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (!".xlsx".equals(suffix)) {
            // 文件内表头信息或文件格式有误，请下载模板文件检查后重新上传！
            return new ResultVO(1100);
        }

        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);
            if (!ExcelUtil.isNormTitle(sheet.getRow(1), EXCEL_TITLE)) {
                // 文件内表头信息或文件格式有误，请下载模板文件检查后重新上传！
                return new ResultVO(1100);
            }

            List<String> names = new LinkedList<>();
            List<String> idCards = new LinkedList<>();
            for (int r = 2; r <= sheet.getLastRowNum(); r++) {
                XSSFRow row = sheet.getRow(r);
                names.add(ExcelUtil.getStringValue(row, 0));
                idCards.add(ExcelUtil.getStringValue(row, 1));
            }

            // TODO: 业务逻辑


            String[][] rows = new String[names.size()][];
            for (int i = 0; i < names.size(); i++) {
                String[] row = new String[EXCEL_TITLE_RES.length];
                row[0] = names.get(i);
                row[1] = idCards.get(i);
                row[2] = String.valueOf(3);
                row[3] = String.valueOf(4);
                row[4] = String.valueOf(5);
                row[5] = String.valueOf(6);
                row[6] = String.valueOf(7);
                rows[i] = row;
            }

            String url = ExcelUtil.save(ExcelUtil.buildExcel("批量认证结果", EXCEL_TITLE_RES, rows),
                    rootDir, projectDir, excelDir, "批量认证结果");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(1100);
        }

        return new ResultVO(1000);
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
        cardMapper.updateByPrimaryKeySelective(newCardPO);
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
}
