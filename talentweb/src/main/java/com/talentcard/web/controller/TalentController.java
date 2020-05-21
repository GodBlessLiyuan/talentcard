package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:14
 * @description: 人才管理
 * @version: 1.0
 */
@RequestMapping("talent")
@RestController
public class TalentController {
    @Autowired
    private ITalentService iTalentService;

    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "start", defaultValue = "") String start,
                          @RequestParam(value = "end", defaultValue = "") String end,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "sex", defaultValue = "") Byte sex,
                          @RequestParam(value = "educ", defaultValue = "") Integer educ,
                          @RequestParam(value = "title", defaultValue = "") Integer title,
                          @RequestParam(value = "quality", defaultValue = "") Integer quality,
                          @RequestParam(value = "card", defaultValue = "") String card) {

        Map<String, Object> reqMap = new HashMap<>(8);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        reqMap.put("sex", sex);
        reqMap.put("educ", educ);
        reqMap.put("title", title);
        reqMap.put("quality", quality);
        reqMap.put("card", card);

        return iTalentService.query(pageNum, pageSize, reqMap);
    }

    @RequestMapping("detail")
    public ResultVO detail(@RequestParam(value = "tid") Long tid) {
        return iTalentService.detail(tid);
    }

    @RequestMapping("queryCert")
    public ResultVO queryCert(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "start", defaultValue = "") String start,
                              @RequestParam(value = "end", defaultValue = "") String end,
                              @RequestParam(value = "name", defaultValue = "") String name,
                              @RequestParam(value = "sex", defaultValue = "") Byte sex,
                              @RequestParam(value = "educ", defaultValue = "") Integer educ,
                              @RequestParam(value = "title", defaultValue = "") Integer title,
                              @RequestParam(value = "quality", defaultValue = "") Integer quality,
                              @RequestParam(value = "card", defaultValue = "") String card,
                              @RequestParam(value = "category", defaultValue = "") String category,
                              @RequestParam(value = "honour", defaultValue = "") Long honour) {
        Map<String, Object> reqMap = new HashMap<>(10);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("start", start);
        reqMap.put("end", end);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        reqMap.put("sex", sex);
        reqMap.put("educ", educ);
        reqMap.put("title", title);
        reqMap.put("quality", quality);
        reqMap.put("card", card);
        reqMap.put("category", category);
        reqMap.put("honour", honour);

        return iTalentService.queryCert(pageNum, pageSize, reqMap);
    }

    @RequestMapping("edit")
    public ResultVO edit(@RequestParam(value = "talentId") Long talentId,
                         @RequestParam(value = "cardId") Long cardId) {
        iTalentService.edit(talentId, cardId);
        return new ResultVO(1000);
    }

    @RequestMapping("batch")
    public ResultVO batch(@RequestParam(value = "file") MultipartFile file) {
        return iTalentService.batch(file);
    }
}
