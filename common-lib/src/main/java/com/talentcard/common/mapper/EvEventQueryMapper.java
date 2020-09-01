package com.talentcard.common.mapper;

import com.talentcard.common.bo.EvEventQueryBO;
import com.talentcard.common.pojo.EvEventQueryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * EvEventQueryMapper继承基类
 */
@Mapper
public interface EvEventQueryMapper extends BaseMapper<EvEventQueryPO, Long> {
    /**
     * 新增
     *
     * @param evEventQueryPO
     * @return
     */
    Integer add(EvEventQueryPO evEventQueryPO);

    /**
     * 总查询
     *
     * @param name
     * @param type
     * @param status
     * @return
     */
    List<EvEventQueryBO> query(@Param("name") String name,
                               @Param("type") Byte type,
                               @Param("status") Byte status,
                               @Param("currentTime") String currentTime);
    EvEventQueryPO queryByEid(@Param("eid")Long eid);
    List<EvEventQueryBO> approvalQuery(Map<String, Object> reqData);
}