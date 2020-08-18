package com.talentcard.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/24 9:56
 * @description: 分页查询工具
 * @version: 1.0
 */
public class PageQueryUtil extends PageHelper {
    private static final String KEY_PAGE_NUM = "pageNum";
    private static final String KEY_PAGE_SIZE = "pageSize";
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 1000000000;

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

        return startPage(pageNum, pageSize);
    }
}
