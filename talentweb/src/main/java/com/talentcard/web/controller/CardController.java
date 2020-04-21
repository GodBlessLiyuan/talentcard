package com.talentcard.web.controller;


import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
     * @param description
     * @param prerogative
     * @param background
     * @param initialWord
     * @param initialNumber
     * @param status
     * @return
     */
    @PostMapping("add")
    public ResultVO add(@RequestParam(value = "name") String name,
                        @RequestParam(value = "title") String title,
                        @RequestParam(value = "notice", required = false, defaultValue = "") String notice,
                        @RequestParam(value = "description") String description,
                        @RequestParam(value = "prerogative") String prerogative,
                        @RequestParam(value = "background") MultipartFile background,
                        @RequestParam(value = "initialWord") String initialWord,
                        @RequestParam(value = "initialNumber") String initialNumber,
                        @RequestParam(value = "status") Byte status) {
        return iCardService.add(name, title, notice, description, prerogative,
                background, initialWord, initialNumber, status);
    }


    @PostMapping("edit")
    public ResultVO edit(@RequestParam(value = "cardId") Long cardId,
                         @RequestParam(value = "title", required = false, defaultValue = "") String title,
                         @RequestParam(value = "description", required = false, defaultValue = "") String description,
                         @RequestParam(value = "background", required = false) MultipartFile background) {
        return iCardService.edit(cardId, title, description, background);
    }

    @PostMapping("query")
    public ResultVO query(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                          @RequestParam(value = "startTime", required = false) Date startTime,
                          @RequestParam(value = "endTime", required = false) Date endTime,
                          @RequestParam(value = "cardNum", required = false, defaultValue = "") String cardNum) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
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
}
