package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.FarmhouseDTO;
import com.talentcard.web.service.IFarmhouseService;
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
 * @date: Created in 2020/5/11 18:41
 * @description: 农家乐
 * @version: 1.0
 */
@RequestMapping("farmhouse")
@RestController
public class FarmhouseController {
    @Autowired
    private IFarmhouseService farmhouseService;

    /**
     * 查询
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
        reqMap.put("name", name);
        reqMap.put("status", status);
        reqMap.put("start", start);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("end", end);

        return farmhouseService.query(pageNum, pageSize, reqMap);
    }

    /**
     * 新增/编辑
     *
     * @param dto
     * @return
     */
    @RequestMapping("edit")
    public ResultVO edit(@RequestBody FarmhouseDTO dto) {
        return farmhouseService.edit(dto);
    }

    /**
     * 上架/下架
     *
     * @param farmhouseId
     * @param status
     * @return
     */
    @RequestMapping("status")
    public ResultVO status(@Param("farmhouseId") Long farmhouseId, @Param("status") Long status) {
        return farmhouseService.status(farmhouseId, status);
    }

    /**
     * 详情
     *
     * @param farmhouseId
     * @return
     */
    @RequestMapping("detail")
    public ResultVO status(@Param("farmhouseId") Long farmhouseId) {
        return farmhouseService.detail(farmhouseId);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public ResultVO upload(@Param("file") MultipartFile file) {
        return farmhouseService.upload(file);
    }
}
