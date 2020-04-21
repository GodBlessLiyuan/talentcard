package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/13 19:00
 * @Description: 卡用的sevice层接口
 */
public interface ICardService {
    ResultVO add(String name, String title, String notice, String description,
                 String prerogative, MultipartFile background,
                 String initialWord, String initialNumber, Byte status);

    ResultVO edit(Long cardId, String title, String description, MultipartFile background);

    ResultVO query(HashMap<String, Object> hashMap);

    ResultVO findOne(Long cardId);

    ResultVO delete(Long cardId);
}
