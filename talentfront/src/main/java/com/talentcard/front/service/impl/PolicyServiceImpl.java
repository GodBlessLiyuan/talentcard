package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.PolicyApplyMapper;
import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IPolicyService;
import com.talentcard.front.vo.PolicyAppliesVO;
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

    @Override
    public ResultVO applies(Long tid) {
        List<PolicyApplyPO> pos = policyApplyMapper.queryByTalentId(tid);
        return new ResultVO<>(1000, PolicyAppliesVO.convert(pos));
    }
}
