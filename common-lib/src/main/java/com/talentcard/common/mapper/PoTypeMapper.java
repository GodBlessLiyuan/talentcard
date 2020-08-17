package com.talentcard.common.mapper;

import com.talentcard.common.bo.PolicyTypeBO;
import com.talentcard.common.pojo.PoTypePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PoTypeMapper继承基类
 */
@Mapper
public interface PoTypeMapper extends BaseMapper<PoTypePO, Long> {
    /**
     * 首页根据政策类型和状态模糊查询
     *
     * @param reqData
     * @return
     */
    List<PolicyTypeBO> pageQuery(Map<String, Object> reqData);
    /**
     * 首页根据政策类型id查出政策类型名称
     *
     * @param reqData
     * @return
     */
    List<PolicyTypeBO> queryPtNameByPtId(@Param("reqData")List<String> reqData);
    /**
     * 查询所有的政策名称和对应的id，返给前端进行展示
     *
     * @param
     * @return
     */
    List<PolicyTypeBO> queryExIdAndName();

    /**
     * 查询所有的政策名称和对应的id，返给前端进行展示
     *
     * @param
     * @return
     */
}