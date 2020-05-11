package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_config
 * @author 
 */
@Data
public class ConfigPO implements Serializable {
    private String configKey;

    private String configValue;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}