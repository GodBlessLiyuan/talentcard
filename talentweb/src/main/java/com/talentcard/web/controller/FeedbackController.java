package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 10:45
 * @description: 意见反馈
 * @version: 1.0
 */
@RequestMapping("feedback")
@RestController
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param contact
     * @param start
     * @param end
     * @return
     */
    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "name", defaultValue = "") String name,
                          @RequestParam(value = "contact", defaultValue = "") String contact,
                          @RequestParam(value = "start", defaultValue = "") String start,
                          @RequestParam(value = "end", defaultValue = "") String end) {

        Map<String, Object> reqMap = new HashMap<>(6);
        reqMap.put("name", name.replaceAll("%", "\\\\%"));
        reqMap.put("contact", contact);
        reqMap.put("start", start);
        if (!"".equals(end)) {
            end = end + " 23:59:59";
        }
        reqMap.put("end", end);

        return feedbackService.query(pageNum, pageSize, reqMap);
    }
}
