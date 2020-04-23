package com.talentcard.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;

/**
 * @author: xiahui
 * @date: Created in 2019/10/17 18:44
 * @description: 分页查询
 * @version: 1.0
 */
public class PageHelper extends com.github.pagehelper.PageHelper {

    /**
     * 适配前端DataTable传递的数据
     *
     * @param pageNum
     * @param pageSize
     * @param <E>
     * @return
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize) {
        return PageMethod.startPage(pageNum, pageSize);
    }
}
