package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserFeedbackPO;
import org.springframework.stereotype.Repository;

/**
 * UserFeedbackMapper继承基类
 */
@Repository
public interface UserFeedbackMapper extends BaseMapper<UserFeedbackPO, Long> {
}