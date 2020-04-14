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
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;

    public DTPageInfo() {
    }

    public DTPageInfo(int draw) {
        this.draw = draw;
    }

    public DTPageInfo(int draw, long recordsTotal) {
        this(draw);
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public DTPageInfo(int draw, long recordsTotal, List<T> data) {
        this(draw, recordsTotal);
        this.data = data;
    }
}
