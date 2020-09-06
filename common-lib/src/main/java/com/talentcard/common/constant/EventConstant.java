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
    public static final int APPLY = 1;
    /**
     * 已同意（已通过，未开始）--小程序，后台
     */
    public static final int AGREE = 2;
    /**
     * 已驳回； —小程序
     */
    public static final int REJECT = 3;
    /**
     * 管理员取消； — 小程序，后台
     */
    public static final int ADMINCANCEL = 4;
    /**
     * 用户取消； —小程序
     */
    public static final int USERCANCEL = 5;
    /**
     * 报名中； —小程序，后台
     */
    public static final int ING = 6;
    /**
     * 结束： -- 小程序，后台
     */
    public static final int FINISH = 7;
    /**
     * 下架状态： - 下架
     */
    public static final int STOP = 8;
    /**
     * 报名已结束
     */
    public static final int REGISTRATION_IS_OVER = 9;


    /**
     * 根据状态返回前端需要的状态值
     * @param startTime
     * @param endTime
     * @param status
     * @param upDown
     * @return
     */
    public static int getStatus(Long currentTime, Long startTime, Long endTime, Byte status, Byte upDown) {
        if (upDown == 2) {
            //下架
            return EventConstant.STOP;
        } else if (status == EventConstant.ADMINCANCEL) {
            //管理员取消
            return EventConstant.ADMINCANCEL;
        } else if (status == EventConstant.USERCANCEL) {
            //用户取消
            return EventConstant.USERCANCEL;
        } else if (status == EventConstant.REJECT) {
            //驳回
            return EventConstant.REJECT;
        } else if (status == EventConstant.APPLY) {
            //提交审批
            return EventConstant.APPLY;
        } else if (status == EventConstant.AGREE) {
            //已同意（已通过，未开始）
            if (startTime > currentTime) {
                //报名中
                return EventConstant.ING;
            } else if (currentTime > startTime && endTime < endTime) {
                //报名已结束
                return EventConstant.REGISTRATION_IS_OVER;
            } else if (currentTime > endTime) {
                //已结束
                return EventConstant.FINISH;
            }
        }
        return -1;
    }
}
