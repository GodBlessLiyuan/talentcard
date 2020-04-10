package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ProfTitlePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ProfTitleMapper继承基类
 */
@Mapper
public interface ProfTitleMapper extends BaseMapper<ProfTitlePO, Long> {
    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Integer> queryNameByTalentId(Long talentId);
}