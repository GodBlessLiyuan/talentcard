package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.TalentTypePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * TalentTypeMapper继承基类
 */
@Mapper
public interface TalentTypeMapper extends BaseMapper<TalentTypePO, Long> {

    /**
     * 查询所有符合条件的用户
     * @param map
     * @return
     */
    List<Long> selectByAllType(Map map);

    /**
     * 查询某个用户的所有标签
     * @param talentId
     * @return
     */
    List<TalentTypePO> selectByTalentId(Long talentId);

}