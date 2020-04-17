package com.talentcard.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2019/9/24 19:46
 * @description: 分页信息VO
 * @version: 1.0
 */
@Data
public class PageInfoVO<T> {
    private long total;
    private List<T> data;

    public PageInfoVO(long total) {
        this.total = total;
    }

    public PageInfoVO(long total, List<T> data) {
        this(total);
        this.data = data;
    }
}
