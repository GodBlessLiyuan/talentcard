package com.talentcard.web.controller;


import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;


/**
 * 卡的增删改查相关
 * Chen XU
 */
@RequestMapping("card")
@RestController
public class CardController {
    @Autowired
    private ICardService iCardService;

    /**
     * @param name
     * @param title
     * @param notice
     * @param description   可以不给
     * @param prerogative   必须给
     * @param background
     * @param initialWord
     * @param areaNum
     * @param status
     * @return
     */
    @PostMapping("add")
    public ResultVO add(@RequestParam(value = "name") String name,
                        @RequestParam(value = "title") String title,
                        @RequestParam(value = "notice", required = false, defaultValue = "") String notice,
                        @RequestParam(value = "description", required = false, defaultValue = "") String description,
                        @RequestParam(value = "prerogative", required = false, defaultValue = "可享受多项人才服务，如人才政策网上兑现、人才公寓申请、免费旅游、酒店农家乐折扣、VIP出行等") String prerogative,
                        @RequestParam(value = "background") MultipartFile background,
                        @RequestParam(value = "initialWord", required = false, defaultValue = "") String initialWord,
                        @RequestParam(value = "areaNum", required = false, defaultValue = "010") String areaNum,
                        @RequestParam(value = "businessDescription", required = false, defaultValue = "") String businessDescription,
                        @RequestParam(value = "status") Byte status,
                        @RequestParam(value = "color", required = false, defaultValue = "Color030") String color,
                        @RequestParam(value = "tripTimes", required = false, defaultValue = "0") Integer tripTimes,
                        HttpSession httpSession) {
        return iCardService.add(name, title, notice, description, prerogative,
                background, initialWord, areaNum, businessDescription, status, color, tripTimes, httpSession);
    }


    @PostMapping("edit")
    public ResultVO edit(@RequestParam(value = "cardId") Long cardId,
                         @RequestParam(value = "title", required = false, defaultValue = "") String title,
                         @RequestParam(value = "businessDescription", required = false, defaultValue = "") String businessDescription,
                         @RequestParam(value = "background", required = false) MultipartFile background,
                         @RequestParam(value = "tripTimes", required = false, defaultValue = "0") Integer tripTimes,
                         HttpSession httpSession) {
        return iCardService.edit(cardId, title, businessDescription, background, tripTimes, httpSession);
    }

    @PostMapping("query")
    public ResultVO query(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                          @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                          @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime,
                          @RequestParam(value = "cardNum", required = false, defaultValue = "") String cardNum) throws ParseException {
        HashMap<String, Object> hashMap = new HashMap<>(4);
        if (!startTime.equals("")) {
            startTime = startTime + " 00:00:00";
        }
        if (!endTime.equals("")) {
            endTime = endTime + " 23:59:59";
        }
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        hashMap.put("title", title.replaceAll("%", "\\\\%"));
        hashMap.put("cardNum", cardNum);
        return iCardService.query(hashMap);
    }

    @PostMapping("findOne")
    public ResultVO findOne(@RequestParam(value = "cardId") Long cardId) {
        return iCardService.findOne(cardId);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestParam(value = "cardId") Long cardId) {
        return iCardService.delete(cardId);
    }

    @PostMapping("findSeniorCard")
    public ResultVO findSeniorCard(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                                   @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                                   @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime,
                                   @RequestParam(value = "cardNum", required = false, defaultValue = "") String cardNum) throws ParseException {
        HashMap<String, Object> hashMap = new HashMap<>(4);
        if (!startTime.equals("")) {
            startTime = startTime + " 00:00:00";
        }
        if (!endTime.equals("")) {
            endTime = endTime + " 23:59:59";
        }
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        hashMap.put("title", title);
        hashMap.put("cardNum", cardNum);
        return iCardService.findSeniorCard(hashMap);
    }

    @RequestMapping("queryCardIdName")
    public ResultVO queryCardIdName() {
        return iCardService.queryCardIdName();
    }

}
