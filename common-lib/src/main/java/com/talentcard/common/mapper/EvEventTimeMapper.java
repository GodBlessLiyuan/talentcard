package com.talentcard.common.mapper;

import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.pojo.EvEventTimePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * EvEventTimeMapper继承基类
 */
@Mapper
public interface EvEventTimeMapper extends BaseMapper<EvEventTimePO, Long> {
    /**
     * 根据场地和时间查询占用的时间段
     *
     * @param reqData
     * @return
     */
    EvEventTimePO queryByPlaceAndDate(Map<String, Object> reqData);
}