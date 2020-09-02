package com.talentcard.common.mapper;

import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.bo.PolicyTypeBO;
import com.talentcard.common.pojo.EvFrontendEventPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * EvFrontendEventMapper继承基类
 */
@Mapper
public interface EvFrontendEventMapper extends BaseMapper<EvFrontendEventPO, Long> {
    /**
     * 根据openid查询出所有的数据
     *
     * @param reqData
     * @return
     */
    List<EvFrontendEventBO> pageQuery(Map<String, Object> reqData);
    /**
     * 审批主页查询出所有的数据
     *
     * @param reqData
     * @return
     */
    List<EvFrontendEventBO> approvalQuery(Map<String, Object> reqData);
}
