package com.talentcard.front.utils;

/**
 * 人才工具类
 *
 * @author ChenXU
 */
public class TalentUtil {
    /**
     * 根据身份证号返回性别
     * @param idCard
     * @return
     */
    public static Byte judgeGenderUtil(String idCard) {
        Integer num = idCard.charAt(16) - '0';
        if (num == 1 || num == 3 || num == 5 || num == 7 || num == 9) {
            return (byte) 1;
        } else {
            return (byte) 2;
        }
    }
}
