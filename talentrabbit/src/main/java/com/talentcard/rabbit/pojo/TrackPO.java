package com.talentcard.rabbit.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * t_track
 *
 * @author
 */
@Data
public class TrackPO implements Serializable {
    private Long trackId;

    private Date trackTime;

    /**
     * 1：人才追踪
     * 2：政策追踪
     * 3：服务追踪
     */
    private Byte trackType;

    /**
     * 追踪类型 = 1：1：注册；2：提交；3：通过；4：驳回；5：领取
     * 追踪类型 = 2：1：申请；2：驳回；3：通过
     * 追踪类型 = 3：1：领取；2：使用；3：享受
     */
    private Byte trackNode;

    private String trackContent;

    private static final long serialVersionUID = 1L;
}