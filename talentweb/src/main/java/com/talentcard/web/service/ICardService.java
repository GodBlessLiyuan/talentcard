package com.talentcard.web.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/13 19:00
 * @Description: 卡用的sevice层接口
 */
public interface ICardService {
    ResultVO add(String name, String title, String notice, String description,
                 String prerogative, MultipartFile background,
                 String initialWord, String initialNumber, Byte status);

    ResultVO delete(Long cardId);
}
