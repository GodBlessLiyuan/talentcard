package com.talentcard.web.service.impl;

import com.talentcard.common.bo.RoleAuthorityAddNameBO;
import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.mapper.RoleAuthorityMapper;
import com.talentcard.common.mapper.RoleMapper;
import com.talentcard.common.pojo.RolePO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IRoleService;
import com.talentcard.web.vo.ManageRoleVO;
import com.talentcard.web.vo.RoleNameIdVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    @Override
    public ResultVO queryByRole(String roleName, String startTime, String endTime) {
        startTime = startTime + " 00:00:00";
        endTime = endTime + " 24:00:00";
        // 1 先在角色基础表中查询当前角色信息
        List<RolePO> rolePOS = roleMapper.queryRoleByTime(roleName,startTime,endTime);
        if (rolePOS.size() == 0) {
            // 角色列表查询当前为空
            return new ResultVO(2111);
        }
        List<ManageRoleVO> manageRoleVOS = new ArrayList<>();
        // 2 根据角色id在角色权限关联表中，查询其所拥有权限，权限状态码status为1
        for (RolePO rolePO:rolePOS) {
            ManageRoleVO manageRoleVO = new ManageRoleVO();
            Long roleId = rolePO.getRoleId();
            List<RoleAuthorityAddNameBO> bos = roleAuthorityMapper.queryByRoleIdName(roleId);
            RoleAuthorityBO roleAuthorityBO = RoleAuthorityBO.convert(bos);
            manageRoleVO.setCreateTime(rolePO.getCreateTime());
            manageRoleVO.setExtra(rolePO.getExtra());
            manageRoleVO.setRoleAuthorityBO(roleAuthorityBO);
            manageRoleVO.setRoleName(rolePO.getName());
            manageRoleVOS.add(manageRoleVO);
        }
        return new ResultVO(1000,manageRoleVOS);
    }

    @Override
    public ResultVO queryRoleNameIdMsg() {
        List<RoleNameIdVO> vos = RoleNameIdVO.convert(roleMapper.queryRoleNameMsg());
        if (vos.size() == 0) {
            // 当前无任何角色
            return new ResultVO(2666);
        }
        return new ResultVO(1000,vos);
    }
}
