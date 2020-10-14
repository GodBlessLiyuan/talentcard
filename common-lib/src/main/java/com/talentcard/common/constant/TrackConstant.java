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
    private Byte TALENT_TRACK = 1;
    /**
     * 2：政策追踪
     */
    private Byte POLICY_TRACK = 1;
    /**
     * 3：服务追踪
     */
    private Byte SERVICE_TRACK = 1;

    /**
     * 1：人才追踪
     * <p>
     * 1：注册；2：提交；3：通过；4：驳回；5：领取
     */
    private Byte TALENT_REGISTER = 1;
    private Byte TALENT_SUBMIT = 2;
    private Byte TALENT_PASS = 3;
    private Byte TALENT_REJECT = 4;
    private Byte TALENT_RECEIVE = 4;


    /**
     * 2：政策追踪
     * <p>
     * 1：申请；2：驳回；3：通过
     */
    private Byte POLICY_APPLY = 1;
    private Byte POLICY_REJECT = 2;
    private Byte POLICY_PASS = 3;

    /**
     * 3：服务追踪
     * <p>
     * 1：领取；2：使用；3：享受
     */
    private Byte SERVICE_RECEIVED = 1;
    private Byte SERVICE_USED = 2;
    private Byte SERVICE_ENJOYED = 3;

}
