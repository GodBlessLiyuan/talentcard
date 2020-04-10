package com.talentcard.front.service.impl;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IPolicyService;
import com.talentcard.front.vo.PolicyAppliesVO;
import com.talentcard.front.vo.PolicyApplyDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 16:55
 * @description: 政策权益
 * @version: 1.0
 */
@Service
public class PolicyServiceImpl implements IPolicyService {
    @Resource
    private PolicyApplyMapper policyApplyMapper;
    @Resource
    private TalentMapper talentMapper;
    @Resource
    private PolicyMapper policyMapper;
    @Resource
    private EducationMapper educationMapper;
    @Resource
    private ProfTitleMapper profTitleMapper;
    @Resource
    private ProfQualityMapper profQualityMapper;

    @Override
    public ResultVO policies(Long talentId) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (null == talentPO || talentPO.getStatus() == 2) {
            return new ResultVO(1000);
        }

        List<Integer> existEduc = educationMapper.queryNameByTalentId(talentId);
        List<Integer> existTitle = profTitleMapper.queryNameByTalentId(talentId);
        List<Integer> existQuality = profQualityMapper.queryNameByTalentId(talentId);

        List<PolicyPO> pos = policyMapper.queryByDr((byte) 1);
        List<PolicyPO> showPOs = new ArrayList<>();
        for (PolicyPO po : pos) {
            String[] educ = po.getEducations().split(",");
            String[] title = po.getTitles().split(",");
            String[] quality = po.getQualities().split(",");
        }

        return null;
    }

    @Override
    public ResultVO apply(Long tid, Long pid) {
        return null;
    }

    @Override
    public ResultVO applies(Long tid) {
        List<PolicyApplyPO> pos = policyApplyMapper.queryByTalentId(tid);
        return new ResultVO<>(1000, PolicyAppliesVO.convert(pos));
    }

    @Override
    public ResultVO detail(Long paid) {
        PolicyApplyBO bo = policyApplyMapper.queryDetail(paid);
        if (null == bo) {
            return new ResultVO(2000);
        }

        return new ResultVO<>(1000, PolicyApplyDetailVO.convert(bo));
    }
}
