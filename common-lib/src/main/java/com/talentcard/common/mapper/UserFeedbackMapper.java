package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserFeedbackPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * UserFeedbackMapper继承基类
 */
@Mapper
public interface UserFeedbackMapper extends BaseMapper<UserFeedbackPO, Long> {
}