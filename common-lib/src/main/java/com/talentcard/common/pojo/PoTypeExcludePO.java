package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * po_type_exclude
 * @author 
 */
@Data
public class PoTypeExcludePO implements Serializable {
    /**
     * 互斥id
     */
    private Long excludeId;

    /**
     * 政策类型id1
     */
    private Long pTid1;

    /**
     * 政策类型id2
     */
    private Long pTid2;

    private static final long serialVersionUID = 1L;
}