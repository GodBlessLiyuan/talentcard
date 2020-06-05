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
    /**
     * s意见反馈
     * @param openId
     * @param content
     * @param picture
     * @param contact
     * @return
     */
    ResultVO addFeedBack(String openId, String content, String picture, String contact);

    /**
     * 我的足迹
     * @param openId
     * @return
     */
    ResultVO footprint(String openId);
}
