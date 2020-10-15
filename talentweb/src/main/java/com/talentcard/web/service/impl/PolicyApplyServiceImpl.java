package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.netflix.discovery.converters.Auto;
import com.talentcard.common.bo.ApplyNumCountBO;
import com.talentcard.common.bo.HavingApprovePolicyBO;
import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.constant.TrackConstant;
import com.talentcard.common.dto.ApplyNumCountDTO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.rabbit.RabbitUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.IBestPolicyToTalentService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IPolicyApplyService;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.PolicyApplyDetailVO;
import com.talentcard.web.vo.PolicyApplyVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String[] EXPORT_TITLES = {"序号", "政策名称", "政策编号", "申请人", "申请状态", "银行卡号",
            "开户行名", "持卡人", "政策资金（元）", "申请时间"};
    @Autowired
    private ILogService logService;
    @Autowired
    PolicyMapper policyMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    private IBestPolicyToTalentService iBestPolicyToTalentService;
    @Autowired
    private ITalentService iTalentService;

    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> reqMap) {
        Page<PolicyApplyBO> page = PageHelper.startPage(pageNum, pageSize);
        List<PolicyApplyBO> bos = policyApplyMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), PolicyApplyVO.convert(bos)));
    }

    @Override
    public void export(HashMap<String, Object> reqMap, HttpServletResponse res) {
        List<PolicyApplyBO> bos = policyApplyMapper.query(reqMap);
        String[][] content = buildExcelContents(bos);
        //生成Excel表格
        ExportUtil.exportExcel(null, EXPORT_TITLES, content, res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO approval(HttpSession session, Long paid, Byte status, String opinion, BigDecimal actualFunds) {
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
        if (applyPO.getStatus() == null || applyPO.getStatus() != 3) {
            return new ResultVO(2743, "当前政策状态不对！");
        }
        applyPO.setStatus(status);

        PolicyApprovalPO po = new PolicyApprovalPO();
        po.setPaId(paid);
        po.setCreateTime(new Date());
        po.setType((byte) 2);
        po.setUserId((Long) session.getAttribute("userId"));
        po.setUsername((String) session.getAttribute("username"));
        po.setResult(status);
        po.setOpinion(opinion);
        po.setActualFunds(actualFunds);
        po.setUpdateTime(new Date());
        policyApprovalMapper.add(po);

        applyPO.setPaId(po.getPaId());
        applyPO.setActualFunds(actualFunds);
        applyPO.setPolicyApprovalId(po.getApprovalId());
        policyApplyMapper.updateByPrimaryKey(applyPO);

        /**
         * 更新政策适配用户表
         */
        iBestPolicyToTalentService.updatePOCompliance(applyPO.getTalentId(), applyPO.getPolicyId(), status);

        /**
         * 政策修改后，需要重新匹配用户信息
         */
        iBestPolicyToTalentService.asynBestPolicyForTalent(applyPO.getTalentId());

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


        this.iTalentService.clearRedisCache(talentPO.getOpenId());

        /*区块链埋点*/
        String talentName = talentPO.getName();
        String policyName = applyPO.getPolicyName();
        if (1 == status) {
            String eventLog = talentName + "申请政策\"" + policyName + "\",已通过审批";
            Byte eventStatus = TrackConstant.POLICY_PASS;
            RabbitUtil.sendTrackMsg(TrackConstant.POLICY_TRACK, eventStatus, eventLog, true);
        } else if (2 == status) {
            String eventLog = talentName + "申请政策\"" + policyName + "\",被驳回";
            Byte eventStatus = TrackConstant.POLICY_REJECT;
            RabbitUtil.sendTrackMsg(TrackConstant.POLICY_TRACK, eventStatus, eventLog, true);
        }

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
    public ResultVO count(HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        UserPO userPO = userMapper.selectByPrimaryKey(userId);
        if (userPO == null) {
            return new ResultVO(2741, "查无此角色！");
        }
        RolePO rolePO = roleMapper.selectByPrimaryKey(userPO.getRoleId());
        if (rolePO == null) {
            return new ResultVO(2741, "查无此角色！");
        }
        Long roleId = null;
        //政策角色
        if (rolePO.getRoleType() == 2) {
            roleId = rolePO.getRoleId();
        }
        Long count = policyApplyMapper.countWaitApproval(roleId);
        return new ResultVO<>(1000, count);
    }

    @Override
    public ResultVO applyNumCount(ApplyNumCountDTO applyNumCountDTO) {
        ApplyNumCountBO applyNumCountBO = policyApplyMapper.applyNumCount(applyNumCountDTO);
        //算总数
        if (applyNumCountBO == null) {
            return new ResultVO(1000, new ApplyNumCountBO());
        }
        Integer allNum = applyNumCountBO.getAgreeNum() +
                applyNumCountBO.getRejectNum() + applyNumCountBO.getWaitApprovalNum();
        applyNumCountBO.setAllNum(allNum);
        return new ResultVO(1000, applyNumCountBO);
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
            String[] content = new String[10];
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

            String funds = "";
            if (bo.getStatus() != null && bo.getStatus() == 1) {

                Map<String, Object> map = new HashMap<>(3);
                map.put("talentId", bo.getTalentId());
                map.put("policyId", bo.getPolicyId());
                map.put("status", 1);
                List<PolicyApplyPO> pos = policyApplyMapper.selectByMap(map);
                if (pos != null && pos.size() > 0) {
                    DecimalFormat df2 = new DecimalFormat("#.00");
                    for (PolicyApplyPO po : pos) {
                        BigDecimal funds1 = po.getActualFunds();
                        try {
                            funds = df2.format(funds1);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    content[8] = funds;
                }
            }

            if (StringUtils.isEmpty(funds)) {
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

    @Override
    public ResultVO cancel(HttpSession httpSession, Long paId, String opinion) {
        PolicyApplyPO policyApplyPO = policyApplyMapper.selectByPrimaryKey(paId);
        if (policyApplyPO == null) {
            return new ResultVO(2742, "查无此政策审批！");
        }
        if (policyApplyPO.getStatus() == null || policyApplyPO.getStatus() == 3) {
            return new ResultVO(2743, "当前政策状态不对！");
        }
        policyApplyPO.setStatus((byte) 3);
        PolicyApprovalPO policyApprovalPO = new PolicyApprovalPO();
        policyApprovalPO.setCreateTime(new Date());
        policyApprovalPO.setPaId(paId);
        policyApprovalPO.setResult((byte) 3);
        policyApprovalPO.setType((byte) 3);
        policyApprovalPO.setOpinion(opinion);
        UserPO userPO = userMapper.selectByPrimaryKey((Long) httpSession.getAttribute("userId"));
        if (userPO != null) {
            policyApprovalPO.setUserId(userPO.getUserId());
            policyApprovalPO.setUsername(userPO.getUsername());
        }
        policyApprovalPO.setUpdateTime(new Date());
        policyApprovalMapper.add(policyApprovalPO);
        policyApplyPO.setPolicyApprovalId(policyApprovalPO.getApprovalId());
        policyApplyMapper.updateByPrimaryKey(policyApplyPO);

        /**
         * 更新政策适配用户表
         */
        iBestPolicyToTalentService.updatePOCompliance(policyApplyPO.getTalentId(), policyApplyPO.getPolicyId(), policyApplyPO.getStatus());

        /**
         * 用模版推送消息
         */
        TalentPO talentPO = talentMapper.selectByPrimaryKey(policyApplyPO.getTalentId());
        if (talentPO == null) {
            return new ResultVO(2500, "查无此人！");
        }
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        messageDTO.setFirst("您好，您申请的“" + policyApplyPO.getPolicyName() + "”需要重新审批。");
        //信息类型
        messageDTO.setKeyword1("政策权益");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "操作原因：" + opinion;
        messageDTO.setRemark(remark);
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        String detail = "撤销人才\"%s\"的政策申请“" + policyApplyPO.getPolicyName() + "”";
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentPolicyManager,
                OpsRecordMenuConstant.S_PolicyApply, detail, talentPO.getName());

        this.iTalentService.clearRedisCache(talentPO.getOpenId());

        return new ResultVO(1000);
    }
}
