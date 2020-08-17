package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyTypeDTO;
import com.talentcard.web.service.IPolicyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 16:34 2020/8/10+
 * @ Description：政策类型
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("policyType")
@RestController
public class PolicyTypeController {
    @Autowired
    private IPolicyTypeService policyTypeService;

    @RequestMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return policyTypeService.pageQuery(reqData);
    }

    @RequestMapping("insert")
    public ResultVO insert(HttpServletRequest request, @RequestBody PolicyTypeDTO dto) {
        return policyTypeService.insert(request.getSession(), dto);
    }

    @RequestMapping("queryExIdAndName")
    public ResultVO queryExIdAndName(@RequestBody Map<String, Object> reqData) {
        return policyTypeService.queryExIdAndName(reqData);
    }

    @RequestMapping("update")
    public ResultVO update(HttpServletRequest request, @RequestBody PolicyTypeDTO dto) {
        return policyTypeService.update(request.getSession(), dto);
    }

    @RequestMapping("changeStatus")
    public ResultVO changeStatus(HttpServletRequest request, @RequestBody PolicyTypeDTO dto) {
        return policyTypeService.changeStatus(request.getSession(), dto);
    }
    @RequestMapping("delete")
    public ResultVO delete(HttpServletRequest request, @RequestBody PolicyTypeDTO dto) {
        return policyTypeService.delete(request.getSession(), dto);
    }
}
