package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.PolicyApprovalBO;
import com.talentcard.common.bo.PolicyTypeBO;
import com.talentcard.common.mapper.PolicyApplyMapper;
import com.talentcard.common.mapper.RoleMapper;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.RolePO;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IPolicyApprovalService;
import com.talentcard.web.vo.PolicyApprovalShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 9:01 2020/10/23
 * @ Description：政策审批服务类
 * @ Modified By：
 * @ Version:     1.0
 */
@Service
public class PolicyApprovalServiceImpl implements IPolicyApprovalService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PolicyApplyMapper policyApplyMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqMap, HttpSession session) {
        //获取登录用户角色
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //获取用户角色
        UserPO userPO = userMapper.selectByPrimaryKey(userId);
        RolePO rolePO;
        if (userPO != null && userPO.getRoleId() != null) {
            rolePO = roleMapper.selectByPrimaryKey(userPO.getRoleId());
        } else {
            // 无此角色
            return new ResultVO(1001, "无此角色");
        }
        //如果不是管理员和运营角色，只查询当前缺色可审批的政策
        if (rolePO.getRoleType() != null && rolePO.getRoleType() != 1) {
            reqMap.put("rid", rolePO.getRoleId());
        }
        //入参当前时间
        reqMap.put("currentTime", new Date());
        Page<PolicyTypeBO> page = PageHelper.startPage(reqMap);
        List<PolicyApprovalBO> policyApprovalBOS = policyApplyMapper.pageQuery(reqMap);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), PolicyApprovalShowVO.convert(policyApprovalBOS)));
    }


    @Override
    public ResultVO notApprovalNum(HttpSession session) {
        //获取登录用户角色
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //获取用户角色
        UserPO userPO = userMapper.selectByPrimaryKey(userId);
        RolePO rolePO;
        if (userPO != null && userPO.getRoleId() != null) {
            rolePO = roleMapper.selectByPrimaryKey(userPO.getRoleId());
        } else {
            // 无此角色
            return new ResultVO(1001, "无此角色");
        }
        //如果不是管理员和运营角色，只查询当前缺色可审批的政策
        Long notApprovalNum;
        if (rolePO.getRoleType() != null && rolePO.getRoleType() != 1) {
            notApprovalNum = policyApplyMapper.notApprovalNum(null);
            Map map = new HashMap(1);
            map.put("notApprovalNum", notApprovalNum);
            return ResultVO.ok().setData(map);
        } else if (rolePO.getRoleType() != null && rolePO.getRoleType() != 2) {
            notApprovalNum = policyApplyMapper.notApprovalNum(rolePO.getRoleId());
            Map map = new HashMap(1);
            map.put("notApprovalNum", notApprovalNum);
            return ResultVO.ok().setData(map);
        } else {
            //角色类型错误
            return new ResultVO(1003, "角色类型错误");
        }

    }
}
