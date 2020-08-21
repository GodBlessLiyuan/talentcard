package com.talentcard.web.service.impl;

import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.dto.TemplateDataDTO;
import com.talentcard.web.dto.WeChatTemDTO;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.RequestUtil;
import com.talentcard.web.utils.WebParameterUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: velve
 * @date: Created in 2020/8/21 16:00
 * @description: TODO
 * @version: 1.0
 */
@Service
public class WxOfficalAccountServiceImpl implements IWxOfficalAccountService {

    /**
     * 将符合政策但未申请对应政策的人才进行推送消息
     */
    @Value("wechat.sendToNotApplyPolicy")
    private String notApplyPolicy;

    /**
     * @param openId     人才openId
     * @param policyName 政策名称
     *                   <p>
     *                   示例：
     *                   通知示例：
     *                   您好，您符合“{{policyName}}”条件，但系统暂未收到您的申请，申请后可享受政策补贴
     *                   审批结果：未申请
     *                   温馨提醒：申请后可享受政策补贴，请及时申请哦~
     * @return
     */
    @Override
    public int messToNotApply(String openId, String policyName) {

        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);

        String first = String.format("您好，您符合“{%s}”条件，但系统暂未收到您的申请，申请后可享受政策补贴", policyName);
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("未申请");
        messageDTO.setKeyword2("申请后可享受政策补贴，请及时申请哦~");
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());

        sendTemplateMessage(messageDTO, notApplyPolicy);
        return 0;
    }


    public String sendTemplateMessage(MessageDTO messageDTO, String template) {
        if (messageDTO == null) {
            return "";
        }
        String at = AccessTokenUtil.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + at;
        WeChatTemDTO weChatTemDto = new WeChatTemDTO();
        weChatTemDto.setTouser(messageDTO.getOpenid());

        weChatTemDto.setTemplate_id(template);

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
