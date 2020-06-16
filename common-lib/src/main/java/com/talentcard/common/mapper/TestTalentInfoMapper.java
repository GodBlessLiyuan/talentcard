package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TestTalentInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TestTalentInfoMapper继承基类
 */
@Mapper
public interface TestTalentInfoMapper extends BaseMapper<TestTalentInfoPO, Long> {
    TestTalentInfoPO selectByOpenId(@Param("openId") String openId);
}