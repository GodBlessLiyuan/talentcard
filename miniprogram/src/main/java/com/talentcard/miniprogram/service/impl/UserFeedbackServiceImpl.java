package com.talentcard.miniprogram.service.impl;

import com.talentcard.common.mapper.UserFeedbackMapper;
import com.talentcard.common.pojo.UserFeedbackPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.UserFeedBackDTO;
import com.talentcard.miniprogram.service.IUserFeedbackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 19:08
 */
@Service
public class UserFeedbackServiceImpl implements IUserFeedbackService {
    @Autowired
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public ResultVO insert(UserFeedBackDTO userFeedBackDTO) {
        if (userFeedBackDTO == null || StringUtils.isEmpty(userFeedBackDTO.getOpenId())) {
            return new ResultVO(2000);
        }
        //""在这里赋值不是，为null
        if (userFeedBackDTO.getPageType() == null || userFeedBackDTO.getRelateItem() == null) {
            return new ResultVO(2000);
        }

        UserFeedbackPO userFeedbackPO = new UserFeedbackPO();
        userFeedbackPO.setOpenId(userFeedBackDTO.getOpenId());
        userFeedbackPO.setPageType(userFeedBackDTO.getPageType());
        userFeedbackPO.setRelateItem(userFeedBackDTO.getRelateItem());
        userFeedbackPO.setProDescribe(userFeedBackDTO.getProDescribe());
        userFeedbackPO.setSubmitDate(new Date());
        int flag = userFeedbackMapper.insert(userFeedbackPO);
        if (flag != 1) {
            return new ResultVO(2000);
        }
        return new ResultVO(1000);
    }
}
