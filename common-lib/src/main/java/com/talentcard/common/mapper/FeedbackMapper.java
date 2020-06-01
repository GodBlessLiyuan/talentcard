package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FeedbackPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * FeedbackMapper继承基类
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<FeedbackPO, Long> {
}