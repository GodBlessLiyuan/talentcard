package com.talentcard.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.wechat.config.WxConfig;
import com.talentcard.wechat.utils.AccessTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/10 09:28
 * @description 菜单的新建/删除/查询
 */
@RequestMapping("menu")
@RestController
public class MenuController {

    @Autowired
    private WxConfig wxConfig;

    /**
     * 增加菜单
     * @param jsonObject
     * @return
     */
    @PostMapping("add")
    public JSONObject add(@RequestBody JSONObject jsonObject, @RequestHeader String token) {

        if(!StringUtils.equals(this.wxConfig.getCheckToken(), token)){
            return null;
        }

        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject result = WechatApiUtil.postRequest(url, jsonObject);
        return result;
    }

    /**
     * 查询当前菜单
     * @return
     */
    @RequestMapping("query")
    public JSONObject query(@RequestHeader String token) {

        if(!StringUtils.equals(this.wxConfig.getCheckToken(), token)){
            return null;
        }

        String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject result = WechatApiUtil.getRequest(url);
        return result;
    }

//    @PostMapping("delete")
//    public JSONObject delete() {
//        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
//                + AccessTokenUtil.getAccessToken();
//        JSONObject result = WechatApiUtil.getRequest(url);
//        return result;
//    }
}
