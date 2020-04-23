package com.talentcard.wechat.controller;

import com.talentcard.wechat.service.IEventService;
import com.talentcard.wechat.service.WxCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-20 9:32
 * @description: TODO
 * @version: 1.0
 */
@RequestMapping("WxCard")
@RestController
public class WxCardController {
    private static final Logger logger = LoggerFactory.getLogger(WxCardController.class);
    @Autowired
    private WxCardService wxCardService;
    @Autowired
    private IEventService iEventService;


    //接收消息和事件推送
    @RequestMapping(value = "card", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        response.setCharacterEncoding("utf8");
        response.setContentType("text/html;charset=UTF-8");

        //处理消息和事件推送
        try {
            //接收消息
            Map<String, String> requestMap = wxCardService.parseRequest(request);
            String openId = requestMap.get("FromUserName");
            String event = requestMap.get("Event");
            if(event.equalsIgnoreCase("user_get_card")){
                //用户领取卡事件，激活接口
                logger.info("用户开始领卡");
                iEventService.activate(openId);
                logger.info("用户领取卡成功");
            }else if(event.equalsIgnoreCase("user_del_card")){
                //用户删除卡券事件，删除接口
                logger.info("用户开始删卡");
                iEventService.delete(openId);
                logger.info("用户删除卡成功");
            }


            //准备回复的数据
            String respxml = wxCardService.getResponse(requestMap);
            PrintWriter out = response.getWriter();
            out.print(respxml);
            out.flush();
            out.close();
            logger.info("回复成功", respxml);

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("接收消息失败");

        }
    }

    @RequestMapping(value = "card", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        timestamp	时间戳
        nonce	随机数
        echostr	随机字符串*/
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //校验请求
        if (wxCardService.check(timestamp, nonce, signature)) {
            PrintWriter out = response.getWriter();
            //原样返回
            out.print(echostr);
            out.flush();
            out.close();
            logger.info("连接服务器成功");

        } else {
            logger.info("连接服务器失败");
        }

    }
}
