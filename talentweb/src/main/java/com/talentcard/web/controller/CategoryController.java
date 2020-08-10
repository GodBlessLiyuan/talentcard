package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:56
 * @description
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 新增
     *
     * @param name
     * @param description
     * @return
     */
    @PostMapping("add")
    public ResultVO add(HttpSession httpSession, @RequestParam(value = "name") String name,
                        @RequestParam(value = "description") String description) {
        return iCategoryService.add(name, description, httpSession);
    }

    /**
     * 编辑
     *
     * @param categoryId
     * @param description
     * @return
     */
    @PostMapping("edit")
    public ResultVO add(HttpSession httpSession, @RequestParam(value = "categoryId") Long categoryId,
                        @RequestParam(value = "description") String description) {
        return iCategoryService.edit(categoryId, description, httpSession);
    }

    /**
     * 上下架
     *
     * @param categoryId
     * @param status
     * @return
     */
    @PostMapping("upDown")
    public ResultVO upDown(HttpSession httpSession, @RequestParam(value = "categoryId") Long categoryId,
                           @RequestParam(value = "status") Byte status) {
        return iCategoryService.upDown(categoryId, status, httpSession);
    }

    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @PostMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "100") int pageSize,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "status", required = false) Byte status,
                          @RequestParam(value = "type", defaultValue = "1") Byte type) {
        return iCategoryService.query(pageNum, pageSize, name, status, type);
    }

    /**
     * 查询单个详情
     *
     * @param categoryId
     * @return
     */
    @PostMapping("findOne")
    public ResultVO findOne(@RequestParam(value = "categoryId") Long categoryId) {
        return iCategoryService.findOne(categoryId);
    }
}
