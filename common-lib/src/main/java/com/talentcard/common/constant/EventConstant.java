package com.talentcard.common.constant;

/**
 * @author: velve
 * @date: Created in 2020/9/5 20:37
 * @description: TODO
 * @version: 1.0
 */
public class EventConstant {
    /**
     * 提交待审批； -- 小程序
     */
    private static int APPLY = 1;
    /**
     * 已同意（已通过，未开始）--小程序，后台
     */
    private static int AGREE = 2;
    /**
     * 已驳回； —小程序
     */
    private static int REJECT = 3;
    /**
     * 管理员取消； — 小程序，后台
     */
    private static final int ADMINCANCEL = 4;
    /**
     * 用户取消； —小程序
     */
    private static final int USERCANCEL = 5;
    /**
     * 报名中； —小程序，后台
     */
    private static final int ING = 6;
    /**
     * 结束： -- 小程序，后台
     */
    private static final int FINISH = 7;

}
