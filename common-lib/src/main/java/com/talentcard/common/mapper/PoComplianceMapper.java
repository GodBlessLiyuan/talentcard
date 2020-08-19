package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoCompliancePO;
import org.apache.ibatis.annotations.Mapper;

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
    PoCompliancePO selectByPolicyTalent(Map map);

    /**
     * 获取人才当前所有的申请情况
     * @param map
     * @return
     */
    List<PoCompliancePO> selectByYearTalent(Map map);
}