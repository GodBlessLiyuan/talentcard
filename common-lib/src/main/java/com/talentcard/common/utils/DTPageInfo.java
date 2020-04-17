package com.talentcard.common.utils;

import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2019/9/24 19:46
 * @description: 适配前端DataTables分页结构
 * @version: 1.0
 */
@Data
public class DTPageInfo<T> {
    private long total;
    private List<T> data;

    public DTPageInfo() {
    }

    public DTPageInfo(long total) {
        this.total = total;
    }

    public DTPageInfo(long total, List<T> data) {
        this(total);
        this.data = data;
    }
}
