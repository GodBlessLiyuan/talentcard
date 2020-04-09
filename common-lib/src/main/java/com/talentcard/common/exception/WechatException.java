package com.talentcard.common.exception;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 18:00
 * @Description: 微信API用的异常
 */
public class WechatException extends RuntimeException {
    public WechatException() {
        super("微信server出问题啦！");
    }

    public WechatException(String message) {
        super(message);
    }
}
