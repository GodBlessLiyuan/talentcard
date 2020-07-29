package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.IPolicyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
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
        hashMap.put("name", name.replaceAll("%", "\\\\%"));
        hashMap.put("num", num);
        hashMap.put("apply", apply);

        return service.query(pageNum, pageSize, hashMap);
    }

    @RequestMapping("insert")
    public ResultVO insert(HttpServletRequest request, @RequestBody PolicyDTO dto) {
        return service.insert(request.getSession(), dto);
    }

    @RequestMapping("update")
    public ResultVO update(HttpServletRequest request, @RequestBody PolicyDTO dto) {
        return service.update(request.getSession(), dto);
    }

    @RequestMapping("delete")
    public ResultVO update(HttpServletRequest request, @RequestParam(value = "pid") Long pid) {
        return service.delete(request.getSession(), pid);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "pid") Long pid) {
        return service.detail(pid);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @RequestMapping("upload")
    public ResultVO upload(HttpServletRequest request, @Param("file") MultipartFile file) {
        return service.upload(request.getSession(), file);
    }
}
