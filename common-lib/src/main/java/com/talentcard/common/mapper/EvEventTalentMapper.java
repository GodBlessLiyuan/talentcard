package com.talentcard.common.mapper;

import com.talentcard.common.bo.QueryTalentInfoBO;
import com.talentcard.common.pojo.EvEventTalentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EvEventTalentMapper继承基类
 */
@Mapper
public interface EvEventTalentMapper extends BaseMapper<EvEventTalentPO, Long> {
    List<QueryTalentInfoBO> queryTalentInfo(@Param("name") String name,
                                            @Param("workLocation") String workLocation,
                                            @Param("sex") Byte sex,
                                            @Param("status") Byte status);

}