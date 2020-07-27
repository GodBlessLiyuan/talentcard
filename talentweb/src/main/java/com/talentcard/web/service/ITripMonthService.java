package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ITripMonthService {
    ResultVO init_month();

    ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map);

    ResultVO export(Map<String, Object> map, HttpServletResponse response);

    ResultVO total(Map<String, Object> map);
}
