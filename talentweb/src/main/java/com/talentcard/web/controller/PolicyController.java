package com.talentcard.web.controller;

import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.IPolicyService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                          @RequestParam(value = "start", required = false) String start,
                          @RequestParam(value = "end", required = false) String end,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "num", defaultValue = "") String num,
                          @RequestParam(value = "status", required = false) Byte status,
                          @RequestParam(value = "policyType", required = false) Byte policyType,
                          @RequestParam(value = "roleId", required = false) Long roleId,
                          @RequestParam(value = "roleType", required = false) Byte roleType) {

        HashMap<String, Object> hashMap = new HashMap<>(10);
        if (!StringUtils.isEmpty(start)) {
            start = start + " 00:00:00";
        }
        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
        }
        hashMap.put("start", start);
        hashMap.put("end", end);
        hashMap.put("name", name.replaceAll("%", "\\\\%"));
        hashMap.put("num", num);
        hashMap.put("status", status);
        hashMap.put("policyType", policyType);
        hashMap.put("roleId", roleId);
        hashMap.put("roleType", roleType);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        hashMap.put("currentTime", currentTime);
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
