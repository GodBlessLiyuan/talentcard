package com.talentcard.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2019/10/17 18:44
 * @description: 分页查询
 * @version: 1.0
 */
public class PageHelper extends com.github.pagehelper.PageHelper {

    private static final String KEY_PAGE_NUM = "pageNum";
    private static final String KEY_PAGE_SIZE = "pageSize";
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 1000000000;

    /**
     * 适配前端DataTable传递的数据，当当前页面数大于查询总数时，跳转到最后一页，不会查询出来是空值
     *
     * @param pageNum
     * @param pageSize
     * @param <E>
     * @return
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize) {
        return PageMethod.startPage(pageNum, pageSize, DEFAULT_COUNT,true,(Boolean)null);
    }

    /**
     * 分页查询
     *
     * @param reqData
     * @param <E>
     * @return
     */
    public static <E> Page<E> startPage(Map<String, Object> reqData) {
        int pageNum = reqData.get(KEY_PAGE_NUM) == null ? DEFAULT_PAGE_NUM : (int) reqData.get(KEY_PAGE_NUM);
        int pageSize = reqData.get(KEY_PAGE_SIZE) == null ? DEFAULT_PAGE_SIZE : (int) reqData.get(KEY_PAGE_SIZE);

        return startPage(pageNum, pageSize, DEFAULT_COUNT,true,(Boolean)null);
    }
}
