package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IConfigService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:20
 * @description: 配置
 * @version: 1.0
 */
@RequestMapping("config")
@RestController
public class ConfigController {
    @Autowired
    private IConfigService configService;

    @RequestMapping("query")
    public ResultVO query(@Param("key") String key) {
        return configService.query(key);
    }

    @RequestMapping("edit")
    public ResultVO edit(@Param("key") String key, @Param("value") String value) {
        return configService.edit(key, value);
    }
}
