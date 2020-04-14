package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.PolicyMapper;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.utils.DTPageInfo;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.IPolicyService;
import com.talentcard.web.vo.PolicyVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:22
 * @description: 人才政策管理
 * @version: 1.0
 */
@Service
public class PolicyServiceImpl implements IPolicyService {
    @Resource
    private PolicyMapper policyMapper;

    @Override
    public DTPageInfo<PolicyVO> query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PolicyPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PolicyPO> pos = policyMapper.query(reqMap);
        return null;
    }

    @Override
    public ResultVO insert(HttpSession session, PolicyDTO dto) {
        return null;
    }

    @Override
    public ResultVO update(HttpSession session, PolicyDTO dto) {
        return null;
    }

    @Override
    public ResultVO delete(Long pid) {
        return null;
    }

    @Override
    public ResultVO detail(Long pid) {
        return null;
    }
}
