package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ScenicEnjoyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * ScenicEnjoyMapper继承基类
 */
@Mapper
public interface ScenicEnjoyMapper extends BaseMapper<ScenicEnjoyPO, Long> {
    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<ScenicEnjoyPO> pos);

    /**
     * 根据 scenic id 删除
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
    List<ScenicEnjoyPO> queryByScenicId(Long scenicId);

    Integer ifEnableEnjoy(HashMap<String, Object> hashMap);
}