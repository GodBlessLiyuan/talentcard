package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EducationPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * EducationMapper继承基类
 */
@Mapper
public interface EducationMapper extends BaseMapper<EducationPO, Long> {
    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Integer> queryNameByTalentId(Long talentId);
}