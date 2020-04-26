package com.talentcard.web.controller;

import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.IPolicyService;
import com.talentcard.web.vo.PolicyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:18
 * @description: 人才政策管理
 * @version: 1.0
 */
@RequestMapping("policy")
@RestController
public class PolicyController {
    @Autowired
    private IPolicyService service;

    @RequestMapping("query")
    public PageInfoVO<PolicyVO> query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(value = "start", defaultValue = "") String start,
                                      @RequestParam(value = "end", defaultValue = "") String end,
                                      @RequestParam(value = "name", defaultValue = "") String name,
                                      @RequestParam(value = "num", defaultValue = "") String num,
                                      @RequestParam(value = "apply", defaultValue = "0") Byte apply) {

        HashMap<String, Object> hashMap = new HashMap<>(6);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        hashMap.put("start", start);
        hashMap.put("end", end);
        hashMap.put("name", name);
        hashMap.put("num", num);
        hashMap.put("apply", apply);

        return service.query(pageNum, pageSize, hashMap);
    }

    @RequestMapping("insert")
    public ResultVO insert(HttpSession session, @RequestBody PolicyDTO dto) {
        return service.insert(session, dto);
    }

    @RequestMapping("update")
    public ResultVO update(HttpSession session, @RequestBody PolicyDTO dto) {
        return service.update(session, dto);
    }

    @RequestMapping("delete")
    public ResultVO update(@RequestParam(value = "pid") Long pid) {
        return service.delete(pid);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "pid") Long pid) {
        return service.detail(pid);
    }
}
