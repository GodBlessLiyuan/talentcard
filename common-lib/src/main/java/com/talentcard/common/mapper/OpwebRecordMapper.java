package com.talentcard.common.mapper;

import com.talentcard.common.pojo.OpwebRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OpwebRecordMapper继承基类
 */
@Mapper
public interface OpwebRecordMapper extends BaseMapper<OpwebRecordPO, Long> {

    void query();
}
