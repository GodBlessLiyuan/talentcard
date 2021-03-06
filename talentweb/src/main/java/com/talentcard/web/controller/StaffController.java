package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IStaffService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        HashMap<String, Object> hashMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        if (!StringUtils.isEmpty(endTime)) {
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
    public ResultVO delete(HttpServletRequest request, @RequestParam("staffId") Long staffId) {
        return iStaffService.delete(request.getSession(), staffId);
    }
}
