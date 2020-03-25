package com.talentcard.common.mapper;

import com.talentcard.common.pojo.AuthenticationPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * AuthenticationMapper继承基类
 * @author ChenXU
 */
@Mapper
@Repository
public interface AuthenticationMapper extends MyBatisBaseDao<AuthenticationPO, Long> {
}