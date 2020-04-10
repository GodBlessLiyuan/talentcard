package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.awt.SunHints;

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
}