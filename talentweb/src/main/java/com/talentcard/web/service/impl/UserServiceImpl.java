package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.UserBO;
import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.OpwebRecordPO;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IUserService;
import com.talentcard.web.utils.Md5Util;
import com.talentcard.web.utils.PageHelper;
import com.talentcard.web.vo.UserRoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:50 2020/4/10
 * @version: 1.0.0
 * @description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;
    @Resource
    private ILogService logService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * 加密用的盐
     */
    public static final String SALT = "talentCard";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editPassword(HttpSession session, String oldPassword, String newPassword) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //根据id,检索到当前用户，以便获得password
        UserPO userPo = userMapper.selectByPrimaryKey(userId);
        try {
            oldPassword = Md5Util.encodeByMd5(SALT + oldPassword);
            newPassword = Md5Util.encodeByMd5(SALT + newPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 比较输入旧密码是否等于用户本身存在数据库的密码
        if (!oldPassword.equals(userPo.getPassword())) {
            // 旧密码输入错误
            return new ResultVO(2105);
        } else {
            // 如果没有问题，则将当前userId用户更新密码
            userMapper.updatePassword(newPassword, userId);
        }

        logService.insertActionRecord(session, OpsRecordMenuConstant.F_SysManager, OpsRecordMenuConstant.S_UserManager, "修改%s的密码", userPo.getUsername());

        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertUser(HttpSession session, String username, String password, String name, Long roleId, String extra) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //1.监测用户名是否重复
        UserPO userPo = userMapper.queryByName(username);
        if (null != userPo) {
            //用户名已存在，请重新填写
            return new ResultVO(2106);
        }
        //2.将密码md5加密
        try {
            password = Md5Util.encodeByMd5(SALT + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserPO userPO = new UserPO();
        userPO.setUsername(username);
        userPO.setPassword(password);
        userPO.setName(name);
        userPO.setRoleId(roleId);
        userPO.setExtra(extra);
        userPO.setCreateTime(new Date());
        int result = userMapper.insertUser(userPO);
        if (result == 0) {
            //新建用户失败
            return new ResultVO(2107);
        }

        logService.insertActionRecord(session, OpsRecordMenuConstant.F_SysManager, OpsRecordMenuConstant.S_UserManager, "新增%s用户", username);

        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editUser(HttpSession session,String username, String name, Long roleId, String extra) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        UserBO userBO = new UserBO();
        userBO.setUsername(username);
        userBO.setName(name);
        userBO.setRoleId(roleId);
        userBO.setExtra(extra);
        int result = userMapper.updateByUserName(userBO);
        if (result == 0) {
            // 编辑用户失败
            return new ResultVO(2108);
        }
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_SysManager,OpsRecordMenuConstant.S_UserManager,"编辑%s用户",username);

        return new ResultVO(1000);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO adminUpdatePassword(HttpSession session,String username, String password) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        try {
            password = Md5Util.encodeByMd5(SALT + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int result = userMapper.adminUpdatePassword(username, password);
        if (result == 0) {
            // 管理员更新用户密码失败
            return new ResultVO(2109);
        }
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_SysManager,OpsRecordMenuConstant.S_UserManager,"更新管理员%s密码",username);

        return new ResultVO(1000);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteUser(HttpSession session,String username) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        int result = userMapper.deleteUser(username);
        if (result == 0) {
            // 删除用户失败
            return new ResultVO(2110);
        }
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_SysManager,OpsRecordMenuConstant.S_UserManager,"删除%s用户",username);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryUserRole(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<UserRoleBO> page = PageHelper.startPage(pageNum, pageSize);
        // 根据username 和 roleId 查询
        List<UserRoleBO> bos = userMapper.queryUserRole(reqData);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), UserRoleVO.convert(bos)));
    }


}
