package com.talentcard.web.utils;

import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.dto.TemplateDataDTO;
import com.talentcard.web.dto.WeChatTemDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-21 14:01
 * @description: TODO
 * @version: 1.0
 */
@Component
public class MessageUtil {


    private static String templateId;

    private static String templateId2;

    private static String policyApprovalTemplate;

    private static String editTalentTemplate;

    private static String sendTalentUnConfirmTemplate;

    @Value("${wechat.template_id}")
    public void setTemplateId(String templateId) {
        MessageUtil.templateId = templateId;
    }

    @Value("${wechat.template_id2}")
    public void setTemplateId2(String templateId2) {
        MessageUtil.templateId2 = templateId2;
    }

    @Value("${wechat.policyApprovalTemplate}")
    public void setPolicyApprovalTemplate(String policyApprovalTemplate) {
        MessageUtil.policyApprovalTemplate = policyApprovalTemplate;
    }

    @Value("${wechat.editTalentTemplate}")
    public void setEditTalentTemplate(String editTalentTemplate) {
        MessageUtil.editTalentTemplate = editTalentTemplate;
    }
    @Value("${wechat.sendTalentUnConfirmTemplate}")
    public void setSendTalentUnConfirmTemplate(String sendTalentUnConfirmTemplate) {
        MessageUtil.sendTalentUnConfirmTemplate = sendTalentUnConfirmTemplate;
    }
    //注册完，审批完
    public static String sendTemplateMessage(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return "";
        }
        String at = AccessTokenUtil.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + at;
        WeChatTemDTO weChatTemDto = new WeChatTemDTO();
        weChatTemDto.setTouser(messageDTO.getOpenid());
        //判断用哪一种模板
        if (messageDTO.getTemplateId() == 1) {
            //领卡通知
            weChatTemDto.setTemplate_id(templateId);
        } else if (messageDTO.getTemplateId() == 2) {
            //驳回通知
            weChatTemDto.setTemplate_id(templateId2);
        } else if (messageDTO.getTemplateId() == 3) {
            //政策审批
            weChatTemDto.setTemplate_id(policyApprovalTemplate);
        } else if (messageDTO.getTemplateId() == 4) {
            //编辑人才
            weChatTemDto.setTemplate_id(editTalentTemplate);
        }else if(messageDTO.getTemplateId()==5){
            weChatTemDto.setTemplate_id(sendTalentUnConfirmTemplate);
        }
        weChatTemDto.setUrl(messageDTO.getUrl());
        Map<String, TemplateDataDTO> map = new HashMap<>(6);

        TemplateDataDTO first = new TemplateDataDTO();
        first.setValue(messageDTO.getFirst());
        first.setColor("#173177");
        map.put("first", first);

        TemplateDataDTO keyword1 = new TemplateDataDTO();
        keyword1.setValue(messageDTO.getKeyword1());
        keyword1.setColor("#173177");
        map.put("keyword1", keyword1);

        TemplateDataDTO keyword2 = new TemplateDataDTO();
        keyword2.setValue(messageDTO.getKeyword2());
        keyword2.setColor("#173177");
        map.put("keyword2", keyword2);

        TemplateDataDTO keyword3 = new TemplateDataDTO();
        keyword3.setValue(messageDTO.getKeyword3());
        keyword3.setColor("#173177");
        map.put("keyword3", keyword3);

        TemplateDataDTO keyword4 = new TemplateDataDTO();
        keyword4.setValue(messageDTO.getKeyword4());
        keyword4.setColor("#173177");
        map.put("keyword4", keyword4);

        TemplateDataDTO remark = new TemplateDataDTO();
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
