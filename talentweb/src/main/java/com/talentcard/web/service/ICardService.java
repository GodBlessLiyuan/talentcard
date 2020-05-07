package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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
                 String initialWord, String initialNumber, String businessDescription, Byte status, String color,
                 HttpSession httpSession);

    ResultVO edit(Long cardId, String title, String businessDescription, MultipartFile background, HttpSession httpSession);

    ResultVO query(HashMap<String, Object> hashMap);

    ResultVO findOne(Long cardId);

    ResultVO delete(Long cardId);

    ResultVO findSeniorCard(HashMap<String, Object> hashMap);

    ResultVO queryCardIdName();

}
