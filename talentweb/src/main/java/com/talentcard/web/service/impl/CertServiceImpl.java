package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.mapper.CertificationMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertService;
import com.talentcard.web.utils.DTPageInfo;
import com.talentcard.web.utils.PageHelper;
import com.talentcard.web.vo.UserRoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:24 2020/4/15
 * @version: 1.0.0
 * @description:
 */
@Service
public class CertServiceImpl implements ICertService {
    @Resource
    CertificationMapper certificationMapper;
    @Resource
    TalentMapper talentMapper;

    @Override
    public ResultVO queryCertStatus(int draw, int pageNum, int pageSize,Map<String, Object> map){
        Page<UserRoleBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TalentCertStatusBO> bos =  talentMapper.queryTalentStatus(map);
        return new ResultVO(1000,new DTPageInfo<>(draw, page.getTotal(), bos));
    }
}
