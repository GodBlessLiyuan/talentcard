package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 14:51
 * @description 员工
 */
@RequestMapping("staff")
@RestController
public class StaffController {
    @Autowired
    private IStaffService iStaffService;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "activitySecondContentName", required = false) String activitySecondContentName,
                          @RequestParam(value = "activityFirstContentId", required = false) Integer activityFirstContentId,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                          @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!startTime.equals("")) {
            startTime = startTime + " 00:00:00";
        }
        if (!endTime.equals("")) {
            endTime = endTime + " 23:59:59";
        }
        hashMap.put("activitySecondContentName", activitySecondContentName);
        hashMap.put("activityFirstContentId", activityFirstContentId);
        if (null != name) {
            name = name.replaceAll("%", "\\\\%");
        }
        hashMap.put("name", name);
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        return iStaffService.query(pageNum, pageSize, hashMap);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestParam("staffId") Long staffId) {
        return iStaffService.delete(staffId);
    }
}
