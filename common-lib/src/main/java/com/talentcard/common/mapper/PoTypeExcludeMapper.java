package com.talentcard.common.mapper;

import com.talentcard.common.bo.PolicyTypeBO;
import com.talentcard.common.pojo.PoTypeExcludePO;
import com.talentcard.common.pojo.PoTypePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PoTypeExcludeMapper继承基类
 */
@Mapper
public interface PoTypeExcludeMapper extends BaseMapper<PoTypeExcludePO, Long> {
    void delete(@Param("eid")Long eid);
    List<PoTypeExcludePO> queryExId(@Param("eid")Long eid);
}