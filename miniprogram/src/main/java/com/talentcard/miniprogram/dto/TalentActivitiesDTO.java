package com.talentcard.miniprogram.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:30 2020/8/28
 * @ Description：人才活动个人
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class TalentActivitiesDTO implements Serializable {
        private static final long SerialVersionUID = 1L;
        /**
         * 活动id
         */
        private Long feid;

        /**
         * 活动名称
         */
        private String name;
        /**
         * 活动时间
         */
        private String time;
        /**
         * 活动场地id
         */
        private Long efid;
        /**
         * 活动时长
         */
        private Double duration;
        /**
         * 活动参与人数
         */
        private Integer num;
        /**
         * 活动日期
         */
        private String date;
        /**
         * 时间间隔
         */
        private String[] interval;
        /**
         * 活动发起方
         */
        private String sponsor;
        /**
         * 活动详情
         */
        private String detail;
        /**
         * 联系人
         */
        private String cperson;
        /**
         * 联系方式
         */
        private String phone;
        /**
         * 活动图片
         */
        private String picture;
        /**
         * 人才ID
         */
        private Long tid;
        /**
         * 人才openid
         */
        private String  oid;
        /**
         * 1：已同意；2：已驳回；3：待审批
         */
        private Byte status;
        /**
         * 创建时间
         */
        private Date ctime;
        /**
         * 活动开始时间
         */
        private Date stime;
        /**
         * 活动结束时间
         */
        private Date etime;
        /**
         * 验证码
         */
        private String vcode;
    }

