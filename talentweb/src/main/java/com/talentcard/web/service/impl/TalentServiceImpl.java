package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.vo.TalentDetailVO;
import com.talentcard.web.vo.TalentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:15
 * @description: 人才管理
 * @version: 1.0
 */
@Service
public class TalentServiceImpl implements ITalentService {
    @Resource
    private TalentMapper talentMapper;

    @Override
    public PageInfoVO<TalentVO> query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TalentBO> bos = talentMapper.query(reqMap);
        return new PageInfoVO<>(page.getTotal(), TalentVO.convert(bos));
    }

    @Override
    public ResultVO detail(Long tid) {
        TalentBO bo = talentMapper.queryDetail(tid);
        if (null == bo) {
            // 数据不存在
            return new ResultVO(1001);
        }

        return new ResultVO<>(1000, TalentDetailVO.convert(bo));
    }

    @Override
    public PageInfoVO<TalentVO> queryCert(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TalentBO> bos = talentMapper.queryCert(reqMap);
        // 当状态1和2同时存在时，返回状态为1的数据
        Map<Long, TalentBO> boMap = new HashMap<>();
        for (TalentBO bo : bos) {
            Long key = bo.getTalentId();
            if (boMap.containsKey(key)) {
                if (bo.getCstatus() == 1) {
                    boMap.put(key, bo);
                }
            } else {
                boMap.put(key, bo);
            }
        }
        return new PageInfoVO<>(page.getTotal(), TalentVO.convert(new ArrayList<>(boMap.values())));
    }
}
