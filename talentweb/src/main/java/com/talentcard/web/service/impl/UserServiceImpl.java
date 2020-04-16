package com.talentcard.web.service.impl;

import com.talentcard.common.bo.UserBO;
import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserService;
import com.talentcard.web.utils.DTPageInfo;
import com.talentcard.web.utils.Md5Util;
import com.talentcard.web.utils.PageHelper;
import com.github.pagehelper.Page;
import com.talentcard.web.vo.UserRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    /**
     * 加密用的盐
     */
    public static final String SALT = "talentCard";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editPassword(HttpSession session, String oldPassword, String newPassword){
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return new ResultVO(2104);
        }
//        Long userId = 1L;
        UserPO userPo = userMapper.selectByPrimaryKey(userId);
        try{
            oldPassword = Md5Util.encodeByMd5(SALT+oldPassword);
            newPassword = Md5Util.encodeByMd5(SALT+newPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!oldPassword.equals(userPo.getPassword())) {
            // 旧密码输入错误
            return new ResultVO(2105);
        }else {
            userMapper.updatePassword(newPassword,userId);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertUser(String username, String password, String name, Long roleId, String extra){
        //1.监测用户名是否重复
        UserPO userPo = userMapper.queryByName(username);
        if (null != userPo) {
            //用户名已存在，请重新填写
            return new ResultVO(2106);
        }
        //2.将密码md5加密
        try{
            password = Md5Util.encodeByMd5(SALT+password);
        }catch (Exception e){
            e.printStackTrace();
        }
        UserBO userBO = new UserBO();
        userBO.setUsername(username);
        userBO.setPassword(password);
        userBO.setName(name);
        userBO.setRoleId(roleId);
        userBO.setExtra(extra);
        int result = userMapper.insertUser(userBO);
        if (result == 0) {
            //新建用户失败
            return new ResultVO(2107);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editUser(String username, String name, Long roleId, String extra){
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
        return new ResultVO(1000);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO adminUpdatePassword(String username, String password){

        try {
            password = Md5Util.encodeByMd5(SALT + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int result = userMapper.adminUpdatePassword(username,password);
        if (result == 0) {
            // 管理员更新用户密码失败
            return new ResultVO(2109);
        }
        return new ResultVO(1000);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteUser(String username){
        int result = userMapper.deleteUser(username);
        if (result == 0) {
            // 删除用户失败
            return new ResultVO(2110);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryUserRole(int draw, int pageNum, int pageSize, Map<String, Object> reqData){

        Page<UserRoleBO> page = PageHelper.startPage(pageNum, pageSize);

        List<UserRoleBO> bos = userMapper.queryUserRole(reqData);

        return new ResultVO(1000,new DTPageInfo<>(draw, page.getTotal(), UserRoleVO.convert(bos)));
    }


}
