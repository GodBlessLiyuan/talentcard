package com.talentcard.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/13 10:00
 * @description: 我的权益 - 申请
 * @version: 1.0
 */
@AllArgsConstructor
@Data
public class PolicyApplyDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long tid;
    private Long pid;
    private String card;
    private String bank;
    private MultipartFile[] files;
}
