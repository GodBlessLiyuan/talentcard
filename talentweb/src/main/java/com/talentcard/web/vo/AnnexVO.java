package com.talentcard.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/15 15:14
 * @description: 附件
 * @version: 1.0
 */
@Data
public class AnnexVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 附件名称
     */
    private String name;
    /**
     * 下载地址
     */
    private String url;
}
