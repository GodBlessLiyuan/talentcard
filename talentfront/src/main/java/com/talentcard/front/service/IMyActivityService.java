package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-04 14:33
 * @description
 */
public interface IMyActivityService {
    ResultVO addFeedBack(String openId, String content, MultipartFile picture, String contact);
    ResultVO footprint(String openId);
}
