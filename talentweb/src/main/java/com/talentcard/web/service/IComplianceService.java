package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:31 2020/8/20
 * @ Description：符合人数详情查询接口
 * @ Modified By：
 * @ Version:     1.0*/

public interface IComplianceService {

    ResultVO queryNum(Map<String, Object> reqData);
    ResultVO pageQuery(Map<String, Object> reqData);
    ResultVO exportExcel(Map<String, Object> reqData, HttpServletResponse response);
    ResultVO pushRecordQuery(Long pId);

}
