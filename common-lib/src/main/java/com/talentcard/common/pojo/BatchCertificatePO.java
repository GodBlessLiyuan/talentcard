package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_batch_certificate
 * @author 
 */
@Data
public class BatchCertificatePO implements Serializable {
    private Long bcId;

    private String fileName;

    /**
     * 1认证中
2认证结束
     */
    private Byte status;

    private Integer totalNum;

    private Integer successNum;

    private Integer failureNum;

    private String downloadUrl;

    private Long userId;

    private String userName;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}