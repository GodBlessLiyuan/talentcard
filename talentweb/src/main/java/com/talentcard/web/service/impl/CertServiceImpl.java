package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.mapper.CertExamineRecordMapper;
import com.talentcard.common.mapper.CertificationMapper;
import com.talentcard.common.pojo.CertExamineRecordPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
    @Autowired
    CertExamineRecordMapper certExamineRecordMapper;


    @Override
    public ResultVO queryCertStatus(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<CertExamineRecordPO> page = PageHelper.startPage(pageNum, pageSize);
//        List<TalentCertStatusBO> bos =  certificationMapper.queryAllCert(map);
        List<CertExamineRecordPO> examineRecordPOList = certExamineRecordMapper.query(hashMap);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), examineRecordPOList));
    }
}
