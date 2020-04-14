package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.PolicyApplyMapper;
import com.talentcard.common.utils.DTPageInfo;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IPolicyApplyService;
import com.talentcard.web.vo.PolicyVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
    
    @Value("${file.publicPath}")
    private String publicPath;

    @Override
    public DTPageInfo<PolicyVO> query(int pageNum, int pageSize, HashMap<String, Object> reqMap) {
        return null;
    }

    @Override
    public ResultVO export(HashMap<String, Object> reqMap) {
        return null;
    }

    @Override
    public ResultVO approval(HttpSession session, Long paid, Byte status, String opinion) {
        return null;
    }

    @Override
    public ResultVO detail(Long paid) {
        return null;
    }
}
