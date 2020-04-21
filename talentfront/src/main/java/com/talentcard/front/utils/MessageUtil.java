package com.talentcard.front.utils;

import com.talentcard.front.dto.TemplateDataDto;
import com.talentcard.front.dto.WeChatTemDto;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-21 14:01
 * @description: TODO
 * @version: 1.0
 */
public class MessageUtil {

    public  static String sendTemplateMessage(String openid){
        String at = AccessTokenUtil.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        WeChatTemDto weChatTemDto = new WeChatTemDto();
        weChatTemDto.setTouser(openid);
        weChatTemDto.setTemplate_id("WOzusH7NSzDlVZMsQSiaZnnTUdcMgGChUVjPA-NjCw0");
        weChatTemDto.setUrl("");
        Map<String, TemplateDataDto> map = new HashMap<>();
        TemplateDataDto first = new TemplateDataDto();
        first.setValue("大傻");
        first.setColor("#173177");
        map.put("first",first);
        TemplateDataDto businessType = new TemplateDataDto();
        businessType.setValue("业务类型");
        businessType.setColor("#173177");
        map.put("businessType",businessType);
        TemplateDataDto business = new TemplateDataDto();
        business.setValue("入户申请");
        business.setColor("#173177");
        map.put("business",business);
        TemplateDataDto order = new TemplateDataDto();
        order.setValue("1234232332");
        order.setColor("#173177");
        map.put("order",order);
        TemplateDataDto time = new TemplateDataDto();
        time.setValue("2020年4月22日8：00-10:00");
        time.setColor("#173177");
        TemplateDataDto address = new TemplateDataDto();
        address.setValue("柯城区城西派出所");
        address.setColor("#173177");
        map.put("address",address);
        TemplateDataDto remark = new TemplateDataDto();
        remark.setValue("请您带上身份证以及相关证件");
        remark.setColor("#173177");
        map.put("remark",remark);
        weChatTemDto.setData(map);
        JSONObject jsonObject = JSONObject.fromObject(weChatTemDto);
        String data = jsonObject.toString();
        String result = RequestUtil.post(url,data);
        return result;
    }
}
