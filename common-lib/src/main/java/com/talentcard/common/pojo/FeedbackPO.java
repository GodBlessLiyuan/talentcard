package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_feedback
 * @author 
 */
@Data
public class FeedbackPO implements Serializable {
    private Long feedbackId;

    private Long talentId;

    private String contact;

    private Date createTime;

    private String content;

    private String picture;

    private static final long serialVersionUID = 1L;
}