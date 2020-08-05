package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IHonourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 10:48
 * @description
 */
@RestController
@RequestMapping("honour")
public class HonourController {
    @Autowired
    private IHonourService iHonourService;

    /**
     * 新增
     *
     * @param name
     * @param description
     * @return
     */
    @PostMapping("add")
    public ResultVO add(HttpSession httpSession,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "description") String description) {
        return iHonourService.add(name, description, httpSession);
    }

    /**
     * 编辑
     *
     * @param honourId
     * @param description
     * @return
     */
    @PostMapping("edit")
    public ResultVO add(HttpSession httpSession,
                        @RequestParam(value = "honourId") Long honourId,
                        @RequestParam(value = "description") String description) {
        return iHonourService.edit(honourId, description, httpSession);
    }

    /**
     * 上下架
     *
     * @param honourId
     * @param status
     * @return
     */
    @PostMapping("upDown")
    public ResultVO upDown(HttpSession httpSession,
                           @RequestParam(value = "honourId") Long honourId,
                           @RequestParam(value = "status") Byte status) {
        return iHonourService.upDown(honourId, status, httpSession);
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
                          @RequestParam(value = "status", required = false) Byte status) {
        return iHonourService.query(pageNum, pageSize, name, status);
    }

    /**
     * 根据id查询单个详情
     *
     * @param honourId
     * @return
     */
    @PostMapping("findOne")
    public ResultVO findOne(@RequestParam(value = "honourId") Long honourId) {
        return iHonourService.findOne(honourId);
    }
}
