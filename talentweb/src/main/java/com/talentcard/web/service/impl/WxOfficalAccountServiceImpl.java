package com.talentcard.web.service.impl;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.dto.TemplateDataDTO;
import com.talentcard.web.dto.WeChatTemDTO;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.RequestUtil;
import com.talentcard.web.utils.WebParameterUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: velve
 * @date: Created in 2020/8/21 16:00
 * @description: 微信公众号发送通知服务
 * @version: 1.0
 */
@Service
public class WxOfficalAccountServiceImpl implements IWxOfficalAccountService {

    private static final Logger logger = LoggerFactory.getLogger(WxOfficalAccountServiceImpl.class);
    /**
     * 将符合政策但未申请对应政策的人才进行推送消息
     */
    @Value("${wechat.sendToNotApplyPolicy}")
    private String notApplyPolicy;
    /**
     * 个人申请活动通过后，发送审批通过的通知
     */
    @Value("${wechat.sendToEventPass}")
    private String eventPass;
    /**
     * 个人申请活动拒绝后，发送审批拒绝的通知
     */
    @Value("${wechat.sendToEventReject}")
    private String eventReject;

    /**
     * @param openId     人才openId
     * @param policyName 政策名称
     *                   <p>
     *                   示例：
     *                   通知示例：
     *                   您好，您符合“{{policyName}}”条件，但系统暂未收到您的申请，申请后可享受政策补贴
     *                   审批结果：未申请
     *                   温馨提醒：申请后可享受政策补贴，请及时申请哦~
     * @return 成功：0；为关注公众号：43004
     */
    @Override
    public int messToNotApply(String openId, String policyName) {

        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);

        String first = String.format("您好，您符合“%s”条件，但系统暂未收到您的申请，申请后可享受政策补贴", policyName);
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("未申请");
        messageDTO.setKeyword2("申请后可享受政策补贴，请及时申请哦~");
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());

        try {
            int result = sendTemplateMessage(messageDTO, notApplyPolicy);
            return result;
        } catch (Exception e) {
            logger.error("send message for messToNotApply is error {}", e);
            return -1;
        }
    }

    /**
     * 个人申请活动通过后，发送审批通过的通知
     *
     * @param openId
     * @param eventName
     * @return
     */
    @Override
    public int messToEventAgree(String openId, String eventName, String opinion, long feId, int status) {
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);

        String first = String.format("您好，您申请的“%s”已通过审核。", eventName);
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("通过");
        messageDTO.setKeyword2("请您按时举办活动，如未能如期举行，请提前取消。");
        messageDTO.setRemark("审批意见：" + opinion);
        //微信推送消息详情页
        String url = String.format(FilePathConfig.getStaticPublicWxBasePath()+"activity.html#/activity-talent/detail?feid=%s&status=%s&appid=%s", feId, status,openId);
        messageDTO.setUrl(url);

        try {
            int result = sendTemplateMessage(messageDTO, eventPass);
            return result;
        } catch (Exception e) {
            logger.error("send message for messToEventAgree is error {}", e);
            return -1;
        }
    }


    /**
     * 个人申请活动拒绝后，发送审批拒绝的通知
     *
     * @param openId
     * @param talentName
     * @param eventName
     * @param opinion
     * @return
     */
    @Override
    public int messToEventReject(String openId, String talentName, String eventName, String opinion, long feId, int status) {
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);

        String first = String.format("您好，您申请的“%s”被驳回", eventName);
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1(talentName);
        messageDTO.setKeyword2(DateUtil.date2Str(new Date(), DateUtil.YMD_HMS));
        messageDTO.setKeyword3("驳回");
        messageDTO.setKeyword4(opinion);
        messageDTO.setRemark("您可以按照驳回意见修改后重新提交");
        //微信推送消息详情页
        String url = String.format(FilePathConfig.getStaticPublicWxBasePath()+"activity.html#/activity-talent/detail?feid=%s&status=%s&appid=%s", feId, status,openId);
        messageDTO.setUrl(url);

        try {
            int result = sendTemplateMessage(messageDTO, eventReject);
            return result;
        } catch (Exception e) {
            logger.error("send message for messToEventPass is error {}", e);
            return -1;
        }
    }

    /**
     * 后台管理员取消活动的小程序详情页
     *
     * @param openId     openid
     * @param eventName  活动名称
     * @param opinion    审批意见
     * @param feId       活动id
     * @param status     前台展示状态
     * @return
     */
    @Override
    public int messToEventCancel(String openId, String eventName, String opinion, long feId, int status) {
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);
        String first = String.format("抱歉，您报名的“%s”已取消，给您造成不便,敬请谅解", eventName);
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("已取消");
        messageDTO.setKeyword2("点击查看活动详情");
        messageDTO.setKeyword3("驳回");
        messageDTO.setKeyword4("不满足活动要求。");
        messageDTO.setRemark("取消原因：" + opinion);
        //微信推送消息详情页
        String url = String.format(FilePathConfig.getStaticPublicWxBasePath()+"activity.html#/activity-talent/detail?feid=%s&status=%s&appid=%s", feId, status,openId);
        messageDTO.setUrl(url);

        try {
            int result = sendTemplateMessage(messageDTO, eventPass);
            return result;
        } catch (Exception e) {
            logger.error("send message for messToEventPass is error {}", e);
            return -1;
        }
    }

    /**
     * 后台管理员取消活动
     *
     * @param openId
     * @param eventName
     * @param opinion
     * @return
     */
    @Override
    public int messToBackEndEventCancel(String openId, Long eventId, String eventName, String opinion) {
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);
        String first = String.format("抱歉，您申请的“%s”已取消，给您造成不便,敬请谅解", eventName);
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("已取消");
        messageDTO.setKeyword2("点击查看活动详情");
        messageDTO.setKeyword3("驳回");
        messageDTO.setKeyword4("不满足活动要求。");
        messageDTO.setRemark("取消原因：" + opinion);
        messageDTO.setUrl(WebParameterUtil.getEventDetail() + "?eventId=" + eventId + "&type=2");

        try {
            int result = sendTemplateMessage(messageDTO, eventPass);
            return result;
        } catch (Exception e) {
            logger.error("send message for messToEventPass is error {}", e);
            return -1;
        }
    }

    public int sendTemplateMessage(MessageDTO messageDTO, String template) {
        if (messageDTO == null) {
            return -1;
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
        com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(result);

        return object.getInteger("errcode");
    }
}
