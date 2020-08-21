package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.OpSendmessagePO;
import com.talentcard.common.pojo.PoStatisticsPO;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.ExcelExportUtil;
import com.talentcard.common.utils.PageQueryUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IComplianceService;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.vo.ComplianceNumVO;
import com.talentcard.web.vo.ComplianceVO;
import com.talentcard.web.vo.PushRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:32 2020/8/20
 * @ Description：符合人数详情查询服务类
 * @ Modified By：
 * @ Version:     1.0*/

@Service
public class ComplianceService implements IComplianceService {

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

    private static final String[] EXPORT_TITLES={"序号","政策名称","政策编号","申请人","申请状态","银行卡号","开户行名","持卡人","政策资金（元）","申请时间"};

    @Override
    public ResultVO queryNum(Map<String, Object> reqData) {
        PoStatisticsPO po = poStatisticsMapper.selectByPrimaryKey(Long.parseLong(reqData.get("pid").toString()));
        return new ResultVO(1000, ComplianceNumVO.convert(po));
    }




    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<PoComplianceBO> page = PageQueryUtil.startPage(reqData);
        List<PoComplianceBO> bos = complianceMapper.pageQuery(reqData);
        //遍历取出人才政策id查出政策权益名称和政策权益编号和政策资金放入对应的BO对象中
        for (PoComplianceBO poComplianceBO:
        bos) {
            PolicyPO policyPo=policyMapper.selectByPrimaryKey(poComplianceBO.getPolicyId());
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
        for (PoComplianceBO poComplianceBO:
                bos) {
            PolicyPO policyPo=policyMapper.selectByPrimaryKey(poComplianceBO.getPolicyId());
            poComplianceBO.setPolicyName(policyPo.getName());
            poComplianceBO.setPolicyNum(policyPo.getNum());
            poComplianceBO.setPolicyFunds(policyPo.getFunds());
            //下面进行银行卡信息查询用于导出
            //首先根据人才查询政策申请表，查出两张表已政策申请id关联机型查询银行卡信息
                PoComplianceBO poComplianceBO1=policyApplyMapper.queryBankByTalentId(poComplianceBO.getTalentId());
                poComplianceBO.setBankNum(poComplianceBO1.getBankNum());
                poComplianceBO.setBankName(poComplianceBO1.getBankName());
        }
        //构造导出Excel文件名称
        Date dt=new Date();
        DateFormat df=new SimpleDateFormat("yyyyMMdd");
        String formatdate=df.format(dt);
        String fileName=bos.get(0).getPolicyName()+formatdate;
        //生成Excel表格
        ExcelExportUtil.exportExcel(fileName,null, EXPORT_TITLES, this.buildExcelContents(bos), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<PoComplianceBO> bos) {
        if(bos==null||bos.size()==0){
            return null;
        }
        String[][] contents=new String[bos.size()][EXPORT_TITLES.length];
        int num=0;
        for(PoComplianceBO bo:bos){
            //增加序号
            int order=1;
            contents[num][0]=Integer.toString(order);
            contents[num][1]=bo.getPolicyName();
            contents[num][2]=bo.getPolicyNum();
            contents[num][3]=bo.getName();
            Byte status = bo.getStatus();
            String statusString="";
            if("0".equals(status)){
                statusString ="未申请";
            }else if("1".equals(status)){
                statusString ="已通过";
            }
            else if("2".equals(status)){
                statusString ="已驳回";
            }
            else if("3".equals(status)){
                statusString ="待审批";
            }
            contents[num][4]=statusString;
            contents[num][5]=bo.getBankNum();
            contents[num][6]=bo.getBankName();
            contents[num][7]=bo.getName();
            contents[num][8]=Integer.toString(bo.getPolicyFunds());
            contents[num][9]=bo.getApplyTime()+"";
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
    public ResultVO push(Map<String, Object> reqData) {
        List<PoComplianceBO> bos = complianceMapper.pageQuery(reqData);
        //遍历取出人才政策id查出政策权益名称和政策权益编号和政策资金放入对应的BO对象中
        for (PoComplianceBO poComplianceBO:
                bos) {
            PolicyPO policyPo=policyMapper.selectByPrimaryKey(poComplianceBO.getPolicyId());
            poComplianceBO.setPolicyName(policyPo.getName());
            poComplianceBO.setPolicyNum(policyPo.getNum());
            poComplianceBO.setPolicyFunds(policyPo.getFunds());
            Long talentId=poComplianceBO.getTalentId();
            TalentPO talentPO=talentMapper.selectByPrimaryKey(talentId);
            String openId=talentPO.getOpenId();
            wxOfficalAccountService.messToNotApply(openId,poComplianceBO.getPolicyName());
        }
        return new ResultVO(1000);
    }


}
