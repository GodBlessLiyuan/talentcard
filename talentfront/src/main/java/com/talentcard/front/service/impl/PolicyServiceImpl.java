package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.PolicyMapper;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IPolicyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private PolicyMapper policyMapper;

    @Override
    public ResultVO applies(Long tid) {
        List<PolicyPO> pos = policyMapper.queryByTalentId(tid);
        return null;
    }
}
