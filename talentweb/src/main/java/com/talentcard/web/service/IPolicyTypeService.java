package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.dto.PolicyTypeDTO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 10:15 2020/8/11
 * @ Description：政策类型Service接口
 * @ Modified By：
 * @ Version:     1.0
 */
public interface IPolicyTypeService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO insert(HttpSession session, PolicyTypeDTO dto);

    ResultVO queryExIdAndName(Map<String, Object> reqData);

    ResultVO update(HttpSession session, PolicyTypeDTO dto);

    ResultVO changeStatus(HttpSession session, PolicyTypeDTO dto);

    ResultVO delete(HttpSession session, PolicyTypeDTO dto);
   }
