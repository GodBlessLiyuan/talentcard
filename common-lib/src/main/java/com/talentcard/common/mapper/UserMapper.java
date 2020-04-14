package com.talentcard.common.mapper;

import com.talentcard.common.bo.UserBO;
import com.talentcard.common.bo.UserRoleBO;
import com.talentcard.common.pojo.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.awt.SunHints;

import java.util.List;
import java.util.Map;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO, Long> {
    /**
     * 根据用户名查询当前用户信息
     * @param username
     * @return
     */
    UserPO queryByName(String username);

    /**
     * 修改用户密码
     * @param newPassword
     * @param userId
     * @return
     */
    void updatePassword(@Param("newPassword") String newPassword, @Param("userId") long userId);

    /**
     * 新增用户
     * @param userBO
     */
    int insertUser(UserBO userBO);

    /**
     * 编辑用户
     * @param userBO
     */
    int updateByUserName(UserBO userBO);

    /**
     * 管理员根据用户名更改用户的密码
     * @param username
     * @param password
     * @return
     */
    int adminUpdatePassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名删除用户
     * @param username
     * @return
     */
    int deleteUser(@Param("username") String username);

    /**
     * 根据用户名和角色名 查询所有用户的权限
     * @param map
     * @return
     */
    List<UserRoleBO> queryUserRole(Map<String, Object> map);
}