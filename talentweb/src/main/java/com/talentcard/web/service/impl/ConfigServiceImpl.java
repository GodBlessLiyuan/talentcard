package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.ConfigMapper;
import com.talentcard.common.pojo.ConfigPO;
import com.talentcard.common.utils.RedisUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.IConfigService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.utils.ActivityResidueNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 16:23
 * @description: 配置
 * @version: 1.0
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ILogService logService;
    @Override
    public ResultVO query(String key) {
//        ConfigPO po = configMapper.selectByPrimaryKey(key);
        return new ResultVO<>(1000, RedisUtil.getConfigValue(key));
    }

    @Override
    public ResultVO edit(HttpSession session,String key, String value) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        ConfigPO po = configMapper.selectByPrimaryKey(key);
        if (null == po) {
            po = new ConfigPO();
            po.setConfigKey(key);
            po.setConfigValue(value);
            po.setCreateTime(new Date());
            configMapper.insert(po);
        } else {
            po.setConfigValue(value);
            po.setUpdateTime(new Date());
            configMapper.updateByPrimaryKey(po);
        }

        RedisUtil.setConfigValue(key, value);
        ActivityResidueNumUtil.reviseNum();
        String menu_second="";
        if("FARMHOUSE_INFO".equals(key)) menu_second=OpsRecordMenuConstant.S_FarmHouse;
        else if("SCENIC_INFO".equals(key)) menu_second=OpsRecordMenuConstant.S_FreeTrip;
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_ExternalFunction,menu_second,
                "配置关于信息");
        return new ResultVO(1000);
    }
}
