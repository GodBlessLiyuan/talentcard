package com.talentcard.front.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 19:17
 * @description: 我的申请 - 详情
 * @version: 1.0
 */
@Data
public class PolicyApplyDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String applyDate;
    private String status;
    private String name;
    private String card;
    private String bank;
    private List<String> annexes;
}
