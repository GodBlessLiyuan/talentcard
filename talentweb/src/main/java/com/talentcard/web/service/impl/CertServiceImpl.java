package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.mapper.CertificationMapper;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertService;
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


    @Override
    public ResultVO queryCertStatus(int pageNum, int pageSize,Map<String, Object> map){
        Page<UserRoleBO> page = PageHelper.startPage(pageNum, pageSize);
        // 根据开始结束时间，姓名，性别，学历，职称，职业资格，状态 检索
        List<TalentCertStatusBO> bos =  certificationMapper.queryAllCert(map);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), bos));
    }
}
