package com.talentcard.common.mapper;

import com.talentcard.common.pojo.BatchCertificatePO;
import com.talentcard.common.pojo.CertificationPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * BatchCertificateMapper继承基类
 */
@Mapper
public interface BatchCertificateMapper extends BaseMapper<BatchCertificatePO, Long> {
    Integer add(BatchCertificatePO batchCertificatePO);
    List<BatchCertificatePO> findBatchCertificate(HashMap<String, Object> hashMap);
}