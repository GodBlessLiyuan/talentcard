package com.talentcard.common.mapper;

import com.talentcard.common.pojo.AnnexPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AnnexMapper继承基类
 */
@Mapper
public interface AnnexMapper extends BaseMapper<AnnexPO, Long> {
    /**
     * 批量插入
     *
     * @param annexPOs
     */
    void batchInsert(List<AnnexPO> annexPOs);
}