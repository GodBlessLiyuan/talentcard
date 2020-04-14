package com.talentcard.common.mapper;

import com.talentcard.common.pojo.RolePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * RoleMapper继承基类
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {
    /**
     * 根据角色名和创建时间间隔查询
     * @param roleName
     * @param startTime
     * @param endTime
     * @return
     */
    List<RolePO> queryRoleByTime(@Param("roleName") String roleName, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 根据角色名和创建时间查询角色id
     * @param roleName
     * @param createTime
     * @return
     */
    Long queryRoleId(@Param("roleName") String roleName, @Param("createTime") Date createTime);
}