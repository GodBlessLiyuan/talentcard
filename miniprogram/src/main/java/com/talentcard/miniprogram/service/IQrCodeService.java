package com.talentcard.miniprogram.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: xiahui
 * @date: Created in 2020/6/3 15:50
 * @description: 二维码
 * @version: 1.0
 */
public interface IQrCodeService {
    ResultVO create(HttpServletResponse res, String openId);
}
