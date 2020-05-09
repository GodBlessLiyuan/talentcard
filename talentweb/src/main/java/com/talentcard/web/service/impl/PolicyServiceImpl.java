package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.PolicyMapper;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.IPolicyService;
import com.talentcard.web.vo.PolicyDetailVO;
import com.talentcard.web.vo.PolicyVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
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
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PolicyPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PolicyPO> pos = policyMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), PolicyVO.convert(pos)));
    }

    @Override
    public ResultVO insert(HttpSession session, PolicyDTO dto) {
        PolicyPO existPO = policyMapper.queryByNum(dto.getNum());
        if (null != existPO) {
            // 编号不能重复
            return new ResultVO(1001);
        }

        PolicyPO po = buildPOByDTO(new PolicyPO(), dto);
        po.setUserId((Long) session.getAttribute("userId"));
        po.setCreateTime(new Date());
        po.setDr((byte) 1);

        policyMapper.insert(po);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO update(HttpSession session, PolicyDTO dto) {
        PolicyPO po = policyMapper.selectByPrimaryKey(dto.getPid());
        if (null == po) {
            // 数据已被删除
            return new ResultVO(1001);
        }
        policyMapper.updateByPrimaryKey(buildPOByDTO(po, dto));
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(Long pid) {
        policyMapper.deleteByPrimaryKey(pid);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long pid) {
        PolicyPO po = policyMapper.selectByPrimaryKey(pid);
        if (null == po) {
            // 数据已被删除
            return new ResultVO(1001);
        }

        return new ResultVO<>(1000, PolicyDetailVO.convert(po));
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private PolicyPO buildPOByDTO(PolicyPO po, PolicyDTO dto) {
        po.setName(dto.getName());
        po.setNum(dto.getNum());
        po.setDescription(dto.getDesc());
        po.setCards(String.join(",", dto.getCardIds()));
        po.setCategories(String.join(",", dto.getCategoryIds()));
        po.setEducations(String.join(",", dto.getEducIds()));
        po.setTitles(String.join(",", dto.getTitleIds()));
        po.setQualities(String.join(",", dto.getQualityIds()));
        po.setTimes(dto.getTimes());
        po.setBank(dto.getBank());
        po.setAnnex(dto.getAnnex());
        po.setApply(dto.getApply());
        po.setRate(dto.getRate());
        po.setUnit(dto.getUnit());

        return po;
    }
}
