package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.ScenicDTO;
import com.talentcard.web.service.IScenicService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:03
 * @description: 景区
 * @version: 1.0
 */
@RequestMapping("scenic")
@RestController
public class ScenicController {
    @Autowired
    private IScenicService scenicService;

    /**
     * 景区查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param start
     * @param end
     * @param status
     * @return
     */
    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "start", defaultValue = "") String start,
                          @RequestParam(value = "end", defaultValue = "") String end,
                          @RequestParam(value = "status", defaultValue = "0") Byte status) {

        Map<String, Object> reqMap = new HashMap<>(6);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("status", status);

        return scenicService.query(pageNum, pageSize, reqMap);
    }

    /**
     * 景区新建/编辑
     *
     * @param dto
     * @return
     */
    @RequestMapping("edit")
    public ResultVO edit(@RequestBody ScenicDTO dto) {
        return scenicService.edit(dto);
    }

    /**
     * 景区上/下架
     *
     * @param scenicId
     * @param status
     * @return
     */
    @RequestMapping("status")
    public ResultVO status(@Param("scenicId") Long scenicId, @Param("status") Long status) {
        return scenicService.status(scenicId, status);
    }

    /**
     * 景区详情
     *
     * @param scenicId
     * @return
     */
    @RequestMapping("detail")
    public ResultVO status(@Param("scenicId") Long scenicId) {
        return scenicService.detail(scenicId);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public ResultVO upload(@Param("file") MultipartFile file) {
        return scenicService.upload(file);
    }
}
