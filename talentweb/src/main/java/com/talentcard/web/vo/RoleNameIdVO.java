package com.talentcard.web.vo;

import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.pojo.RolePO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:53 2020/4/20
 * @version: 1.0.0
 * @description:
 */
@Data
public class RoleNameIdVO {
    private Long roleId;
    private String roleName;

    public static List<RoleNameIdVO> convert(List<RolePO> pos) {
        List<RoleNameIdVO> vos = new ArrayList<>();
        for (RolePO po : pos) {
            RoleNameIdVO vo = new RoleNameIdVO();
            vo.setRoleId(po.getRoleId());
            vo.setRoleName(po.getName());
            vos.add(vo);
        }
        return vos;
    }

}
