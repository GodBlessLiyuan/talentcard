package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhousePicturePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * FarmhousePictureMapper继承基类
 */
@Mapper
public interface FarmhousePictureMapper extends BaseMapper<FarmhousePicturePO, Long> {
    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<FarmhousePicturePO> pos);

    /**
     * 根据 farmhouseId 删除
     *
     * @param farmhouseId
     */
    void deleteByFarmhouseId(Long farmhouseId);

    /**
     * 根据 farmhouseId 查询
     *
     * @param farmhouseId
     * @return
     */
    List<FarmhousePicturePO> queryByFarmhouseId(Long farmhouseId);
}