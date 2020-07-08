package com.talentcard.common.mapper;

import com.talentcard.common.bo.InsertCertApprovalBO;
import com.talentcard.common.pojo.InsertCertApprovalPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * InsertCertApprovalMapper继承基类
 */
@Mapper
public interface InsertCertApprovalMapper extends BaseMapper<InsertCertApprovalPO, Long> {
    /**
     * 新增认证记录查询
     * @param talentId
     * @return
     */
    List<InsertCertApprovalBO> findRecord(@Param("talentId") Long talentId,
                                          @Param("insertCertId") Long insertCertId);

    /**
     * 根据insertCertId新增审批查询结果
     * @param insertCertId
     * @param type
     * @return
     */
    InsertCertApprovalPO findResultByInsertCertId(@Param("insertCertId") Long insertCertId,
                                                  @Param("type") Byte type);
}