package com.talentcard.wechat.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talentcard.wechat.dto.BaseMessageDto;
import com.talentcard.wechat.service.WxCardService;
import com.talentcard.wechat.utils.CommonUtil;
import com.talentcard.wechat.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-20 9:39
 * @description: TODO
 * @version: 1.0
 */
@Service
public class WxCardServiceImpl implements WxCardService {

    private static final String TOKEN ="godinsec";


    /**
     * 接收用户消息
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public  Map<String,String> parseRequest(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        try{
            SAXReader reader = new SAXReader();
            InputStream inputStream=request.getInputStream();
            //读取输入流，获取文档对象
            Document document = reader.read(inputStream);
            //根据文档对象获取根节点
            Element root = document.getRootElement();
            //获取根节点的所有字节点
            List<Element> elements = root.elements();
            for(Element e : elements){
                map.put(e.getName(),e.getStringValue());
            }
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 接入验证
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     */
    public boolean check(String timestamp,String nonce,String signature){
       /* 开发者通过检验signature对请求进行校验（下面有校验方式）。
        若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：

        1）将token、timestamp、nonce三个参数进行字典序排序
        2）将三个参数字符串拼接成一个字符串进行sha1加密
        3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信*/
        String[] strs = new String[]{TOKEN,timestamp,nonce};
        Arrays.sort(strs);

        String str = strs[0]+strs[1]+strs[2];
        String mgsig = CommonUtil.sha1(str);

        return  mgsig.equalsIgnoreCase(signature);
    }

    /**
     * 被动回复消息
     * @param requestMap
     * @return
     */
    public  String getResponse(Map<String,String> requestMap){
        BaseMessageDto msg=null;
        String msgType =requestMap.get("MsgType");
        switch(msgType){
            case "text":

                break;
            case "image":

                break;
            case "voice":

                break;
            case "video":

                break;
            case "music":

                break;
            case "news":

                break;
            case "event":
                msg = dealEvent(requestMap);
                break;
        }

        //把消息对象处理为xml数据包
        return beanToXml(msg);
    }

    //处理event推送
    public static BaseMessageDto dealEvent(Map<String,String> requestMap){
        String event = requestMap.get("Event");
        BaseMessageDto msg=null;
        if(event.equalsIgnoreCase("CLICK")){
//            msg= dealClick(requestMap);
        }
        return msg;
    }

    //把对象处理为xml数据包
    private  static  String beanToXml(BaseMessageDto msg){
        String str = XmlUtils.toXml(msg);
        return str;
    }
}
