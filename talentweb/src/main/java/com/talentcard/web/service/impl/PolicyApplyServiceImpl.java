package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.mapper.PolicyApplyMapper;
import com.talentcard.common.mapper.PolicyApprovalMapper;
import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.pojo.PolicyApprovalPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IPolicyApplyService;
import com.talentcard.web.vo.PolicyApplyDetailVO;
import com.talentcard.web.vo.PolicyApplyVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private static final String[] EXPORT_TITLES = {"序号", "政策名称", "政策编号", "申请人", "申请时间", "状态", "银行卡号",
            "开户行名", "持卡人"};

    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> reqMap) {
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

        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long paid) {
        PolicyApplyBO bo = policyApplyMapper.queryDetail(paid);
        if (null == bo) {
            // 数据不存在
            return new ResultVO(1001);
        }
        return new ResultVO<>(1000, PolicyApplyDetailVO.convert(bo));
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
            content[4] = DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD_HMS);
            content[5] = bo.getStatus() == 1 ? "已通过" : bo.getStatus() == 2 ? "已驳回" : "待审批";
            content[6] = bo.getBankNum();
            content[7] = bo.getBankName();
            if (null != bo.getBankNum() && !"".equals(bo.getBankNum())) {
                content[8] = bo.getTalentName();
            }
            contents[num++] = content;
        }

        return contents;
    }
}
