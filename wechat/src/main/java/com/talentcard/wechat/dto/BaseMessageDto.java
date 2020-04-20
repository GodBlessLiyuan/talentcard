package com.talentcard.wechat.dto;

import com.talentcard.wechat.utils.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-20 18:10
 * @description: TODO
 * @version: 1.0
 */
public class BaseMessageDto {

    @XStreamAlias("ToUserName")
    @XStreamCDATA
    private String toUserName;


    @XStreamAlias("FromUserName")
    @XStreamCDATA
    private String fromUserName;

    @XStreamAlias("CreateTime")
    private String createTime;


    @XStreamAlias("MsgType")
    @XStreamCDATA
    private String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public BaseMessageDto(Map<String,String> requestMap){
        this.toUserName = requestMap.get("ToUserName");
        this.fromUserName = requestMap.get("FromUserName");
        this.createTime = System.currentTimeMillis()/1000+"";
    }

}
