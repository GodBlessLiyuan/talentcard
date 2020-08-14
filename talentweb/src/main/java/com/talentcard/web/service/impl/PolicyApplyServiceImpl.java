package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.HavingApprovePolicyBO;
import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IPolicyApplyService;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.PolicyApplyDetailVO;
import com.talentcard.web.vo.PolicyApplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 20:33
 * @description: 政策审批
 * @version: 1.0
 */
@Service
public class PolicyApplyServiceImpl implements IPolicyApplyService {
    @Resource
    private PolicyApplyMapper policyApplyMapper;
    @Resource
    private PolicyApprovalMapper policyApprovalMapper;
    @Resource
    private TalentMapper talentMapper;
    @Autowired
    private RoleMapper roleMapper;
    private static final String[] EXPORT_TITLES = {"序号", "政策名称", "政策编号", "申请人", "申请时间", "状态", "银行卡号",
            "开户行名", "持卡人"};
    @Autowired
    private ILogService logService;
    @Autowired
    PolicyMapper policyMapper;

    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> reqMap, Long roleId) {
        RolePO rolePO = roleMapper.selectByPrimaryKey(roleId);
        if (rolePO == null) {
            return new ResultVO(2741, "无此角色！");
        }
        reqMap.put("roleType", rolePO.getRoleType());
        reqMap.put("roleId", roleId);
        Page<PolicyApplyBO> page = PageHelper.startPage(pageNum, pageSize);
        List<PolicyApplyBO> bos = policyApplyMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), PolicyApplyVO.convert(bos)));
    }

    @Override
    public ResultVO export(HashMap<String, Object> reqMap, HttpServletResponse res) {
        List<PolicyApplyBO> bos = policyApplyMapper.query(reqMap);

        //生成Excel表格
        ExportUtil.exportExcel(null, EXPORT_TITLES, this.buildExcelContents(bos), res);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO approval(HttpSession session, Long paid, Byte status, String opinion) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        PolicyApplyPO applyPO = policyApplyMapper.selectByPrimaryKey(paid);
        if (null == applyPO) {
            return new ResultVO(1001);
        }
        applyPO.setStatus(status);
        policyApplyMapper.updateByPrimaryKey(applyPO);

        PolicyApprovalPO po = new PolicyApprovalPO();
        po.setPaId(paid);
        po.setCreateTime(new Date());
        po.setType((byte) 2);
        po.setUserId((Long) session.getAttribute("userId"));
        po.setUsername((String) session.getAttribute("username"));
        po.setResult(status);
        po.setOpinion(opinion);
        policyApprovalMapper.insert(po);


        //用消息模板推送微信消息
        TalentPO talentPO = talentMapper.selectByPrimaryKey(applyPO.getTalentId());
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String first = "您申请的" + applyPO.getPolicyName() + "审批结果如下";
        messageDTO.setFirst(first);
        if (status == 1) {
            messageDTO.setKeyword1("通过");
        } else {
            messageDTO.setKeyword1("未通过");
        }

        messageDTO.setKeyword2("审批详情可在“我的申请”页面查看");
        //模版编号
        messageDTO.setTemplateId(3);
        //结束
        messageDTO.setRemark("点击查看详情");
        messageDTO.setUrl(WebParameterUtil.getMyApplicationUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyApply,
                "审批用户\"%s\"的政策申请", talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long paid) {
        PolicyApplyBO bo = policyApplyMapper.queryDetail(paid);
        if (null == bo) {
            // 数据不存在
            return new ResultVO(1001);
        }
        Long talentId = bo.getTalentId();
        PolicyApplyDetailVO policyApplyDetailVO = PolicyApplyDetailVO.convert(bo);
        //已申请政策
        List<HavingApprovePolicyBO> havingApprovePolicyBOList = policyMapper.findHavingApprovePolicy(talentId);
        policyApplyDetailVO.setHavingApprovePolicyBOList(havingApprovePolicyBOList);
        return new ResultVO<>(1000, policyApplyDetailVO);
    }

    @Override
    public ResultVO count() {
        Long count = policyApplyMapper.countByStatus((byte) 3);
        return new ResultVO<>(1000, count);
    }

    /**
     * 根据 bos 构建 导出内容
     *
     * @param bos
     * @return
     */
    private String[][] buildExcelContents(List<PolicyApplyBO> bos) {
        String[][] contents = new String[bos.size()][];
        int num = 0;
        for (PolicyApplyBO bo : bos) {
            String[] content = new String[9];
            content[0] = String.valueOf(num + 1);
            content[1] = bo.getPolicyName();
            content[2] = bo.getNum();
            content[3] = bo.getTalentName();
            content[4] = bo.getStatus() == 1 ? "已通过" : bo.getStatus() == 2 ? "已驳回" : "待审批";
            content[5] = bo.getBankNum();
            content[6] = bo.getBankName();
            if (null != bo.getBankNum() && !"".equals(bo.getBankNum())) {
                content[7] = bo.getTalentName();
            }
            if (bo.getStatus() != null && bo.getStatus() == 1 && bo.getActualFunds() != null) {
                content[8] = bo.getActualFunds().toString();
            } else {
                PolicyPO policyPO = policyMapper.selectByPrimaryKey(bo.getPolicyId());
                if (policyPO != null) {
                    content[8] = policyPO.getFunds().toString();
                }
            }
            content[9] = DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD_HMS);
            contents[num++] = content;
        }

        return contents;
    }
}
