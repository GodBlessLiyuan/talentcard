package com.talentcard.common.mapper;

import com.talentcard.common.pojo.AuthenticationAttachmentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * AuthenticationAttachmentMapper继承基类
 */
@Mapper
@Repository
public interface AuthenticationAttachmentMapper extends BaseMapper<AuthenticationAttachmentPO, Long> {
}