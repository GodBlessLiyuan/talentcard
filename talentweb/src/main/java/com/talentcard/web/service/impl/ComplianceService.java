package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExcelExportUtil;
import com.talentcard.common.utils.PageQueryUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.IComplianceService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.vo.CerIdVO;
import com.talentcard.web.vo.ComplianceNumVO;
import com.talentcard.web.vo.ComplianceVO;
import com.talentcard.web.vo.PushRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.talentcard.common.utils.DateUtil.YMD;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:32 2020/8/20
 * @ Description：符合人数详情查询服务类
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Service
public class ComplianceService implements IComplianceService {
    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ComplianceService.class);
    @Autowired
    private ILogService logService;
    @Autowired
    private PoComplianceMapper complianceMapper;
    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private PolicyApplyMapper policyApplyMapper;
    @Autowired
    private PoStatisticsMapper poStatisticsMapper;
    @Autowired
    private OpSendmessageMapper opSendmessageMapper;
    @Autowired
    private OpMessRecordMapper opMessRecordMapper;
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private IWxOfficalAccountService wxOfficalAccountService;

    private static final String[] EXPORT_TITLES = {"序号", "政策名称", "政策编号", "申请人", "申请状态", "银行卡号", "开户行名", "持卡人", "政策资金（元）", "申请时间"};

    @Override
    public ResultVO queryNum(Map<String, Object> reqData) {
        PoStatisticsPO po = poStatisticsMapper.selectByPrimaryKey(Long.parseLong(reqData.get("pid").toString()));
        if (po == null) {
            return new ResultVO(1001);//未查到
        }
        return new ResultVO(1000, ComplianceNumVO.convert(po));
    }

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<PoComplianceBO> page = PageQueryUtil.startPage(reqData);
        List<PoComplianceBO> bos = complianceMapper.pageQuery(reqData);
        //遍历取出人才政策id查出政策权益名称和政策权益编号和政策资金放入对应的BO对象中
        for (PoComplianceBO poComplianceBO :
                bos) {
            PolicyPO policyPo = policyMapper.selectByPrimaryKey(poComplianceBO.getPolicyId());
            poComplianceBO.setPolicyName(policyPo.getName());
            poComplianceBO.setPolicyNum(policyPo.getNum());
            poComplianceBO.setPolicyFunds(policyPo.getFunds());
        }
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), ComplianceVO.convert(bos)));
    }

    @Override
    public ResultVO exportExcel(Map<String, Object> reqData, HttpServletResponse response) {
        List<PoComplianceBO> bos = complianceMapper.pageQuery(reqData);
        //遍历取出人才政策id查出政策权益名称和政策权益编号和政策资金放入对应的BO对象中
        for (PoComplianceBO poComplianceBO :
                bos) {
            PolicyPO policyPo = policyMapper.selectByPrimaryKey(poComplianceBO.getPolicyId());
            poComplianceBO.setPolicyName(policyPo.getName());
            poComplianceBO.setPolicyNum(policyPo.getNum());
            poComplianceBO.setPolicyFunds(policyPo.getFunds());
            //下面进行银行卡信息查询用于导出
            //首先根据人才查询政策申请表，查出两张表已政策申请id关联机型查询银行卡信息
            PoComplianceBO poComplianceBO1 = policyApplyMapper.queryBankByTalentId(poComplianceBO.getTalentId());
            poComplianceBO.setBankNum(poComplianceBO1.getBankNum());
            poComplianceBO.setBankName(poComplianceBO1.getBankName());
        }
        //构造导出Excel文件名称
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formatdate = df.format(dt);
        String fileName = bos.get(0).getPolicyName() + formatdate;
        //生成Excel表格
        ExcelExportUtil.exportExcel(fileName, null, EXPORT_TITLES, this.buildExcelContents(bos), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<PoComplianceBO> bos) {
        if (bos == null || bos.size() == 0) {
            return null;
        }
        String[][] contents = new String[bos.size()][EXPORT_TITLES.length];
        int num = 0;
        for (PoComplianceBO bo : bos) {
            //增加序号
            int order = 1;
            contents[num][0] = Integer.toString(order);
            contents[num][1] = bo.getPolicyName();
            contents[num][2] = bo.getPolicyNum();
            contents[num][3] = bo.getName();
            Byte status = bo.getStatus();
            String statusString = "";
            if ("0".equals(status)) {
                statusString = "未申请";
            } else if ("1".equals(status)) {
                statusString = "已通过";
            } else if ("2".equals(status)) {
                statusString = "已驳回";
            } else if ("3".equals(status)) {
                statusString = "待审批";
            }
            contents[num][4] = statusString;
            contents[num][5] = bo.getBankNum();
            contents[num][6] = bo.getBankName();
            contents[num][7] = bo.getName();
            contents[num][8] = Integer.toString(bo.getPolicyFunds());
            if (bo.getApplyTime() != null) {
                contents[num][9] = DateUtil.date2Str(bo.getApplyTime(), YMD);
            }
            num++;
            order++;
        }
        return contents;
    }


    @Override
    public ResultVO pushRecordQuery(Long pid) {
        List<OpSendmessagePO> pos = opSendmessageMapper.pushRecordQuery(pid);
        return new ResultVO(1000, PushRecordVO.convert(pos));
    }

    @Override
    @Transactional
    public ResultVO push(HttpSession session, Map<String, Object> reqData) {
        List<PoComplianceBO> bos = complianceMapper.pageQuery(reqData);
        //遍历取出人才政策id查出政策权益名称和政策权益编号和政策资金放入对应的BO对象中
        //计算成功和失败次数
        int successNum = 0;
        int failureNum = 0;
        //将推送的记录下来取到插入统计表后的数据获得SendId在进行插入，不然有关联关系插入不进去
        List<OpMessRecordPO> opMessRecordPOList = new ArrayList<>();
        for (PoComplianceBO poComplianceBO :
                bos) {
            PolicyPO policyPo = policyMapper.selectByPrimaryKey(poComplianceBO.getPolicyId());
            poComplianceBO.setPolicyName(policyPo.getName());
            poComplianceBO.setPolicyNum(policyPo.getNum());
            poComplianceBO.setPolicyFunds(policyPo.getFunds());
            Long talentId = poComplianceBO.getTalentId();
            TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
            String openId = talentPO.getOpenId();
            //推送
            int statusCode = wxOfficalAccountService.messToNotApply(openId, poComplianceBO.getPolicyName());
            //计算成功和失败次数
            if (statusCode == 0) {
                successNum++;
            } else {
                failureNum++;
            }
            log.debug("该人才：" + poComplianceBO.getName() + "推送消息后返回的状态码为" + statusCode);
            //插入一键推送消息记录表
            OpMessRecordPO opMessRecordPO = new OpMessRecordPO();
            opMessRecordPO.setTalentId(talentId);
            opMessRecordPO.setOpenId(openId);
            opMessRecordPO.setStatus(statusCode);
            opMessRecordPOList.add(opMessRecordPO);
        }
        //插入推送消息汇总表
        OpSendmessagePO opSendmessagePO = new OpSendmessagePO();
        opSendmessagePO.setPolicyId(Long.parseLong(reqData.get("pid").toString()));
        opSendmessagePO.setUserId((Long) session.getAttribute("userId"));
        opSendmessagePO.setUsername((String) session.getAttribute("username"));
        opSendmessagePO.setSuccess(new Long(successNum));
        opSendmessagePO.setFailure(new Long(failureNum));
        opSendmessagePO.setCreateTime(new Date());
        opSendmessageMapper.insert(opSendmessagePO);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "将本次推送记录插入到记录汇总表中\"%s\"", opSendmessagePO.toString());
        //将返回的一键推送id插入到一键推送消息记录表中
        for (OpMessRecordPO opMessRecordPO : opMessRecordPOList) {
            //将插入消息汇总表的SendId取到进行关联插入到消息记录表
            opMessRecordPO.setSendId(opSendmessagePO.getSendId());
            opMessRecordMapper.insert(opMessRecordPO);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                    , "插入到历史记录表中进行关联\"%s\"", opMessRecordPO.toString());
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryCertId(Map<String, Object> reqData) {
        CertApprovalPassRecordPO po = complianceMapper.queryCertId(reqData);
        return new ResultVO(1000, CerIdVO.convert(po));
    }

}
