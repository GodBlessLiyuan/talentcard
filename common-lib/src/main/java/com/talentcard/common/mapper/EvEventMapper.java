package com.talentcard.common.mapper;

import com.talentcard.common.bo.MyEventBO;
import com.talentcard.common.pojo.EvEventPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EvEventMapper继承基类
 */
@Mapper
public interface EvEventMapper extends BaseMapper<EvEventPO, Long> {
    /**
     * 新增
     * @param evEventPO
     * @return
     */
    Integer add(EvEventPO evEventPO);

    /**
     * 我的活动查询
     * @param openId
     * @return
     */
    List<MyEventBO> findMyEvent(@Param("openId")String openId);
}