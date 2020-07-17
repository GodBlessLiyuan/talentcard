package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-16 09:03
 */
public interface IFarmhouseUseDailyService {
    ResultVO init_daily();

    ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map);

    ResultVO export(Map<String, Object> map, HttpServletResponse response);
}
