package com.talentcard.common.constant;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 9:03 2020/10/14
 * @ Description：人才卡追踪常量
 * @ Modified By：
 * @ Version:     1.0
 */
public class TrackConstant {

    /**
     * 1：人才追踪
     */
    public static final Byte TALENT_TRACK = 1;
    /**
     * 2：政策追踪
     */
    public static final Byte POLICY_TRACK = 2;
    /**
     * 3：服务追踪
     */
    public static final Byte SERVICE_TRACK = 3;

    /**
     * 1：人才追踪
     * <p>
     * 1：注册；2：提交；3：通过；4：驳回；5：领取
     */
    public static final Byte TALENT_REGISTER = 1;
    public static final Byte TALENT_SUBMIT = 2;
    public static final Byte TALENT_PASS = 3;
    public static final Byte TALENT_REJECT = 4;
    public static final Byte TALENT_RECEIVE = 5;


    /**
     * 2：政策追踪
     * <p>
     * 1：申请；2：驳回；3：通过
     */
    public static final Byte POLICY_APPLY = 1;
    public static final Byte POLICY_REJECT = 2;
    public static final Byte POLICY_PASS = 3;

    /**
     * 3：服务追踪
     * <p>
     * 1：旅游；2：农家乐
     */
    public static final Byte SERVICE_TRIP = 1;
    public static final Byte SERVICE_FARMHOURSE = 2;
}
