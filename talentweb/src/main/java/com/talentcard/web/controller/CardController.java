package com.talentcard.web.controller;


import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("card")
@RestController
public class CardController {
    @Autowired
    private ICardService iCardService;

    @PostMapping("add")
    public ResultVO add(@RequestParam(value = "name") String name,
                        @RequestParam(value = "title") String title,
                        @RequestParam(value = "notice") String notice,
                        @RequestParam(value = "description") String description,
                        @RequestParam(value = "prerogative") String prerogative,
                        @RequestParam(value = "background") MultipartFile background,
                        @RequestParam(value = "initialWord") String initialWord,
                        @RequestParam(value = "initialNumber") String initialNumber,
                        @RequestParam(value = "status") Byte status) {
        return iCardService.add(name, title, notice, description, prerogative,
                background, initialWord, initialNumber, status);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestParam(value = "cardId") Long cardId) {
        return iCardService.delete(cardId);
    }
}
