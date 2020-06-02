package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.BannerDTO;
import com.talentcard.web.service.IBannerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 19:55
 * @description: banner配置
 * @version: 1.0
 */
@RequestMapping("banner")
@RestController
public class BannerController {
    @Autowired
    private IBannerService bannerService;

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @param start
     * @param end
     * @return
     */
    @RequestMapping("query")
    private ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(value = "name", defaultValue = "") String name,
                           @RequestParam(value = "status", defaultValue = "0") Byte status,
                           @RequestParam(value = "start", defaultValue = "") String start,
                           @RequestParam(value = "end", defaultValue = "") String end) {
        Map<String, Object> reqMap = new HashMap<>(6);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("status", status);

        return bannerService.query(pageNum, pageSize, reqMap);
    }

    /**
     * banner配置 新建
     *
     * @param dto
     * @return
     */
    @RequestMapping("insert")
    public ResultVO insert(@RequestBody BannerDTO dto) {
        return bannerService.insert(dto);
    }

    /**
     * 上/下架
     *
     * @param bid
     * @param status
     * @return
     */
    @RequestMapping("status")
    public ResultVO status(@Param("bid") Long bid, @Param("status") Byte status) {
        return bannerService.status(bid, status);
    }

    /**
     * 删除
     *
     * @param bid
     * @return
     */
    @RequestMapping("delete")
    public ResultVO delete(@Param("bid") Long bid) {
        return bannerService.delete(bid);
    }
}
