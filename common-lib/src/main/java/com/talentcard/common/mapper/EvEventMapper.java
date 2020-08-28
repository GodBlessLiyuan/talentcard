package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventPO;
import com.talentcard.common.pojo.PolicyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * EvEventMapper继承基类
 */
@Mapper
public interface EvEventMapper extends BaseMapper<EvEventPO, Long> {
    /**
     * 添加，返回主键
     * @param evEventPO
     * @return
     */
    Integer add(EvEventPO evEventPO);
}