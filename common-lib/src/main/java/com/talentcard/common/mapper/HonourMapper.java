package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CategoryPO;
import com.talentcard.common.pojo.HonourPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * HonourMapper继承基类
 */
@Mapper
public interface HonourMapper extends BaseMapper<HonourPO, Long> {
    /**
     * 查询
     *
     * @param name
     * @param status
     * @return
     */
    List<HonourPO> query(@Param("name") String name, @Param("status") Byte status);

    /**
     * 判断是否存在该名称
     * @param name
     * @return
     */
    Integer ifExistName(@Param("name") String name);
}