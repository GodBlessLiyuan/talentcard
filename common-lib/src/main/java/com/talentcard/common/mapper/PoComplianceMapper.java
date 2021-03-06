package com.talentcard.common.mapper;

import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.bo.QueryPolicyByTalentIdBO;
import com.talentcard.common.pojo.CertApprovalPassRecordPO;
import com.talentcard.common.pojo.PoCompliancePO;
import com.talentcard.common.pojo.PoStatisticsPO;
import com.talentcard.common.pojo.PolicyApplyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PoComplianceMapper继承基类
 */
@Mapper
public interface PoComplianceMapper extends BaseMapper<PoCompliancePO, Long> {
    /**
     * 删除未申请政策的用户信息
     * @return
     */
    long deleteByApplyTime();

    /**
     * 根据policyId和talentId查询出对应的请求信息
     * @param map
     * @return
     */
    List<PoCompliancePO> selectByPolicyTalent(Map map);

    /**
     * 获取人才当前所有的申请情况
     * @param map
     * @return
     */
    List<PoCompliancePO> selectByYearTalent(Map map);

    /**
     * 获取人才当前所有的政策
     * @param talentId
     * @param year
     * @return
     */
    List<QueryPolicyByTalentIdBO> queryPolicyByTalentId(@Param("talentId") Long talentId,
                                                        @Param("year") Integer year);

    /**
     * 获取当前政策所对应的人数
     * @param policyId
     * @return
     */
    Long countMeetConditionNumber(@Param("policyId") Long policyId);

    /**
     * 根据policyId和talentId查询出政策对应的统计信息
     * @param map
     * @return
     */
    List<PoStatisticsPO> policyCount(Map map);
    /**
     * 获取人才当前所有的申请情况
     * @param reqData
     * @return
     */
    List<PoComplianceBO> pageQuery(Map<String, Object> reqData);

    /**
     * 获取人认证id
     * @param reqData
     * @return
     */
    CertApprovalPassRecordPO queryCertId(Map<String, Object> reqData);
    /**
     * 查询审批表未审批和待审批的政策申请记录
     * @param map
     * @return
     */
    List<PoCompliancePO> selectByPidAndStatus(Map map);
}