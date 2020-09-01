package com.talentcard.miniprogram.controller;


import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.TalentActivitiesDTO;
import com.talentcard.miniprogram.service.ITalentActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:32 2020/8/28
 * @ Description：人才活动
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("activities")
@RestController
public class TalentActivitiesController {
    @Autowired
    private ITalentActivitiesService talentActivitiesService;

    @RequestMapping("insert")
    public ResultVO insert(HttpServletRequest request, @RequestBody TalentActivitiesDTO dto) {
        return talentActivitiesService.insert(request.getSession(), dto);
    }

    @RequestMapping("update")
    public ResultVO update(HttpServletRequest request, @RequestBody TalentActivitiesDTO dto) {
        return talentActivitiesService.update(request.getSession(), dto);
    }

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return talentActivitiesService.pageQuery(reqData);
    }

    @RequestMapping("cancel")
    public ResultVO cancel(HttpServletRequest request, @RequestBody Map<String, Object> reqData) {
        return talentActivitiesService.cancel(request.getSession(), reqData);
    }

    @RequestMapping("queryTalentByOpenId")
    public ResultVO queryTalentByOpenId(@RequestBody Map<String, Object> reqData) {
        return talentActivitiesService.queryTalentByOpenId(reqData);
    }

    @RequestMapping("queryAllPlace")
    public ResultVO queryAllPlace() {
        return talentActivitiesService.queryAllPlace();
    }

    @RequestMapping("queryByPlaceAndDate")
    public ResultVO queryByPlaceAndDate(@RequestBody Map<String, Object> reqData) {
        return talentActivitiesService.queryByPlaceAndDate(reqData);
    }
    @RequestMapping("queryPlaceById")
    public ResultVO queryPlaceById(@RequestBody Map<String, Object> reqData) {
        return talentActivitiesService.queryPlaceById(reqData);
    }
    @RequestMapping("queryByFeid")
    public ResultVO queryByFeid(@RequestBody Map<String, Object> reqData) {
        return talentActivitiesService.queryByFeid(reqData);
    }
}