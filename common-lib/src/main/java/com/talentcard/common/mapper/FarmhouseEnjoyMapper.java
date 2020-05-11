package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseEnjoyPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * FarmhouseEnjoyMapper继承基类
 */
@Mapper
public interface FarmhouseEnjoyMapper extends BaseMapper<FarmhouseEnjoyPO, Long> {
    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<FarmhouseEnjoyPO> pos);

    /**
     * 根据 farmhouseId 删除
     *
     * @param farmhouseId
     */
    void deleteByScenicId(Long farmhouseId);

    /**
     * 根据 farmhouseId 查询
     *
     * @param farmhouseId
     * @return
     */
    List<FarmhouseEnjoyPO> queryByScenicId(Long farmhouseId);
}