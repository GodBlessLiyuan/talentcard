package com.talentcard.web.utils;

import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.dto.TemplateDataDto;
import com.talentcard.web.dto.WeChatTemDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-21 14:01
 * @description: TODO
 * @version: 1.0
 */
public class MessageUtil {


    private static String templateId;

    @Value("${wechat.template_id}")
    public void setTemplateId(String templateId) {
        MessageUtil.templateId = templateId;
    }

    //注册完，审批完
    public static String sendTemplateMessage(MessageDTO messageDTO) {
        String at = AccessTokenUtil.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + at;
        WeChatTemDto weChatTemDto = new WeChatTemDto();
        weChatTemDto.setTouser(messageDTO.getOpenid());
        weChatTemDto.setTemplate_id(templateId);
        weChatTemDto.setUrl("");
        Map<String, TemplateDataDto> map = new HashMap<>();
        TemplateDataDto first = new TemplateDataDto();
        first.setValue(messageDTO.getFirst());
        first.setColor("#173177");
        map.put("first", first);
        TemplateDataDto keyword1 = new TemplateDataDto();
        keyword1.setValue(messageDTO.getKeyword1());
        keyword1.setColor("#173177");
        map.put("keyword1", keyword1);
        TemplateDataDto keyword2 = new TemplateDataDto();
        keyword2.setValue(messageDTO.getKeyword2());
        keyword2.setColor("#173177");
        map.put("keyword2", keyword2);
        TemplateDataDto keyword3 = new TemplateDataDto();
        keyword3.setValue(messageDTO.getKeyword3());
        keyword3.setColor("#173177");
        map.put("keyword3", keyword3);
        TemplateDataDto keyword4 = new TemplateDataDto();
        keyword4.setValue(messageDTO.getKeyword4());
        keyword4.setColor("#173177");
        map.put("keyword4", keyword4);
        TemplateDataDto remark = new TemplateDataDto();
        remark.setValue(messageDTO.getRemark());
        remark.setColor("#173177");
        map.put("remark", remark);
        weChatTemDto.setData(map);
        JSONObject jsonObject = JSONObject.fromObject(weChatTemDto);
        String data = jsonObject.toString();
        String result = RequestUtil.post(url, data);
        return result;
    }
}
