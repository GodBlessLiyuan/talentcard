package com.talentcard.front.service;
import com.talentcard.common.bo.UserBO;
import com.talentcard.common.pojo.UserPO;

public interface IUserService {
    int insert(UserPO userPO);

    /**
     * 通过微信id和状态值查找user信息，关联认证信息
     * @param wechatId
     * @param status
     * @return
     */
    UserBO findByWechat(String wechatId, Byte status);
}
