package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CategoryMapper继承基类
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryPO, Long> {
    /**
     * 查询
     *
     * @param name
     * @param status
     * @return
     */
    List<CategoryPO> query(@Param("name") String name, @Param("status") Byte status);
}