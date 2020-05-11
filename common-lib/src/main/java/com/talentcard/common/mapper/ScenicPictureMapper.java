package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ScenicPicturePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ScenicPictureMapper继承基类
 */
@Mapper
public interface ScenicPictureMapper extends BaseMapper<ScenicPicturePO, Long> {
    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<ScenicPicturePO> pos);

    /**
     * 根据 scenicId 删除
     *
     * @param scenicId
     */
    void deleteByScenicId(Long scenicId);

    /**
     * 根据 scenic id 查询
     *
     * @param scenicId
     * @return
     */
    List<ScenicPicturePO> queryByScenicId(Long scenicId);
}