package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CertExamineRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CertExamineRecordMapper继承基类
 */
@Mapper
public interface CertExamineRecordMapper extends BaseMapper<CertExamineRecordPO, Long> {
    /**
     * 根据certId找到一个PO
     *
     * @param certId
     * @return
     */
    CertExamineRecordPO selectByCertId(@Param("certId") Long certId);

    /**
     * 后台认证审批查询
     *
     * @param hashMap
     * @return
     */
    List<CertExamineRecordPO> query(HashMap<String, Object> hashMap);

    /**
     * 查询talentId
     * @param map
     * @return
     */
    List<CertExamineRecordPO> selectByMap(Map map);
}