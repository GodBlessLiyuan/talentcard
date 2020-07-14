package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.UserFeedbackMapper;
import com.talentcard.common.pojo.UserFeedbackPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserFeedbackService;
import com.talentcard.web.utils.PageHelper;
import com.talentcard.web.vo.UserFeedbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 19:35
 */
@Service
public class UserFeedbackServiceImpl implements IUserFeedbackService {
    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    @Override
    public ResultVO query(Integer pageNum,Integer pageSize,Map<String, Object> map) {
        Page<UserFeedbackPO> page = PageHelper.startPage(pageNum, pageSize);
        List<UserFeedbackPO> pos = userFeedbackMapper.query(map);
        System.out.println(map.toString());
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), UserFeedbackVO.convert(pos)));
    }
}
