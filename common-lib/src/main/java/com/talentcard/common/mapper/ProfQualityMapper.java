package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ProfQualityPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ProfQualityMapper继承基类
 */
@Mapper
public interface ProfQualityMapper extends BaseMapper<ProfQualityPO, Long> {
    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Integer> queryNameByTalentId(Long talentId);
}