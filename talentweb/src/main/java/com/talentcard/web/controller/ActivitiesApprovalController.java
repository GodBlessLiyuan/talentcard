package com.talentcard.web.controller;


import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IActivitiesApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

import static com.talentcard.common.utils.DateUtil.YMD_HMS;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:32 2020/8/28
 * @ Description：人才活动
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("activities")
@RestController
public class ActivitiesApprovalController {
    @Autowired
    private IActivitiesApprovalService activitiesApprovalService;


    @RequestMapping("approvalQuery")
    public ResultVO approvalQuery(@RequestBody Map<String, Object> reqData) {
        reqData.put("currentTime", DateUtil.date2Str(new Date(),YMD_HMS));
        return activitiesApprovalService.approvalQuery(reqData);
    }

    @RequestMapping("cancel")
    public ResultVO cancel(HttpServletRequest request, @RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.cancel(request.getSession(), reqData);
    }

    @RequestMapping("queryTalentByOpenId")
    public ResultVO queryTalentByOpenId(@RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.queryTalentByOpenId(reqData);
    }

    @RequestMapping("queryAllPlace")
    public ResultVO queryAllPlace() {
        return activitiesApprovalService.queryAllPlace();
    }

    @RequestMapping("queryByPlaceAndDate")
    public ResultVO queryByPlaceAndDate(@RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.queryByPlaceAndDate(reqData);
    }
    @RequestMapping("queryPlaceById")
    public ResultVO queryPlaceById(@RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.queryPlaceById(reqData);
    }
    @RequestMapping("queryByFeid")
    public ResultVO queryByFeid(@RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.queryByFeid(reqData);
    }

    @RequestMapping("queryApprovalByFeid")
    public ResultVO queryApprovalByFeid(@RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.queryApprovalByFeid(reqData);
    }
    @RequestMapping("approval")
    public ResultVO approval(HttpServletRequest request, @RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.approval(request.getSession(),reqData);
    }
    @RequestMapping("detail")
    public ResultVO detail(@RequestBody Map<String, Object> reqData) {
        return activitiesApprovalService.detail(reqData);
    }
}