package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PolicyMapper继承基类
 */
@Mapper
public interface PolicyMapper extends BaseMapper<PolicyPO, Long> {
    /**
     * 根据人才ID查询政策数据
     *
     * @param tid
     * @return
     */
    List<PolicyPO> queryByTalentId(Long tid);
}