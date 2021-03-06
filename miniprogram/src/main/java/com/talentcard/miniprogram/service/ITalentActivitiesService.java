package com.talentcard.miniprogram.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.TalentActivitiesDTO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:31 2020/8/28
 * @ Description：前台人才活动
 * @ Modified By：
 * @ Version:     1.0*/

public interface ITalentActivitiesService {

    ResultVO insert(HttpSession session, TalentActivitiesDTO dto);
    ResultVO update(HttpSession session, TalentActivitiesDTO dto);
    ResultVO pageQuery(Map<String, Object> reqData);
    ResultVO cancel(HttpSession session, Map<String, Object> reqData);
    ResultVO queryTalentByOpenId(Map<String, Object> reqData);
    ResultVO queryAllPlace();
    ResultVO queryByPlaceAndDate(Map<String, Object> reqData);
    ResultVO queryPlaceById(Map<String, Object> reqData);
    ResultVO queryByFeid(Map<String, Object> reqData);
    ResultVO detail(Map<String, Object> reqData);
}
