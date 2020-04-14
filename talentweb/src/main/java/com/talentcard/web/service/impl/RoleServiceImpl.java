package com.talentcard.web.service.impl;

import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.bo.RoleAuthortyNameBO;
import com.talentcard.common.mapper.AuthorityMapper;
import com.talentcard.common.mapper.RoleAuthorityMapper;
import com.talentcard.common.mapper.RoleMapper;
import com.talentcard.common.pojo.RoleAuthorityPO;
import com.talentcard.common.pojo.RolePO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IRoleService;
import com.talentcard.web.vo.ManageRoleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:11 2020/4/13
 * @version: 1.0.0
 * @description:
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleAuthorityMapper roleAuthorityMapper;
    @Resource
    AuthorityMapper authorityMapper;

    @Override
    public ResultVO queryByRole(String roleName, Date startTime, Date endTime) {
        // 1 先在角色基础表中查询当前角色信息
        List<RolePO> rolePOS = roleMapper.queryRoleByTime(roleName,startTime,endTime);
        if (null == rolePOS) {
            // 角色列表查询失败
            return new ResultVO(10026);
        }
        List<ManageRoleVO> manageRoleVOS = new ArrayList<>();
        // 2 根据角色id在角色权限关联表中，查询其所拥有权限，权限状态码status为1
        for (RolePO rolePO:rolePOS) {
            ManageRoleVO manageRoleVO = new ManageRoleVO();
            List<RoleAuthortyNameBO> roleAuthortyNameBOS = new ArrayList<>();
            Long roleId = rolePO.getRoleId();

            System.out.println(roleId);

            List<RoleAuthorityPO> roleAuthorityPOS = roleAuthorityMapper.queryByRoleId(roleId);
            for (RoleAuthorityPO po:roleAuthorityPOS) {
                RoleAuthortyNameBO roleAuthortyNameBo = new RoleAuthortyNameBO();
                Long authorityId = po.getAuthorityId();
                String authorityName = authorityMapper.queryByAuthorityId(authorityId);
                roleAuthortyNameBo.setAuthorityName(authorityName);
                roleAuthortyNameBo.setRoleAuthorityPO(po);
                roleAuthortyNameBOS.add(roleAuthortyNameBo);
            }
            RoleAuthorityBO roleAuthorityBO = RoleAuthorityBO.convert(roleAuthortyNameBOS);
            manageRoleVO.setCreateTime(rolePO.getCreateTime());
            manageRoleVO.setExtra(rolePO.getExtra());
            manageRoleVO.setRoleAuthorityBO(roleAuthorityBO);
            manageRoleVO.setRoleName(rolePO.getName());
            manageRoleVOS.add(manageRoleVO);
        }
        return new ResultVO(10000,manageRoleVOS);
    }
}
