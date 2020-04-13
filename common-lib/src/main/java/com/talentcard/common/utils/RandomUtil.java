package com.talentcard.common.utils;

import java.util.Random;

/**
 * @author: xiahui
 * @date: Created in 2020/3/18 20:52
 * @description: 随机工具
 * @version: 1.0
 */
public class RandomUtil {
    /**
     * 生成随机数
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
