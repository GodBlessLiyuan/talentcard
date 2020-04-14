package com.talentcard.web.service.impl;

import com.talentcard.common.bo.RoleAndAuthorityBO;
import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.mapper.AuthorityMapper;
import com.talentcard.common.mapper.RoleAuthorityMapper;
import com.talentcard.common.mapper.RoleMapper;
import com.talentcard.common.pojo.RoleAuthorityPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IAuthorityService;
import com.talentcard.web.utils.RoleUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:48 2020/4/13
 * @version: 1.0.0
 * @description: 权限更改
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleAuthorityMapper roleAuthorityMapper;
    @Resource
    AuthorityMapper authorityMapper;

//    @Override
//    public ResultVO updateAuthority(String roleName, Date createTime, RoleAuthorityBO roleAuthorityBO) {
////        1 . 根据角色名和创建时间，find 角色ID
//        Long roleId = roleMapper.queryRoleId(roleName,createTime);
////        2 . 根据roleId在角色权限表中查询出所有的对象
//        List<RoleAuthorityPO> pos = roleAuthorityMapper.queryByRoleId(roleId);
////        3 . 根据角色ID和权限名列表即权限ID,拼装成<roleID,authorityId>
//        RoleAndAuthorityBO raBo = new RoleAndAuthorityBO();
//        raBo.setRoleId(roleId);
//        raBo.setRoleAuthorityBO(roleAuthorityBO);
//        // 将RoleAndAuthorityBO 转换为 AuthorityPO
////        4 . 构建<roleID,authorityId,status>
//        List<RoleAuthorityPO> roleAuthorityPOS = RoleUtil.buildRoleAuthorityPOS(pos,raBo);
//
////        5 . 根据roleAuthorityVO的状态码以及<roleID,authorityId,status>更新roleAuthority表
//    }

}
