package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:31 2020/8/28
 * @ Description：前台人才活动
 * @ Modified By：
 * @ Version:     1.0*/

public interface IActivitiesApprovalService {

    ResultVO approvalQuery(Map<String, Object> reqData);
    ResultVO cancel(HttpSession session, Map<String, Object> reqData);
    ResultVO queryTalentByOpenId(Map<String, Object> reqData);
    ResultVO queryAllPlace();
    ResultVO queryByPlaceAndDate(Map<String, Object> reqData);
    ResultVO queryPlaceById(Map<String, Object> reqData);
    ResultVO queryByFeid(Map<String, Object> reqData);
    ResultVO queryApprovalByFeid(Map<String, Object> reqData);
    ResultVO approval(HttpSession session,Map<String, Object> reqData);
    ResultVO detail(Map<String, Object> reqData);
    ResultVO notApprovalNum();
}
