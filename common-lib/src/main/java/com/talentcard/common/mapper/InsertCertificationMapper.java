package com.talentcard.common.mapper;

import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.pojo.InsertCertificationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * InsertCertificationMapper继承基类
 */
@Mapper
public interface InsertCertificationMapper extends BaseMapper<InsertCertificationPO, Long> {
    /**
     * insert新增
     *
     * @param insertCertificationPO
     * @return
     */
    Integer add(InsertCertificationPO insertCertificationPO);

    /**
     * 根据openId查询人才所拥有的所有 处于待认证状态中 的新增认证
     *
     * @param openId
     * @return
     */
    List<InsertCertificationBO> selectByOpenId(@Param("openId") String openId);

    /**
     * 根据openId和新增认证id，查询具体某一条新增认证的详情
     *
     * @param openId
     * @param insertCertId
     * @return
     */
    InsertCertificationBO findOne(@Param("openId") String openId,
                                  @Param("insertCertId") Long insertCertId);

    /**
     * 根据openId和type，查找当前人才存在的认证数量
     * 已认证+待认证
     *
     * @param openId
     * @param type
     * @return
     */
    Integer findCurrentCertificationTimes(@Param("openId") String openId,
                                          @Param("type") Byte type);

    /**
     * 根据openId查询人才所拥有的所有 处于待认证状态中 的新增认证
     *
     * @param hashMap
     * @return
     */
    List<InsertCertificationBO> query(HashMap<String, Object> hashMap);

    /**
     *
     * @param talentId
     * @param certInfo
     * @param type
     * @return
     */
    Integer checkIfExistInsertCertification(@Param("talentId") Long talentId,
                                            @Param("certInfo") Long certInfo,
                                            @Param("type") Byte type);
}