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
import org.apache.commons.lang.StringUtils;
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
        // 前端传过来的是年月日格式的时间数据，因此要给开始时间拼接00:00:00,给
        // 结束时间拼接23:59:59。一为了和数据库时间格式对应年月日 时分秒，以便查询 二 防止
        // 日期为同一天，若不加前后限制，则检索不出数据
        if (!StringUtils.isEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        if (!StringUtils.isEmpty(endTime)) {
            endTime = endTime + " 23:59:59";
        }
        // 1 先在角色基础表中查询当前角色信息
        List<RolePO> rolePOS = roleMapper.queryRoleByTime(roleName, startTime, endTime);
        if (rolePOS.size() == 0) {
            // 角色列表查询当前为空
            return new ResultVO(1000, null);
        }
        List<ManageRoleVO> manageRoleVOS = new ArrayList<>();
        // 2 根据角色id在角色权限关联表中，查询其所拥有权限，若拥有权限状态码status为1
        for (RolePO rolePO : rolePOS) {
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
        return new ResultVO(1000, manageRoleVOS);
    }

    @Override
    public ResultVO queryRoleNameIdMsg() {
        List<RoleNameIdVO> vos = RoleNameIdVO.convert(roleMapper.queryRoleNameMsg());
        if (vos.size() == 0) {
            // 当前无任何角色
            return new ResultVO(2666);
        }
        return new ResultVO(1000, vos);
    }

    @Override
    public ResultVO queryResponsibleUnit() {
        return new ResultVO(1000, roleMapper.queryResponsibleUnit());
    }

    @Override
    public ResultVO findOne(Long roleId) {
        return new ResultVO(1000, roleMapper.findOne(roleId));
    }
}
