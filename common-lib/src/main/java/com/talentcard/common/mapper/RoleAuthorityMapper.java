package com.talentcard.common.mapper;

import com.talentcard.common.bo.RoleAuthorityAddNameBO;
import com.talentcard.common.pojo.RoleAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * RoleAuthorityMapper继承基类
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthorityPO, Long> {
    /**
     * 根据角色id获取当前角色所有权限列表
     * @param roleId
     * @return
     */
    List<RoleAuthorityAddNameBO> queryByRoleId(long roleId);


}